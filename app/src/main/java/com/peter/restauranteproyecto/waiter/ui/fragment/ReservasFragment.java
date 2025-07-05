package com.peter.restauranteproyecto.waiter.ui.fragment;

import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.common.data.DataRepository;
import com.peter.restauranteproyecto.common.model.Reserva;
import com.peter.restauranteproyecto.common.viewmodel.DashboardViewModel;
import com.peter.restauranteproyecto.waiter.ui.adapter.ReservasAdapter;
import com.peter.restauranteproyecto.waiter.ui.dialog.NuevaReservaDialogFragment;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReservasFragment extends Fragment {

    private final List<Reserva> listaCompleta = new ArrayList<>();
    private ReservasAdapter adapter;
    private String estadoSeleccionado = "Todos los estados";
    private String fechaSeleccionada = "Todas las fechas";

    public ReservasFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservas, container, false);

        EditText inputBuscar = view.findViewById(R.id.edit_buscar_reserva);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_reservas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializa con datos de ejemplo
        DataRepository.reservas.clear();
        DataRepository.reservas.add(new Reserva("RES-001", "Familia Rodríguez", "2024-01-18", "20:00", 4, "Mesa 6", "Confirmada", "Mesa cerca de la ventana", "María García"));
        DataRepository.reservas.add(new Reserva("RES-002", "Carlos Mendoza", "2024-01-18", "19:30", 2, "", "Pendiente", "Aniversario de bodas", ""));
        DataRepository.reservas.add(new Reserva("RES-003", "Empresa TechCorp", "2024-01-19", "13:00", 8, "Mesa 12", "Confirmada", "Comida de negocios", "Carlos López"));

        listaCompleta.clear();
        listaCompleta.addAll(DataRepository.reservas);

        new ViewModelProvider(requireActivity())
                .get(DashboardViewModel.class)
                .notificarActualizacion();

        adapter = new ReservasAdapter(listaCompleta, getParentFragmentManager());
        recyclerView.setAdapter(adapter);

        Spinner spinnerEstado = view.findViewById(R.id.spinner_estado);
        Spinner spinnerFecha = view.findViewById(R.id.spinner_fecha);

        List<String> estados = Arrays.asList("Todos los estados", "Pendiente", "Confirmada", "Completada", "Cancelada", "No Asistió");
        List<String> fechas = Arrays.asList("Todas las fechas", "Hoy", "Mañana", "Esta semana");

        spinnerEstado.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, estados));
        spinnerFecha.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, fechas));

        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estadoSeleccionado = estados.get(position);
                filtrarReservas(inputBuscar.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });


        spinnerFecha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fechaSeleccionada = fechas.get(position);
                filtrarReservas(inputBuscar.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // opcional
            }
        });

        inputBuscar.setOnEditorActionListener((v, actionId, event) -> {
            filtrarReservas(inputBuscar.getText().toString());
            return true;
        });

        Button btnNuevaReserva = view.findViewById(R.id.btn_agregar_reserva);
        btnNuevaReserva.setOnClickListener(v -> {
            NuevaReservaDialogFragment dialog = new NuevaReservaDialogFragment();
            dialog.setOnReservaCreadaListener(nueva -> {
                listaCompleta.add(nueva);
                filtrarReservas(inputBuscar.getText().toString());
            });
            dialog.show(getParentFragmentManager(), "NuevaReservaDialog");
        });

        return view;
    }

    private void filtrarReservas(String query) {
        List<Reserva> filtradas = new ArrayList<>();
        for (Reserva r : listaCompleta) {
            boolean coincideEstado = estadoSeleccionado.equals("Todos los estados") || r.getEstado().equalsIgnoreCase(estadoSeleccionado);
            boolean coincideTexto = r.getCliente().toLowerCase().contains(query.toLowerCase());
            // Opcional: lógica de fecha si lo deseas
            if (coincideEstado && coincideTexto) {
                filtradas.add(r);
            }
        }
        adapter.actualizarLista(filtradas);
    }
}
