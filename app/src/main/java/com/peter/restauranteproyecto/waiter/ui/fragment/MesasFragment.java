package com.peter.restauranteproyecto.waiter.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.common.data.DatabaseHelper;
import com.peter.restauranteproyecto.common.model.Mesa;
import com.peter.restauranteproyecto.waiter.ui.adapter.MesaAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MesasFragment extends Fragment {

    private List<Mesa> listaCompleta = new ArrayList<>();
    private MesaAdapter adapter;
    private DatabaseHelper dbHelper;
    private String estadoSeleccionado = "Todos los estados";
    private String meseroSeleccionado = "Todos los meseros";

    private Spinner spinnerEstado, spinnerMesero;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mesas_waiter, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_mesas);
        spinnerEstado = view.findViewById(R.id.spinner_estado_mesa);
        spinnerMesero = view.findViewById(R.id.spinner_mesero);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DatabaseHelper(getContext());

        adapter = new MesaAdapter(new ArrayList<>(), dbHelper);
        recyclerView.setAdapter(adapter);

        List<String> estados = Arrays.asList("Todos los estados", "Libre", "Ocupada", "Reservada", "Limpieza");
        spinnerEstado.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, estados));
        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estadoSeleccionado = estados.get(position);
                filtrarMesas();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerMesero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                meseroSeleccionado = (String) parent.getItemAtPosition(position);
                filtrarMesas();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dbHelper.eliminarMesasConPrefijoMesa();
        cargarMesasDesdeBD();
    }

    private void recargarMesasDesdeBD() {
        listaCompleta = dbHelper.obtenerTodasLasMesas();
        filtrarMesas(); // Aplica los filtros de estado y mesero también
    }
    private void cargarMesasDesdeBD() {
        listaCompleta = dbHelper.obtenerTodasLasMesas();

        // Refrescar meseros únicos
        List<String> meseros = new ArrayList<>();
        meseros.add("Todos los meseros");
        for (Mesa m : listaCompleta) {
            if (!meseros.contains(m.getMesero())) {
                meseros.add(m.getMesero());
            }
        }

        // Refrescar adaptador del spinner de meseros
        if (spinnerMesero != null) {
            ArrayAdapter<String> meseroAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, meseros);
            spinnerMesero.setAdapter(meseroAdapter);
            // Mantener selección actual si aún existe
            int pos = meseros.indexOf(meseroSeleccionado);
            spinnerMesero.setSelection(pos != -1 ? pos : 0);
        }

        filtrarMesas(); // Mostrar lista con los filtros actuales
    }

    private void filtrarMesas() {
        List<Mesa> filtradas = new ArrayList<>();
        for (Mesa m : listaCompleta) {
            boolean coincideEstado = estadoSeleccionado.equals("Todos los estados") || m.getEstado().equalsIgnoreCase(estadoSeleccionado);
            boolean coincideMesero = meseroSeleccionado.equals("Todos los meseros") || m.getMesero().equalsIgnoreCase(meseroSeleccionado);
            if (coincideEstado && coincideMesero) {
                filtradas.add(m);
            }
        }
        adapter.actualizarLista(filtradas);
    }
}
