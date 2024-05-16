package com.alura_reto.literalura_reto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    private String titulo;
    @Column(unique = false)
    private int numeroDescargas;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    @Column(name="autor_id")
    private String autor;

    public Libro(){

    }

    public Libro(RecordLibros recordLibros) {
        this.titulo = recordLibros.titulo();
        this.numeroDescargas = recordLibros.numeroDescargas();
        this.idioma = Idioma.fromString(recordLibros.idiomas()[0].toString());
        this.autor = recordLibros.autor();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(int numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "/***********Libro***********/ \n" +
                "Título= " + titulo + "\n" +
                "Número de Descargas= " + numeroDescargas + "\n" +
                "Idioma= " + idioma + "\n" +
                "Autor= " + autor + "\n"+
                "/**************************/ \n";
    }
}
