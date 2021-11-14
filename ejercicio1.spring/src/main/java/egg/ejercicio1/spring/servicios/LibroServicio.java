/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.ejercicio1.spring.servicios;

import egg.ejercicio1.spring.Errores.ErrorServicio;
import egg.ejercicio1.spring.entidades.Autor;
import egg.ejercicio1.spring.entidades.Editorial;
import egg.ejercicio1.spring.entidades.Libro;
import egg.ejercicio1.spring.repositorios.AutorRepositorio;
import egg.ejercicio1.spring.repositorios.EditorialRepositorio;
import egg.ejercicio1.spring.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void registrarLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String autorNombre, String editorialNombre) throws ErrorServicio {
        validarLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados);
        Autor autor = autorRepositorio.buscarAutorPorNombre(autorNombre);
        Editorial editorial = editorialRepositorio.buscarEditorialPorNombre(editorialNombre);

        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setIsbn(isbn);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplares - ejemplaresPrestados);
        libro.setAnio(anio);
        libro.setAlta(true);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    @Transactional
    public void modificarLibro(String idAutor, String idEditorial, String id, String titulo, Long isbn, Integer anio, Integer ejemplares, Integer ejemplaresPrestados) throws ErrorServicio {
        validarLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados);
        Autor autor = autorRepositorio.buscarAutorPorNombre(idAutor);
        Editorial editorial = editorialRepositorio.buscarEditorialPorNombre(idEditorial);
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setTitulo(titulo);
            libro.setIsbn(isbn);
            libro.setEjemplares(ejemplares);
            libro.setAnio(anio);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplares - ejemplaresPrestados);
            libro.setAutor(autor);
            libro.setEditorial(editorial);

            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No se encontró el libro que buscaba");
        }
    }

    public void agregarEjemplares(String id, String titulo, Integer ejemplaresAdicionales) throws ErrorServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setEjemplares(ejemplaresAdicionales + libro.getEjemplares());
            libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());
            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No se encontró el libro que buscaba");
        }
    }

    public void prestarLibro(String id, String titulo, Integer libroPrestado) throws ErrorServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setEjemplaresPrestados(libroPrestado + libro.getEjemplaresPrestados());
            libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());
            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No se encontró el libro que buscaba");
        }
    }

    public void habilitarLibro(String id, String titulo) throws ErrorServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(true);
            libroRepositorio.save(libro);
        }
    }

    public void deshabilitarLibro(String id, String titulo) throws ErrorServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(false);
            libroRepositorio.save(libro);
        }
    }

    public List<Libro> libros() {
        List<Libro> libros = libroRepositorio.findAll();
        return libros;
    }

    public void validarLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados) throws ErrorServicio {
        if (isbn == null) {
            throw new ErrorServicio("El codigo isbn no puede estar vacío");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacío");
        }
        if (anio == null) {
            throw new ErrorServicio("El año no puede estar vacío");
        }
        if (ejemplares < 0) {
            throw new ErrorServicio("La cantidad de libros no puede ser negativa");
        }
        if (ejemplaresPrestados > ejemplares) {
            throw new ErrorServicio("La cantidad de libros prestados no puede ser mayor a la cantidad total de ejemplares");
        }
    }
}
