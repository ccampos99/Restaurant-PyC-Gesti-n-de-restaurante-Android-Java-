package com.peter.restauranteproyecto.common.model;

import java.util.List;

public class Pedido {
    private String id;
    private String mesa;
    private List<String> articulos;
    private String estado;
    private String horaPedido;
    private String estimado;
    private double total;

    private String prioridad;
    private String mesero;



    public Pedido(String id, String mesa, List<String> articulos, String estado, String horaPedido, String estimado, double total, String prioridad, String mesero) {
        this.id = id;
        this.mesa = mesa;
        this.articulos = articulos;
        this.estado = estado;
        this.horaPedido = horaPedido;
        this.estimado = estimado;
        this.total = total;
        this.prioridad = prioridad;
        this.mesero = mesero;
    }
    public String getMesero() {
        return mesero;
    }

    public void setMesero(String mesero) {
        this.mesero = mesero;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<String> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<String> articulos) {
        this.articulos = articulos;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public String getHoraPedido() {
        return horaPedido;
    }

    public void setHoraPedido(String horaPedido) {
        this.horaPedido = horaPedido;
    }

    public String getEstimado() {
        return estimado;
    }

    public void setEstimado(String estimado) {
        this.estimado = estimado;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
}
