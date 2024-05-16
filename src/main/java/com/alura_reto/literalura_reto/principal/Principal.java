package com.alura_reto.literalura_reto.principal;

import com.alura_reto.literalura_reto.model.*;
import com.alura_reto.literalura_reto.repository.AutorRepository;
import com.alura_reto.literalura_reto.repository.LibroRepository;
import com.alura_reto.literalura_reto.service.ConsumoAPI;
import com.alura_reto.literalura_reto.service.ConvierteDatos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI api = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<RecordLibros> recordLibros = new ArrayList<>();
    private LibroRepository repositorioLibro;
    private AutorRepository repositorioAutor;
    private List<Libro> libros;
    private Optional<Libro> libroBuscado;

    String nombreAutor = null;

    @Autowired
    public Principal(LibroRepository repositorioLibro, AutorRepository repositorioAutor) {
        this.repositorioLibro = repositorioLibro;
        this.repositorioAutor = repositorioAutor;
    }

    public void menu() {
        var opcion = -1;

        while (opcion != 0) {
            var menu = """
                    \n
                    1 - Buscar libro por título
                    2 - Listar Libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    verLibrosRegistrados();
                    break;
                case 3:
                    verAutoresRegistrados();
                    break;
                case 4:
                    buscarAutorPorAnio();
                    break;
                case 5:
                    verLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        }
    }

    private void verLibrosPorIdioma() {
        Idioma idioma = null;
        int opcionIdioma;

        do {
            var menuIdioma = """
                        \n
                        Eligir idioma:
                        1 - Español
                        2 - Inglés
                        3 - Francés
                        4 - Italiano
                        5 - Alemán
                        6 - Portugués
                    """;

            System.out.println(menuIdioma);
            try {
                opcionIdioma = teclado.nextInt();
                switch (opcionIdioma) {
                    case 1:
                        idioma = Idioma.es;
                        break;
                    case 2:
                        idioma = Idioma.en;
                        break;
                    case 3:
                        idioma = Idioma.fr;
                        break;
                    case 4:
                        idioma = Idioma.it;
                        break;
                    case 5:
                        idioma = Idioma.de;
                        break;
                    case 6:
                        idioma = Idioma.pt;
                        break;
                    default:
                        System.out.println("Opción invalida");
                        teclado.nextLine();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Introducir un número válido");
                teclado.nextLine();
                opcionIdioma = 0;
            }
        } while(opcionIdioma < 1 || opcionIdioma > 6);

        if(idioma != null){
            List<Libro> libros = repositorioLibro.findByIdioma(idioma);
            if(libros.isEmpty()){
                System.out.println("No hay idioma disponible del libro");
            } else {
              for (Libro libro : libros){
                  System.out.println(libro);
              }
            }
        }
    }

    private void buscarAutorPorAnio() {
        System.out.println("Ingrese el año");
        int anio = teclado.nextInt();

        List<String> autoresAnios = repositorioAutor.autoresPorAnio(anio);
        if (!autoresAnios.isEmpty()) {
            autoresAnios.forEach(nombreCompleto -> System.out.printf("Autor: %s\n", nombreCompleto));
        } else {
            System.out.println("No hay autores en esa epoca");
        }
    }

    private void verAutoresRegistrados() {
        System.out.println("/***********Autores Registrados***********/ \n");
        List<Autor> autores = repositorioAutor.findAll();
        for (Autor autor : autores) {
            System.out.println(autor);
        }
        System.out.println("/*****************************************/ \n");

    }

    private void verLibrosRegistrados() {
        System.out.println("/***********Libros Registrados***********/ \n");
        List<Libro> libros = repositorioLibro.findAll();
        for (Libro libro : libros) {
            System.out.println(libro);
        }
        System.out.println("/***************************************/ \n");
    }

    private RecordAutor getDatosAutor(String json) {
        return conversor.obtenerDatosAutor(json, RecordAutor.class);
    }

    private void buscarLibroPorTitulo() {
        RecordLibros datos = getRecordLibros();
        Libro libro = new Libro(datos);
        libro.setAutor(nombreAutor);
        repositorioLibro.save(libro);
        System.out.println(libro.toString());
    }

    private RecordLibros getRecordLibros() {
        System.out.println("Escribe el titulo del libro que desea investigar");
        String tituloLibro = teclado.nextLine();
        String tituloLibro2 = (tituloLibro.replace(" ", "%20")).toLowerCase();
        String json = api.obtenerDatos(URL_BASE + "?search=" + tituloLibro2);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);
            json = rootNode.get("results").get(0).toString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        nombreAutor = buscarAutor(json);

        RecordLibros datos = conversor.obtenerDatos(json, RecordLibros.class);
        return datos;
    }

    @Transactional
    private String buscarAutor(String json) {
        String nombreAutor = null;
        JsonNode autoresNode = null;

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            autoresNode = jsonNode.get("authors");
            json = autoresNode.get(0).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        RecordAutor datosAutor = getDatosAutor(json);
        Autor autor = new Autor(datosAutor);
        repositorioAutor.save(autor);
        return datosAutor.nombreCompleto();
    }
}
