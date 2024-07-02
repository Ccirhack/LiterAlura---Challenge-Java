package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.service.ConsumoApi;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    Scanner teclado = new Scanner(System.in);
    private static String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private ConsumoApi consumoApi = new ConsumoApi();
    private AutorRepository repository;

    public Principal(AutorRepository repository) {
        this.repository = repository;
    }

    public void mostrarMenu() {
        var opcionUsuario = -1;
        while (opcionUsuario != 0) {
            var menu = """
                    ----------------
                    Elija la opción a través de su número:
                    1. Buscar libro por título
                    2. Listar libros registrados
                    3. Listar autores registrados
                    4. Listar autores vivos en un determinado año
                    5. Listar libros por idioma
                    0. Salir
                    """;
            System.out.println(menu);
            opcionUsuario = teclado.nextInt();
            teclado.nextLine();

            switch (opcionUsuario) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida: " + opcionUsuario);
            }
        }
    }

    private DatosLibro buscarLibroEnLaWeb() {
        System.out.println("Escribe el título del libro que quieres buscar: ");
        var libroUsuario = teclado.nextLine();

        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + libroUsuario.replace(" ", "+"));
        var datosLibro = conversor.obtenerDatos(json, DatosApiLibros.class);

        //DatosLibro
        Optional<DatosLibro> libroEncontrado = datosLibro.libros().stream()
                .filter(l -> l.titulo().toLowerCase().contains(libroUsuario.toLowerCase()))
                .findFirst();

        if (libroEncontrado.isPresent()) {
            //Llamada al método guardarDatos para mandar los datos a la base de datos
            guardarDatos(libroEncontrado.get());
            return libroEncontrado.get();
        } else {
            return null;
        }
    }

    private void guardarDatos(DatosLibro datosLibro) {
        var datos = repository.findAll();

        Optional<Libro> nombreLibro = datos.stream()
                .flatMap(a -> a.getLibros()
                        .stream()
                        .filter(l -> l.getTitulo().toLowerCase().contains(datosLibro.titulo().toLowerCase())))
                .findFirst();

        if (nombreLibro.isPresent()) {
            System.out.println("El libro ya está registrado");
        } else {
            // Buscar el autor por nombre
            Optional<Autor> autorExistente = repository.findByNombre(datosLibro.autor().get(0).nombre());

            Autor autor;
            if (autorExistente.isPresent()) {
                // Si el autor ya existe, usar el existente
                autor = autorExistente.get();
            } else {
                // Si el autor no existe, crear uno nuevo
                autor = new Autor(datosLibro.autor().get(0));
                autor = repository.save(autor);
            }

            // Crear el nuevo libro con el autor
            Libro libro = new Libro(datosLibro);
            libro.setAutors(autor);

            // Guardar el libro en la base de datos

            List<Libro> libros = new ArrayList<>();
            libros.add(libro);
            autor.setLibros(libros);
            repository.save(autor);
        }
    }

    private void buscarLibroPorTitulo() {
        DatosLibro datosLibro = buscarLibroEnLaWeb();
        if (datosLibro != null) {
            Libro libro = new Libro(datosLibro);
            System.out.println(String.format("""
                -----LIBRO-----
                Título: %s
                Autor: %s
                Idioma: %s
                Número de descargas: %d""", libro.getTitulo(), libro.getAutor(), libro.getIdiomas(), libro.getNumeroDeDescargas()));
        } else {
            System.out.println("¡¡¡¡¡Libro no encontrado!!!!!");
        }

    }

    private void listarLibrosRegistrados() {
        var datos = repository.findAll();
        datos.forEach(a -> a.getLibros().forEach(l -> System.out.println(String.format("""
                -----LIBRO-----
                Título: %s
                Autor: %s
                Idioma: %s
                Número de descargas: %d\n""", l.getTitulo(), l.getAutor(), l.getIdiomas(), l.getNumeroDeDescargas()))));
    }

    private void listarAutoresRegistrados() {
        var datos = repository.findAll();
        mostrarAutores(datos);
    }

    private void listarAutoresVivosPorAnio() {
        System.out.println("Ingrese el año del cuál quieres buscar a los autores: ");
        var anio = teclado.nextInt();
        teclado.nextLine();
        Optional<List> autores = repository.autoresVivosPorAnio(anio);
        if (autores.isPresent()) {
            List<Autor> listaAutores = autores.get();
            mostrarAutores(listaAutores);
        } else {
            System.out.println("No hay autores en nuestra base de datos que vivieron en esa fecha");
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
                es - español
                en - inglés
                fr - francés
                pt - portugués""");
        var opcion = teclado.nextLine();

        Optional<List> libros = repository.buscarLibrosPorIdioma(opcion);
        if (libros.isPresent()) {
            List<Libro> listaLibros = libros.get();
           listaLibros.forEach(l -> System.out.println(String.format("""
                   -----LIBRO-----
                   Título: %s
                   Autor: %s
                   Idioma: %s
                   Número de descargas: %d\n""", l.getTitulo(), l.getAutor(), l.getIdiomas(), l.getNumeroDeDescargas())));
        } else {
            System.out.println("No existen libros de tal idioma en la base de datos");
        }
    }

    private void mostrarAutores(List<Autor> autores) {
        autores.forEach(a -> {
            System.out.println(String.format("""
            Autor: %s
            Fecha de nacimiento: %s
            Fecha de fallecimiento: %s""", a.getNombre(), a.getAnio_nacimiento(), a.getAnio_fallecimiento()));
            System.out.println("Libros: " + a.getLibros().stream().map(l -> l.getTitulo())
                    .collect(Collectors.toList()));
            System.out.println(); // Para una nueva línea entre autores
        });
    }
}
