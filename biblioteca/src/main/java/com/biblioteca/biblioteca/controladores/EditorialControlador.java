package com.biblioteca.biblioteca.controladores;

import com.biblioteca.biblioteca.entidades.Editorial;
import com.biblioteca.biblioteca.exceptions.MiException;
import com.biblioteca.biblioteca.repositorios.EditorialRepositorio;
import com.biblioteca.biblioteca.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "editorialForm.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {
        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "La editorial se ha cargado correctamente.");
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "editorialForm.html";
        }
        return "index.html";
    }

    @GetMapping("/listar")
    public String listarEditoriales(Model model, @RequestParam(defaultValue = "0") int page) {
        // Muestra 5 editoriales por página
        Page<Editorial> editoriales = editorialServicio.obtenerPaginaDeEditoriales(page, 5);

        model.addAttribute("listaEditoriales", editoriales.getContent());
        model.addAttribute("paginaActual", page);
        model.addAttribute("totalPages", editoriales.getTotalPages());
        return "listarEditoriales.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("editorial", editorialServicio.getOne(id));
        return "modificarEditorial.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo) {
        try {
            editorialServicio.modificarEditorial(id, nombre);
        } catch (MiException e) {
            modelo.put("editorial", editorialServicio.getOne(id));
            modelo.put("error", e.getMessage());
            return "modificarEditorial.html";
        }
        return "redirect:../listar";
    }
}
