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
import com.peter.restauranteproyecto.common.model.Mesa;
import com.peter.restauranteproyecto.waiter.ui.adapter.MesaAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MesasFragment extends Fragment {

    private List<Mesa> listaCompleta = new ArrayList<>();
    private MesaAdapter adapter;
    private String estadoSeleccionado = "Todos los estados";
    private String meseroSeleccionado = "Todos los meseros";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mesas_waiter, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_mesas);
        Spinner spinnerEstado = view.findViewById(R.id.spinner_estado_mesa);
        Spinner spinnerMesero = view.findViewById(R.id.spinner_mesero);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listaCompleta = obtenerDatosDummy();
        adapter = new MesaAdapter(new ArrayList<>(listaCompleta));
        recyclerView.setAdapter(adapter);

        List<String> estados = Arrays.asList("Todos los estados", "Libre", "Ocupada", "Reservada", "Limpieza");
        List<String> meseros = new ArrayList<>();
        meseros.add("Todos los meseros");
        for (Mesa m : listaCompleta) {
            if (!meseros.contains(m.getMesero())) {
                meseros.add(m.getMesero());
            }
        }

        spinnerEstado.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, estados));
        spinnerMesero.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, meseros));

        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estadoSeleccionado = estados.get(position);
                filtrarMesas();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinnerMesero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                meseroSeleccionado = meseros.get(position);
                filtrarMesas();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });

        return view;
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

    private List<Mesa> obtenerDatosDummy() {
        List<Mesa> lista = new ArrayList<>();
        lista.add(new Mesa("Mesa 1", 4, "Ocupada", "María García", "3/4", "Pedido: 14:30"));
        lista.add(new Mesa("Mesa 2", 2, "Libre", "Carlos López", "-", "-"));
        lista.add(new Mesa("Mesa 3", 6, "Reservada", "Ana Martín", "-", "Reserva: 16:00\nFamilia Rodríguez"));
        lista.add(new Mesa("Mesa 4", 4, "Limpieza", "Pedro Ruiz", "-", "-"));
        lista.add(new Mesa("Mesa 5", 8, "Ocupada", "María García", "6/8", "Pedido: 13:45"));
        return lista;
    }
}
