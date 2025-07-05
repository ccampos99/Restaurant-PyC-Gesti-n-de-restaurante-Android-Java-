package com.peter.restauranteproyecto.common.model;

public class Mesa {
    private String nombre;
    private int capacidad;
    private String estado;
    private String mesero;
    private String clientes;
    private String informacion;

    public Mesa(String nombre, int capacidad, String estado, String mesero, String clientes, String informacion) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.estado = estado;
        this.mesero = mesero;
        this.clientes = clientes;
        this.informacion = informacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public String getClientes() {
        return clientes;
    }

    public void setClientes(String clientes) {
        this.clientes = clientes;
    }

    public String getMesero() {
        return mesero;
    }

    public void setMesero(String mesero) {
        this.mesero = mesero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}
