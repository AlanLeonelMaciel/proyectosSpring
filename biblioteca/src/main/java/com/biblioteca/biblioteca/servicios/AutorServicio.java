package com.biblioteca.biblioteca.servicios;

import com.biblioteca.biblioteca.entidades.Autor;
import com.biblioteca.biblioteca.entidades.Libro;
import com.biblioteca.biblioteca.exceptions.MiException;
import com.biblioteca.biblioteca.repositorios.AutorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {

    @Autowired
    AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede estar vacio o nulo.");
        }
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }

    public void modificarAutor(String nombre, String id) throws MiException {
        validar(nombre, id);
        Optional<Autor> optionalAutor = autorRepositorio.findById(id);
        if (optionalAutor.isPresent()) {
            Autor autor = optionalAutor.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }
    }

    public void validar(String nombre, String id) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede estar vacio o nulo.");
        }
        if (id.isEmpty() || id == null) {
            throw new MiException("El id no puede estar vacio o nulo.");
        }

    }
    public List<Autor> listarAutores() {
        List<Autor> autores = new ArrayList();
        autores = autorRepositorio.findAll();
        return autores;
    }
}
