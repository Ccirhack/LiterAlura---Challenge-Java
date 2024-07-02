package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.lang.annotation.Target;
import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer anio_nacimiento;
    private Integer anio_fallecimiento;
    @OneToMany(mappedBy = "autors", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.anio_nacimiento = datosAutor.AnioNacimiento();
        this.anio_fallecimiento = datosAutor.AnioFallecimiento();
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(a -> a.setAutors(this));
        this.libros = libros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnio_fallecimiento() {
        return anio_fallecimiento;
    }

    public void setAnio_fallecimiento(Integer anio_fallecimiento) {
        this.anio_fallecimiento = anio_fallecimiento;
    }

    public Integer getAnio_nacimiento() {
        return anio_nacimiento;
    }

    public void setAnio_nacimiento(Integer anio_nacimiento) {
        this.anio_nacimiento = anio_nacimiento;
    }

    @Override
    public String toString() {
        return "nombre='" + nombre + '\'' +
                ", AnioNacimiento=" + anio_nacimiento +
                ", AnioFallecimiento=" + anio_fallecimiento;
    }
}
