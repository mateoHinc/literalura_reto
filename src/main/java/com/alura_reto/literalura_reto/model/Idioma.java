package com.alura_reto.literalura_reto.model;

public enum Idioma {
    es("es"),
    en("en"),
    fr("fr"),
    it("it"),
    de("de"),
    pt("pt");

    private String categoria;

    Idioma(String categoria) { this.categoria = categoria; }

    public static Idioma fromString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if(idioma.categoria.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Idioma "+text+" no encontrado");
    }

    @Override
    public String toString() { return categoria; }
}
