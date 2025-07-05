package com.peter.restauranteproyecto.common.data;

import com.peter.restauranteproyecto.common.model.Empleado;
import com.peter.restauranteproyecto.common.model.Ingredientes;
import com.peter.restauranteproyecto.common.model.Pedido;
import com.peter.restauranteproyecto.common.model.Plato;
import com.peter.restauranteproyecto.common.model.Proveedor;
import com.peter.restauranteproyecto.common.model.Reserva;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataRepository {
    public static final List<Reserva> reservas = new ArrayList<>();
    public static final List<Pedido> pedidos = new ArrayList<>();
    public static final List<Plato> platos = new ArrayList<>();
    public static final List<Proveedor> proveedores = new ArrayList<>();
    public static final List<Ingredientes> ingredientes = new ArrayList<>();
    public static List<Empleado> empleados = new ArrayList<>();
    public static void cargarEmpleadosDummy() {
        empleados.clear();

        empleados.add(new Empleado(
                "María García",
                "2023-03-15",
                "Mesero",
                "maria.garcia@restaurant.com",
                "+34 612 345 678",
                "Activo",
                "Tarde",
                Arrays.asList("L", "M", "X", "J", "V", "S", "D"),
                4.5,
                1800
        ));

        empleados.add(new Empleado(
                "Carlos López",
                "2023-01-20",
                "Mesero",
                "carlos.lopez@restaurant.com",
                "+34 623 456 789",
                "Activo",
                "Noche",
                Arrays.asList("L", "X", "J", "V", "S", "D"),
                4.2,
                1850
        ));

        empleados.add(new Empleado(
                "Ana Martín",
                "2023-06-10",
                "Mesero",
                "ana.martin@restaurant.com",
                "+34 634 567 890",
                "En Descanso",
                "Mañana",
                Arrays.asList("L", "M", "X", "J", "V", "S", "D"),
                4.8,
                1750
        ));
    }
}


