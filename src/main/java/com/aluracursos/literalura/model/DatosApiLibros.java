package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosApiLibros(
        @JsonAlias("count") Integer NumeroLibros,
        @JsonAlias("results") ArrayList<DatosLibro> libros
) {
}
