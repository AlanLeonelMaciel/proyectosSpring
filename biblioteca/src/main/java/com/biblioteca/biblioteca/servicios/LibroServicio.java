
package com.biblioteca.biblioteca.servicios;

import com.biblioteca.biblioteca.entidades.Autor;
import com.biblioteca.biblioteca.entidades.Editorial;
import com.biblioteca.biblioteca.entidades.Libro;
import com.biblioteca.biblioteca.repositorios.AutorRepositorio;
import com.biblioteca.biblioteca.repositorios.EditorialRepositorio;
import com.biblioteca.biblioteca.repositorios.LibroRepositorio;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {
    @Autowired
    private  LibroRepositorio libroRepositorio;
    @Autowired
   private AutorRepositorio autorRepositorio;
    @Autowired 
    private EditorialRepositorio editorialRepositorio;
    public void crearLibro(String titulo, long isbn,Integer ejemplares, String idAutor, String idEditorial){
        Libro libro= new Libro();
        Autor autor= autorRepositorio.findById(idAutor).get();
        Editorial editorial=editorialRepositorio.findById(idEditorial).get();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAltaDate(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libroRepositorio.save(libro);
        
    };
}
