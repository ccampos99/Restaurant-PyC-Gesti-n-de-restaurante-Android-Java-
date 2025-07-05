package com.peter.restauranteproyecto.waiter.ui.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.common.data.DatabaseHelper;
import com.peter.restauranteproyecto.common.model.Pedido;
import com.peter.restauranteproyecto.waiter.ui.adapter.PedidoAdapter;
import com.peter.restauranteproyecto.waiter.ui.dialog.NuevoPedidoDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PedidosMeseroFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private List<Pedido> listaCompleta = new ArrayList<>();
    private PedidoAdapter adapter;
    private String estadoSeleccionado = "Todos los estados";
    private String prioridadSeleccionada = "Todas las prioridades";
    private RecyclerView recyclerView;

    public PedidosMeseroFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedidos_waiter, container, false);

        dbHelper = new DatabaseHelper(getContext());

        recyclerView = view.findViewById(R.id.recycler_pedidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Spinner spinnerEstado = view.findViewById(R.id.spinner_estado);
        Spinner spinnerPrioridad = view.findViewById(R.id.spinner_prioridad);

        List<String> estados = Arrays.asList("Todos los estados", "Pendiente", "Preparando", "Listo", "Servido");
        List<String> prioridades = Arrays.asList("Todas las prioridades", "Alta", "Media", "Baja");

        spinnerEstado.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, estados));
        spinnerPrioridad.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, prioridades));

        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estadoSeleccionado = estados.get(position);
                filtrarPedidos();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerPrioridad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prioridadSeleccionada = prioridades.get(position);
                filtrarPedidos();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        FloatingActionButton btnNuevoPedido = view.findViewById(R.id.btn_nuevo_pedido);
        btnNuevoPedido.setOnClickListener(v -> {
            NuevoPedidoDialogFragment dialog = new NuevoPedidoDialogFragment();
            dialog.setOnPedidoCreadoListener(nuevo -> {
                dbHelper.insertarPedido(nuevo); // guarda en SQLite
                cargarPedidos(); // vuelve a cargar todo
            });
            dialog.show(getParentFragmentManager(), "NuevoPedidoDialog");
        });

        cargarPedidos(); // carga al iniciar

        return view;
    }

    private void cargarPedidos() {
        listaCompleta = dbHelper.obtenerTodosLosPedidos();
        filtrarPedidos();
    }

    private void filtrarPedidos() {
        List<Pedido> filtrados = new ArrayList<>();
        for (Pedido p : listaCompleta) {
            boolean coincideEstado = estadoSeleccionado.equals("Todos los estados") || p.getEstado().equalsIgnoreCase(estadoSeleccionado);
            boolean coincidePrioridad = prioridadSeleccionada.equals("Todas las prioridades") || p.getPrioridad().equalsIgnoreCase(prioridadSeleccionada);
            if (coincideEstado && coincidePrioridad) {
                filtrados.add(p);
            }
        }
        adapter = new PedidoAdapter(filtrados);
        recyclerView.setAdapter(adapter);
    }
}
