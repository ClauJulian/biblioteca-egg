package com.egg.biblioteca.entidades;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long isbn;

    private String titulo;

    private Integer ejemplares;

    @Temporal(TemporalType.DATE)
    private Date alta;

    @ManyToOne
    private Autor autor;

    @ManyToOne
    private Editorial editorial;

   
}
