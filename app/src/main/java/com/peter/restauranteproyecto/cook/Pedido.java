package com.peter.restauranteproyecto.cook;

import java.util.List;

public class Pedido {
    public String id;
    public String mesa;
    public List<String> articulos;
    public String estado;
    public String horaPedido;
    public String estimado;
    public double total;
    public String prioridad;

    public Pedido(String id, String mesa, List<String> articulos, String estado, String horaPedido, String estimado, double total, String prioridad) {
        this.id = id;
        this.mesa = mesa;
        this.articulos = articulos;
        this.estado = estado;
        this.horaPedido = horaPedido;
        this.estimado = estimado;
        this.total = total;
        this.prioridad = prioridad;
    }
}
