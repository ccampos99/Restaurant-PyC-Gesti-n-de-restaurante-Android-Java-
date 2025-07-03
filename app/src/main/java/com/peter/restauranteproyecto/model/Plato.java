package com.peter.restauranteproyecto.model;

import java.io.Serializable;

public class Plato implements Serializable {
    public String nombre;
    public String descripcion;
    public String categoria;
    public double precio;
    public String estado;        // "Disponible", "Temporada", "No Disponible"
    public int tiempoPrep;    // Ej. "25 min"

    public Plato(String nombre, String descripcion, String categoria,
                 double precio, String estado, int tiempoPrep) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.estado = estado;
        this.tiempoPrep = tiempoPrep;
    }
}

