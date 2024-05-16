package com.alura_reto.literalura_reto.repository;

import com.alura_reto.literalura_reto.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);

    @Query("SELECT a.nombreCompleto FROM Autor a WHERE :anio >= a.anioNacimiento AND :anio <= a.anioMuerte")
    List<String> autoresPorAnio(int anio);

    @Query("SELECT a.nombreCompleto FROM Autor a WHERE a.nombreCompleto LIKE %:nombre%")
    List<String> autorNombre(String nombre);
}
