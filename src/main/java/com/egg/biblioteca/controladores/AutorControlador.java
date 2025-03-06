package com.egg.biblioteca.controladores;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;

@Controller // Esta clase es un Controlador
@RequestMapping("/autor") // Recibira todas las solicitudes hacia "/autor"
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/registrar") // GET /autor/registrar
    public String registrar() {
        return "autor_form.html";
    }

    @PostMapping("/registro") // POST /autor/registro
    public String registro(@RequestParam String nombre, ModelMap model) {
        try {
            autorServicio.crearAutor(nombre);
            model.addAttribute("exito", "¡El Autor se ha creado con exito!");
        } catch (MiException me) {
            model.addAttribute("error", "¡El Autor debe tener un nombre!");
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, me);
            return "autor_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {


        List<Autor> autores = autorServicio.listarAutores();
        modelo.addAttribute("autores", autores);
        return "autor_list.html";
    }

    // para modificar una entidad primero necesito traerla y luego modificarla
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable UUID id, ModelMap modelo) {
        modelo.put("autor", autorServicio.buscarPorID(id));
        return "autor_modificar.html";
    }


    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable UUID id, String nombre, ModelMap modelo) {
        try {
            autorServicio.modificarAutor(nombre, id);

            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
        }
    }
}
