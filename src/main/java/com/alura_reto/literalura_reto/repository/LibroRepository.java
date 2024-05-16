package com.alura_reto.literalura_reto.repository;

import com.alura_reto.literalura_reto.model.Idioma;
import com.alura_reto.literalura_reto.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTituloContainsIgnoreCase(String tituloLibro);

    List<Libro> findByIdioma(Idioma idioma);

    @Query("SELECT l.titulo, l.numeroDescargas FROM Libro l ORDER BY l.numeroDescargas DESC")
    List<Object[]> top10();
}
