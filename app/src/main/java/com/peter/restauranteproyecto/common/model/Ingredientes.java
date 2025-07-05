package com.peter.restauranteproyecto.common.model;

public class Ingredientes {
    private String nombre;
    private String categoria;
    private String stockActual;
    private String stockMinimo;
    private String estado;
    private String proveedor;
    private double costoUnidad;
    private String fechaReposicion;

    public Ingredientes(String nombre, String categoria, String stockActual, String stockMinimo,
                       String estado, String proveedor, double costoUnidad, String fechaReposicion) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.estado = estado;
        this.proveedor = proveedor;
        this.costoUnidad = costoUnidad;
        this.fechaReposicion = fechaReposicion;
    }

    // Getters y setters omitidos por brevedad...

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getStockActual() {
        return stockActual;
    }

    public String getStockMinimo() {
        return stockMinimo;
    }

    public String getEstado() {
        return estado;
    }

    public String getProveedor() {
        return proveedor;
    }

    public double getCostoUnidad() {
        return costoUnidad;
    }

    public String getFechaReposicion() {
        return fechaReposicion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setStockActual(String stockActual) {
        this.stockActual = stockActual;
    }

    public void setStockMinimo(String stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public void setCostoUnidad(double costoUnidad) {
        this.costoUnidad = costoUnidad;
    }

    public void setFechaReposicion(String fechaReposicion) {
        this.fechaReposicion = fechaReposicion;
    }
}
