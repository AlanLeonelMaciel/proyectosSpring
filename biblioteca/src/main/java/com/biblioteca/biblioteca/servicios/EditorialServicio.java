package com.biblioteca.biblioteca.servicios;

import com.biblioteca.biblioteca.entidades.Editorial;
import com.biblioteca.biblioteca.exceptions.MiException;
import com.biblioteca.biblioteca.repositorios.EditorialRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {

    @Autowired
    EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede estar vacio o nulo.");
        }
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }

    public void modificarEditorial(String id, String nombre) throws MiException {
        validar(nombre, id);
        Optional<Editorial> optionalEditorial = editorialRepositorio.findById(id);
        if (optionalEditorial.isPresent()) {
            Editorial editorial = optionalEditorial.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
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
    public List<Editorial> listarEditorials(){
        List<Editorial> listaEditoriales=new ArrayList();
        listaEditoriales=editorialRepositorio.findAll();
        return listaEditoriales;
    }
}
