package com.egg.biblioteca.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;

@Service
public class LibroServicio {
    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Autowired
    private LibroRepositorio libroRepositorio;

    // EXTRAS
    private void validar(String titulo,UUID idAutor, UUID idEditorial) throws MiException{
        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("El titulo no puede estar vacio o ser nulo!");
        }
        if (idAutor == null) {
            throw new MiException("Debes proveer un id de Autor!");
        }
        if (idEditorial == null) {
            throw new MiException("Debes proveer un id de Editorial!");
        }
    }

    // CREATE
    @Transactional
    public void crearLibro(Long isbn,String titulo, Integer ejemplares, UUID idAutor, UUID idEditorial) throws MiException {
        validar(titulo, idAutor, idEditorial);
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        Libro libro = new Libro();

        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    // READ
    @Transactional(readOnly = true)
    public List<Libro> listarLibros() {
        List<Libro> libros = new ArrayList<Libro>();
        
        libros = libroRepositorio.findAll();

        return libros;
    }

    // READ
    @Transactional(readOnly = true)
    public Libro buscarPorIsbn(Long isbn){
        return libroRepositorio.getReferenceById(isbn);
    }

    // UPDATE

    @Transactional
    public void modificarLibro(Long idLibro, String titulo, Integer ejemplares, UUID idAutor, UUID idEditorial) throws MiException {
        validar(titulo, idAutor, idEditorial);
        Optional<Libro> respuestaLibro = libroRepositorio.findById(idLibro);

        if (respuestaLibro.isPresent()) {
            Libro libro = respuestaLibro.get();
            Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
            Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            if (respuestaAutor.isPresent()) {
                libro.setAutor(respuestaAutor.get());
            }
            if (respuestaEditorial.isPresent()) {
                libro.setEditorial(respuestaEditorial.get());
            }

            libroRepositorio.save(libro);
        }
    }
}
