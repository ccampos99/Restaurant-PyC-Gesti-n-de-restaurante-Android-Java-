package com.peter.restauranteproyecto.common.model;

public class Reserva {

    private String codigo;         // Ej: RES-001
    private String cliente;        // Nombre o empresa
    private String fecha;          // Formato: yyyy-MM-dd
    private String hora;           // Formato: HH:mm
    private int comensales;        // Número de personas
    private String mesa;           // Ej: Mesa 6 o "" si no asignada
    private String estado;         // Pendiente, Confirmada, Completada, Cancelada, No Asistió
    private String solicitudes;    // Comentarios especiales
    private String mesero;         // Nombre del mesero asignado

    public Reserva(String codigo, String cliente, String fecha, String hora, int comensales, String mesa, String estado, String solicitudes, String mesero) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.fecha = fecha;
        this.hora = hora;
        this.comensales = comensales;
        this.mesa = mesa;
        this.estado = estado;
        this.solicitudes = solicitudes;
        this.mesero = mesero;
    }

    // Getters
    public String getCodigo() { return codigo; }
    public String getCliente() { return cliente; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public int getComensales() { return comensales; }
    public String getMesa() { return mesa; }
    public String getEstado() { return estado; }
    public String getSolicitudes() { return solicitudes; }
    public String getMesero() { return mesero; }

    // Setters
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setHora(String hora) { this.hora = hora; }
    public void setComensales(int comensales) { this.comensales = comensales; }
    public void setMesa(String mesa) { this.mesa = mesa; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setSolicitudes(String solicitudes) { this.solicitudes = solicitudes; }
    public void setMesero(String mesero) { this.mesero = mesero; }
}
