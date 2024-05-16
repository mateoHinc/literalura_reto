package com.alura_reto.literalura_reto.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
