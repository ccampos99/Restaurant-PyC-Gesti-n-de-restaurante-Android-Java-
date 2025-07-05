package com.peter.restauranteproyecto.common.model;

public class Proveedor {
    private String nombre;
    private String productos;
    private String categoria;
    private String telefono;
    private String correo;
    private String direccion;
    private double calificacion;
    private String estado;
    private int totalPedidos;
    private String ultimaEntrega;
    private String tiempoEntrega;

    public Proveedor(String nombre, String productos, String categoria, String telefono, String correo,
                     String direccion, double calificacion, String estado,
                     int totalPedidos, String ultimaEntrega, String tiempoEntrega) {
        this.nombre = nombre;
        this.productos = productos;
        this.categoria = categoria;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.calificacion = calificacion;
        this.estado = estado;
        this.totalPedidos = totalPedidos;
        this.ultimaEntrega = ultimaEntrega;
        this.tiempoEntrega = tiempoEntrega;
    }

    // Getters y setters omitidos por brevedad


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTotalPedidos() {
        return totalPedidos;
    }

    public void setTotalPedidos(int totalPedidos) {
        this.totalPedidos = totalPedidos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUltimaEntrega() {
        return ultimaEntrega;
    }

    public void setUltimaEntrega(String ultimaEntrega) {
        this.ultimaEntrega = ultimaEntrega;
    }

    public String getTiempoEntrega() {
        return tiempoEntrega;
    }

    public void setTiempoEntrega(String tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }
}
