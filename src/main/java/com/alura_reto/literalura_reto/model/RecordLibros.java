package com.alura_reto.literalura_reto.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RecordLibros(
        @JsonAlias("title") String titulo,
        @JsonAlias("download_count") int numeroDescargas,
        @JsonAlias("languages") Idioma[] idiomas,
        String autor
) {
//    @Override
    public Idioma[] idiomas() {
        return idiomas;
    }
}
