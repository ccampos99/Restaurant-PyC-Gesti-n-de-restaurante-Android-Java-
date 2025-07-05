package com.peter.restauranteproyecto.waiter.ui.fragment;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.common.utils.DashboardUtils;
import com.peter.restauranteproyecto.common.viewmodel.DashboardViewModel;

public class WaiterInicioFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio_waiter, container, false);

        // Referencias a los TextViews
        TextView mesasCount = view.findViewById(R.id.text_mesas_count);
        TextView pedidosCount = view.findViewById(R.id.text_pedidos_count);
        TextView reservasCount = view.findViewById(R.id.text_reservas_count);
        TextView personalCount = view.findViewById(R.id.text_personal_count);

        // Obtener ViewModel
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        // Observar si se debe actualizar el dashboard
        dashboardViewModel.getActualizarDashboard().observe(getViewLifecycleOwner(), update -> {
            if (Boolean.TRUE.equals(update)) {

                // Cargar datos (puedes reemplazar con llamadas a tu repositorio o backend)
                int mesasOcupadas = DashboardUtils.contarMesasOcupadas(); // Ejemplo: 8
                int totalMesas = DashboardUtils.contarMesasTotales();     // Ejemplo: 12
                int pedidosActivos = DashboardUtils.contarPedidosActivos();
                int reservasHoy = DashboardUtils.contarReservasHoy();
                int personalActivo = DashboardUtils.contarPersonalEnTurno();

                // Mostrar datos en la UI
                mesasCount.setText(mesasOcupadas + "/" + totalMesas);
                pedidosCount.setText(String.valueOf(pedidosActivos));
                reservasCount.setText(String.valueOf(reservasHoy));
                personalCount.setText(String.valueOf(personalActivo));

                // Finalizar actualización
                dashboardViewModel.actualizarCompletada();
            }
        });

        return view;
    }
}
