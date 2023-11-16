package com.biblioteca.biblioteca.controladores;

import com.biblioteca.biblioteca.entidades.Autor;
import com.biblioteca.biblioteca.exceptions.MiException;
import com.biblioteca.biblioteca.repositorios.AutorRepositorio;
import com.biblioteca.biblioteca.servicios.AutorServicio;
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
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    AutorServicio autorServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "autorForm.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {
        try {
            autorServicio.crearAutor(nombre);
            modelo.put("exito", "El autor ha sido cargado correctamente.");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "autorForm.html";
        }
        return "index.html";
    }

    @GetMapping("/listar")
    public String listarAutores(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Autor> autores = autorServicio.obtenerPaginaDeAutores(page, 5);//muestra 5 autores por pagna

        model.addAttribute("listaAutores", autores.getContent());
        model.addAttribute("paginaActual", page);
        model.addAttribute("totalPages", autores.getTotalPages());
        
        return "listarAutores.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("autor", autorServicio.getOne(id));
        return "modificarAutor.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo) {
        try {
            autorServicio.modificarAutor(nombre, id);
        } catch (MiException e) {
            modelo.put("autor", autorServicio.getOne(id));
            modelo.put("error", e.getMessage());
            return "modificarAutor.html";
        }
        return "redirect:../listar";
    }
}
