package com.peter.restauranteproyecto.common.utils;

import com.peter.restauranteproyecto.common.data.DataRepository;

public class DashboardUtils {

    // cook
    public static int contarPedidosPendientes() {
        return (int) DataRepository.pedidos.stream()
                .filter(p -> p.getEstado().equalsIgnoreCase("Pendiente"))
                .count();
    }

    public static int contarIngredientesBajos() {
        return (int) DataRepository.ingredientes.stream()
                .filter(i -> i.getEstado().equalsIgnoreCase("Sin Stock") ||
                        i.getEstado().equalsIgnoreCase("Stock Bajo"))
                .count();
    }

    public static int contarPlatosDisponibles() {
        return (int) DataRepository.platos.stream()
                .filter(p -> p.getEstado().equalsIgnoreCase("Disponible"))
                .count();
    }

    public static int contarProveedoresActivos() {
        return (int) DataRepository.proveedores.stream()
                .filter(p -> p.getEstado().equalsIgnoreCase("Activo"))
                .count();
    }

    // waiter
    public static int contarMesasOcupadas() {
        // Lógica temporal o mock
        return 8;
    }

    public static int contarMesasTotales() {
        return 12;
    }

    public static int contarPedidosActivos() {
        return 15;
    }

    public static int contarReservasHoy() {
        return 6;
    }

    public static int contarPersonalEnTurno() {
        return 12;
    }
}
