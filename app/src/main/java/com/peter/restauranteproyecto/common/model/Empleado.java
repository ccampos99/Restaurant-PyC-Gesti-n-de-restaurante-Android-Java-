package com.peter.restauranteproyecto.common.model;

import java.util.List;

public class Empleado {
    private String nombre;
    private String fechaIngreso;
    private String rol;
    private String email;
    private String telefono;
    private String estado;       // "Activo", "En Descanso"
    private String turno;        // "Mañana", "Tarde", "Noche"
    private List<String> horario; // Días laborales: "L", "M", "X", "J", "V", "S", "D"
    private double rendimiento;  // Ej: 4.5
    private double salario;

    public Empleado(String nombre, String fechaIngreso, String rol, String email, String telefono,
                    String estado, String turno, List<String> horario,
                    double rendimiento, double salario) {
        this.nombre = nombre;
        this.fechaIngreso = fechaIngreso;
        this.rol = rol;
        this.email = email;
        this.telefono = telefono;
        this.estado = estado;
        this.turno = turno;
        this.horario = horario;
        this.rendimiento = rendimiento;
        this.salario = salario;
    }

    // Getters y setters (puedes generarlos con tu IDE)
    public String getNombre() { return nombre; }
    public String getFechaIngreso() { return fechaIngreso; }
    public String getRol() { return rol; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public String getEstado() { return estado; }
    public String getTurno() { return turno; }
    public List<String> getHorario() { return horario; }
    public double getRendimiento() { return rendimiento; }
    public double getSalario() { return salario; }

    public void setEstado(String estado) { this.estado = estado; }
}
