package com.biblioteca.biblioteca.servicios;

import com.biblioteca.biblioteca.entidades.Autor;
import com.biblioteca.biblioteca.entidades.Editorial;
import com.biblioteca.biblioteca.entidades.Libro;
import com.biblioteca.biblioteca.exceptions.MiException;
import com.biblioteca.biblioteca.repositorios.AutorRepositorio;
import com.biblioteca.biblioteca.repositorios.EditorialRepositorio;
import com.biblioteca.biblioteca.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(String titulo, Long isbn, Integer ejemplares, String idAutor, String idEditorial) throws MiException {
        validar(titulo, idAutor, idEditorial, isbn, ejemplares);
        Libro libro = new Libro();
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAltaDate(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libroRepositorio.save(libro);
    }

    public List<Libro> listarLibros() {
        List<Libro> libros = new ArrayList();
        libros = libroRepositorio.findAll();
        return libros;
    }

    public void modificarLibro(String titulo, Long isbn, String idAutor, String idEditorial, Integer ejemplares) throws MiException {
        validar(titulo, idAutor, idEditorial, isbn, ejemplares);
        Optional<Libro> respuestaLibro = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if (respuestaAutor.isPresent()) {
            autor = respuestaAutor.get();
        }

        if (respuestaEditorial.isPresent()) {
            editorial = respuestaEditorial.get();
        }
        if (respuestaLibro.isPresent()) {
            Libro libro = respuestaLibro.get();
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);
            libroRepositorio.save(libro);
        }
    }

    public void validar(String titulo, String idAutor, String idEditorial, Long isbn, Integer ejemplares) throws MiException {
        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("El titulo no puede estar vacio o nulo.");
        }
        if (idAutor.isEmpty() || idAutor == null) {
            throw new MiException("El idAutor no puede estar vacio o nulo.");
        }
        if (idEditorial.isEmpty() || idEditorial == null) {
            throw new MiException("El idEditorial no puede estar vacio o nulo.");
        }
        if (isbn == null) {
            throw new MiException("El isbn no puede ser nulo.");
        }
        if (ejemplares == null) {
            throw new MiException("Ejemplares no puede ser nulo.");
        }
    }
};
