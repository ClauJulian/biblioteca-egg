package com.egg.biblioteca.controladores;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;
import com.egg.biblioteca.servicios.EditorialServicio;
import com.egg.biblioteca.servicios.LibroServicio;

@Controller // Esta clase es un Controlador
@RequestMapping("/libro") // Recibira todas las solicitudes enviadas a "/libro"
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar") // GET /libro/registrar
    public String registrar(ModelMap model) {
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        model.addAttribute("autores", autores);
        model.addAttribute("editoriales", editoriales);
        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn,
            @RequestParam String titulo,
            @RequestParam(required = false) Integer ejemplares,
            @RequestParam String idAutor,
            @RequestParam String idEditorial, ModelMap model) {
        try {
            // Realizamos la conversión manual de String a UUID. En este caso, se convertirá solo si el ID no es nulo y no está vacío
            UUID autorUUID = (idAutor != null && !idAutor.isEmpty()) ? UUID.fromString(idAutor) : null;
            UUID editorialUUID = (idEditorial != null && !idEditorial.isEmpty()) ? UUID.fromString(idEditorial) : null;

            if (autorUUID == null || editorialUUID == null) {
                throw new MiException("Debe seleccionar un autor y una editorial válidos.");
            }

            libroServicio.crearLibro(isbn, titulo, ejemplares, autorUUID, editorialUUID);
            model.put("exito", "El libro fue cargado correctamente.");

        } catch (IllegalArgumentException ex) {
            model.put("error", ex.getMessage());
            return "libro_form.html";
        } catch (MiException ex) {
            model.addAttribute("autores", autorServicio.listarAutores());
            model.addAttribute("editoriales", editorialServicio.listarEditoriales());
            model.put("error", ex.getMessage());

            return "libro_form.html"; // volvemos a cargar el formulario.
        }
        return "inicio.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Libro> libros = libroServicio.listarLibros();

        modelo.addAttribute("libros", libros);
        return "libro_list.html";
    }

    // para modificar una entidad primero necesito traerla y luego modificarla
    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap model) {
        Libro libro = libroServicio.buscarPorIsbn(isbn);

        model.put("libro", libroServicio.buscarPorIsbn(isbn));
        model.addAttribute("autores", autorServicio.listarAutores());  // Agregar autores para que nos muestre la lista de autores
        model.addAttribute("editoriales", editorialServicio.listarEditoriales());  // Agregar editoriales para que nos muestre la lista de autores

        model.addAttribute("autorSeleccionado", libro.getAutor().getId());  // UUID del autor actual para que ya aparezca seleccionado
        model.addAttribute("editorialSeleccionada", libro.getEditorial().getId());  // UUID de la editorial actual para que ya aparezca seleccionado
        return "libro_modificar.html";
    }

    /* String titulo, Integer ejemplares, UUID idAutor, UUID idEditorial, Long idLibro */
    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial, ModelMap modelo) {
        try {
            UUID autorUUID = (idAutor != null && !idAutor.isEmpty()) ? UUID.fromString(idAutor) : null;
            UUID editorialUUID = (idEditorial != null && !idEditorial.isEmpty()) ? UUID.fromString(idEditorial) : null;

            if (autorUUID == null || editorialUUID == null) {
                throw new MiException("Debe seleccionar un autor y una editorial válidos.");
            }

            libroServicio.modificarLibro(isbn, titulo, ejemplares, autorUUID, editorialUUID);

            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
        }
    }
}
