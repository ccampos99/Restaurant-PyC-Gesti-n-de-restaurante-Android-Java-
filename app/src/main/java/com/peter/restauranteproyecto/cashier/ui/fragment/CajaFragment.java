package com.peter.restauranteproyecto.cashier.ui.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.common.data.DataRepository;

public class CajaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_caja, container, false);

        setupResumen(view, R.id.card_reservas, "Reservas", DataRepository.reservas.size());
        setupResumen(view, R.id.card_pedidos, "Pedidos", DataRepository.pedidos.size());
        setupResumen(view, R.id.card_platos, "Platos", DataRepository.platos.size());
        setupResumen(view, R.id.card_proveedores, "Proveedores", DataRepository.proveedores.size());
        setupResumen(view, R.id.card_ingredientes, "Ingredientes", DataRepository.ingredientes.size());
        setupResumen(view, R.id.card_empleados, "Empleados", DataRepository.empleados.size());

        return view;
    }

    private void setupResumen(View parent, int cardId, String titulo, int total) {
        View card = parent.findViewById(cardId);
        TextView textTitulo = card.findViewById(R.id.text_titulo);
        TextView textTotal = card.findViewById(R.id.text_total);
        textTitulo.setText(titulo);
        textTotal.setText(String.valueOf(total));
    }
}

