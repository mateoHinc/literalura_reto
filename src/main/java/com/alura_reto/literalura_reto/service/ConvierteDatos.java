package com.alura_reto.literalura_reto.service;

import com.alura_reto.literalura_reto.model.Libro;
import com.alura_reto.literalura_reto.model.RecordLibros;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos {
    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> T obtenerDatosAutor(String json, Class<T> clase){
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            if (clase.equals(Libro.class)) {
                RecordLibros recordLibros = objectMapper.readValue(json, RecordLibros.class);
                return (T) new Libro((recordLibros));
            }else {
                return objectMapper.readValue(json, clase);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
