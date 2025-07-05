package com.peter.restauranteproyecto.cook.ui.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.common.utils.DashboardUtils;
import com.peter.restauranteproyecto.common.viewmodel.DashboardViewModel;

public class InicioFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        TextView pedidosCount = view.findViewById(R.id.text_pedidos_count);
        TextView ingredientesCount = view.findViewById(R.id.text_ingredientes_count);
        TextView menuCount = view.findViewById(R.id.text_menu_count);
        TextView proveedoresCount = view.findViewById(R.id.text_proveedores_count);

        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        // 👇 Observa si se solicitó una actualización
        dashboardViewModel.getActualizarDashboard().observe(getViewLifecycleOwner(), update -> {
            if (Boolean.TRUE.equals(update)) {
                // Vuelve a contar los datos
                int pedidosPendientes = DashboardUtils.contarPedidosPendientes();
                int ingredientesBajos = DashboardUtils.contarIngredientesBajos();
                int platosDisponibles = DashboardUtils.contarPlatosDisponibles();
                int proveedoresActivos = DashboardUtils.contarProveedoresActivos();

                pedidosCount.setText(String.valueOf(pedidosPendientes));
                ingredientesCount.setText(String.valueOf(ingredientesBajos));
                menuCount.setText(String.valueOf(platosDisponibles));
                proveedoresCount.setText(String.valueOf(proveedoresActivos));

                dashboardViewModel.actualizarCompletada();
            }
        });

        return view;
    }
}
