package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long>{
    Optional<Autor> findByNombre(String nombre);

    @Query("select a from Autor a where a.anio_nacimiento < :anio and a.anio_fallecimiento > :anio")
    Optional<List> autoresVivosPorAnio(int anio);

    @Query("select l from Autor a join a.libros l where l.idiomas = :opcion ")
    Optional<List> buscarLibrosPorIdioma(String opcion);
}
