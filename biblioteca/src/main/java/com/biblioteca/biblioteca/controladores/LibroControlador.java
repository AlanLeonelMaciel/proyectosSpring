package com.biblioteca.biblioteca.controladores;

import com.biblioteca.biblioteca.entidades.Autor;
import com.biblioteca.biblioteca.entidades.Editorial;
import com.biblioteca.biblioteca.exceptions.MiException;
import com.biblioteca.biblioteca.servicios.AutorServicio;
import com.biblioteca.biblioteca.servicios.EditorialServicio;
import com.biblioteca.biblioteca.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    LibroServicio libroServicio;
    @Autowired
    AutorServicio autorServicio;
    @Autowired
    EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        List<Autor> listaAutores = autorServicio.listarAutores();
        List<Editorial> listaEditorials = editorialServicio.listarEditorials();
        modelo.addAttribute("autores", listaAutores);
        modelo.addAttribute("editoriales", listaEditorials);

        return "libroForm.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo, @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor, @RequestParam String idEditorial, ModelMap modelo) {
        try {
            libroServicio.crearLibro(titulo, isbn, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "El libro ha sido cargado correctamente.");
        } catch (MiException e) {
            List<Autor> listaAutores = autorServicio.listarAutores();
            List<Editorial> listaEditorials = editorialServicio.listarEditorials();
            modelo.addAttribute("autores", listaAutores);
            modelo.addAttribute("editoriales", listaEditorials);
            modelo.put("error", e.getMessage());
            return "libroForm.html";
        }
        return "index.html";
    }
}
