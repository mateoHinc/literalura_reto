package com.alura_reto.literalura_reto.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    private String nombreCompleto;
    @Column(unique = false)
    private int anioNacimiento;
    private int anioMuerte;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {}

    public Autor(RecordAutor recordAutor) {
        this.nombreCompleto = recordAutor.nombreCompleto();
        this.anioNacimiento = recordAutor.anioNacimiento();
        this.anioMuerte = recordAutor.anioMuerte();
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    @Override
    public String toString() {
        return "/***********Autor***********/ \n" +
                "Nombre Completo= " + nombreCompleto + "\n" +
                "Año de Nacimiento= " + anioNacimiento + "\n" +
                "Año de Muerte=" + anioMuerte + "\n" +
                "libros=" + libros + "\n" +
                "/**************************/ \n";
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(int anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public int getAnioMuerte() {
        return anioMuerte;
    }

    public void setAnioMuerte(int anioMuerte) {
        this.anioMuerte = anioMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}
