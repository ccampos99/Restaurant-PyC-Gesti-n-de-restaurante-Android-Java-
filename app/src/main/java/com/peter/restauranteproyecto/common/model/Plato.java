package com.peter.restauranteproyecto.common.model;

import java.io.Serializable;

public class Plato implements Serializable{
    private String nombre;
    private String descripcion;
    private String categoria;
    private double precio;
    private String estado;        // "Disponible", "Temporada", "No Disponible"
    private int tiempoPrep;    // Ej. "25 min"

    public Plato(String nombre, String descripcion, String categoria,
                 double precio, String estado, int tiempoPrep) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.estado = estado;
        this.tiempoPrep = tiempoPrep;
    }

    public int getTiempoPrep() {
        return tiempoPrep;
    }

    public void setTiempoPrep(int tiempoPrep) {
        this.tiempoPrep = tiempoPrep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

