package com.peter.restauranteproyecto.cook.ui.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.cook.ui.adapter.PedidoAdapter;
import com.peter.restauranteproyecto.common.data.DataRepository;
import com.peter.restauranteproyecto.common.model.Pedido;
import com.peter.restauranteproyecto.common.viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PedidosFragment extends Fragment {

    private List<Pedido> listaCompleta = new ArrayList<>();
    private PedidoAdapter adapter;
    private String estadoSeleccionado = "Todos los estados";
    private String prioridadSeleccionada = "Todas las prioridades";

    public PedidosFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_pedidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Limpiar y agregar a DataRepository
        DataRepository.pedidos.clear();

        // Datos de prueba
        DataRepository.pedidos.add(crearPedido("ORD-001", "Mesa 5", Arrays.asList("Paella Valenciana", "Gazpacho", "Sangría"), "Preparando", "14:30", 15, 32.50, "María García"));
        DataRepository.pedidos.add(crearPedido("ORD-002", "Mesa 3", Arrays.asList("Pulpo a la Gallega", "Vino Tinto"), "Pendiente", "14:45", 25, 18.00, "Carlos López"));
        DataRepository.pedidos.add(crearPedido("ORD-003", "Mesa 8", Arrays.asList("Tarta de Santiago", "Café"), "Listo", "15:00", 40, 8.50, "Pedro Ruiz"));
        DataRepository.pedidos.add(crearPedido("ORD-004", "Mesa 12", Arrays.asList("Jamón Ibérico", "Pan con Tomate", "Cerveza"), "Servido", "13:15", 5, 24.00, "Ana Martín"));


        listaCompleta = DataRepository.pedidos;

        new ViewModelProvider(requireActivity())
                .get(DashboardViewModel.class)
                .notificarActualizacion();
        adapter = new PedidoAdapter(listaCompleta);
        recyclerView.setAdapter(adapter);

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
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinnerPrioridad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prioridadSeleccionada = prioridades.get(position);
                filtrarPedidos();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });

        return view;
    }

    private Pedido crearPedido(String id, String mesa, List<String> articulos, String estado, String hora, int minutosRestantes, double total, String mesero) {
        String prioridad;
        if (minutosRestantes <= 10) prioridad = "Alta";
        else if (minutosRestantes <= 20) prioridad = "Media";
        else prioridad = "Baja";
        return new Pedido(id, mesa, articulos, estado, hora, minutosRestantes + " min", total, prioridad, mesero);
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
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_pedidos);
        recyclerView.setAdapter(adapter);
    }
}
