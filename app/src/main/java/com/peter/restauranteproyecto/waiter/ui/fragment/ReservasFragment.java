package com.peter.restauranteproyecto.waiter.ui.fragment;

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
import com.peter.restauranteproyecto.common.data.DatabaseHelper;
import com.peter.restauranteproyecto.common.model.Reserva;
import com.peter.restauranteproyecto.common.viewmodel.DashboardViewModel;
import com.peter.restauranteproyecto.waiter.ui.adapter.ReservasAdapter;
import com.peter.restauranteproyecto.waiter.ui.dialog.NuevaReservaDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ReservasFragment extends Fragment {

    private final List<Reserva> listaCompleta = new ArrayList<>();
    private ReservasAdapter adapter;
    private String estadoSeleccionado = "Todos los estados";
    private String fechaSeleccionada = "Todas las fechas";
    private DatabaseHelper dbHelper;

    public ReservasFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservas, container, false);

        dbHelper = new DatabaseHelper(requireContext());

        EditText inputBuscar = view.findViewById(R.id.edit_buscar_reserva);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_reservas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cargarReservas();

        adapter = new ReservasAdapter(listaCompleta, getParentFragmentManager(), new ReservasAdapter.OnReservaAccionListener() {
            @Override
            public void onActualizarEstado(String codigo, String nuevoEstado) {
                dbHelper.actualizarEstadoReserva(codigo, nuevoEstado);
                cargarReservas();
            }

            @Override
            public void onEliminarReserva(String codigo) {
                dbHelper.eliminarReservaPorCodigo(codigo);
                cargarReservas();
            }
        });

        recyclerView.setAdapter(adapter);

        Spinner spinnerEstado = view.findViewById(R.id.spinner_estado);
        Spinner spinnerFecha = view.findViewById(R.id.spinner_fecha);

        List<String> estados = Arrays.asList("Todos los estados", "Pendiente", "Confirmada", "Completada", "Cancelada", "No Asistió");
        List<String> fechas = Arrays.asList("Todas las fechas", "Hoy", "Mañana", "Esta semana");

        // ✅ CORRECCIÓN: usamos layout adecuado para visibilidad
        ArrayAdapter<String> estadoAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, estados);
        estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(estadoAdapter);

        ArrayAdapter<String> fechaAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, fechas);
        fechaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFecha.setAdapter(fechaAdapter);

        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estadoSeleccionado = estados.get(position);
                cargarReservas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerFecha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fechaSeleccionada = fechas.get(position);
                cargarReservas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        inputBuscar.setOnEditorActionListener((v, actionId, event) -> {
            filtrarReservas(inputBuscar.getText().toString());
            return true;
        });

        Button btnNuevaReserva = view.findViewById(R.id.btn_agregar_reserva);
        btnNuevaReserva.setOnClickListener(v -> {
            NuevaReservaDialogFragment dialog = new NuevaReservaDialogFragment();
            dialog.setOnReservaCreadaListener(nueva -> {
                dbHelper.insertarReserva(nueva);
                cargarReservas();
            });
            dialog.show(getParentFragmentManager(), "NuevaReservaDialog");
        });

        return view;
    }

    private void cargarReservas() {
        listaCompleta.clear();
        listaCompleta.addAll(dbHelper.obtenerTodasLasReservas());
        if (adapter != null) filtrarReservas(""); // Filtra en base al filtro actual
        new ViewModelProvider(requireActivity()).get(DashboardViewModel.class).notificarActualizacion();
    }

    private void filtrarReservas(String query) {
        List<Reserva> filtradas = new ArrayList<>();

        Calendar hoy = Calendar.getInstance();
        Calendar manana = Calendar.getInstance();
        manana.add(Calendar.DAY_OF_YEAR, 1);

        Calendar inicioSemana = Calendar.getInstance();
        inicioSemana.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Calendar finSemana = Calendar.getInstance();
        finSemana.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        for (Reserva r : listaCompleta) {
            boolean coincideEstado = estadoSeleccionado.equals("Todos los estados") || r.getEstado().equalsIgnoreCase(estadoSeleccionado);
            boolean coincideTexto = query.isEmpty() || r.getCliente().toLowerCase().contains(query.toLowerCase());
            boolean coincideFecha = true;

            // Evaluar filtro de fecha
            if (!fechaSeleccionada.equals("Todas las fechas")) {
                try {
                    // Suponiendo que la fecha está en formato yyyy-MM-dd
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date fechaReserva = sdf.parse(r.getFecha());
                    Calendar fechaRes = Calendar.getInstance();
                    fechaRes.setTime(fechaReserva);

                    switch (fechaSeleccionada) {
                        case "Hoy":
                            coincideFecha = esMismaFecha(fechaRes, hoy);
                            break;
                        case "Mañana":
                            coincideFecha = esMismaFecha(fechaRes, manana);
                            break;
                        case "Esta semana":
                            coincideFecha = (fechaRes.compareTo(inicioSemana) >= 0 && fechaRes.compareTo(finSemana) <= 0);
                            break;
                    }

                } catch (ParseException e) {
                    coincideFecha = false;
                }
            }

            if (coincideEstado && coincideTexto && coincideFecha) {
                filtradas.add(r);
            }
        }

        adapter.actualizarLista(filtradas);
    }

    private boolean esMismaFecha(Calendar c1, Calendar c2) {
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

}
