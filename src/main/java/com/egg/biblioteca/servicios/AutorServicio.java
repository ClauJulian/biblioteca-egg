package com.egg.biblioteca.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;

@Service
public class AutorServicio {
    @Autowired
    private AutorRepositorio autorRepositorio;

    // EXTRAS
    private void validar(String nombre) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacio!");
        }
    }

    // CREATE
    @Transactional
    public void crearAutor(String nombre) throws MiException {
        validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }

    // READ
    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {
        List<Autor> autores = new ArrayList<Autor>();

        autores = autorRepositorio.findAll();

        return autores;
    }


    // READ
     @Transactional(readOnly = true)
    public Autor buscarPorID(UUID id){
        return autorRepositorio.getReferenceById(id);
    }

    // UPDATE
    @Transactional
    public void modificarAutor(String nombre, UUID id) throws MiException {
        validar(nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }
    }
}
