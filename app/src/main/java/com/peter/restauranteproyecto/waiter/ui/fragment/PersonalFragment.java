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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.common.data.DataRepository;
import com.peter.restauranteproyecto.common.model.Empleado;
import com.peter.restauranteproyecto.waiter.ui.adapter.EmpleadoAdapter;
import com.peter.restauranteproyecto.waiter.ui.dialog.NuevoEmpleadoDialogFragment;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonalFragment extends Fragment {

    private List<Empleado> listaCompleta = new ArrayList<>();
    private EmpleadoAdapter adapter;
    private String rolSeleccionado = "Todos";
    private String estadoSeleccionado = "Todos";

    public PersonalFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_personal);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DataRepository.cargarEmpleadosDummy(); // Este método lo defines tú si no existe

        listaCompleta.clear();
        listaCompleta.addAll(DataRepository.empleados);

        adapter = new EmpleadoAdapter(listaCompleta, requireContext());
        recyclerView.setAdapter(adapter);

        Spinner spinnerRol = view.findViewById(R.id.spinner_rol);
        Spinner spinnerEstado = view.findViewById(R.id.spinner_estado);
        EditText inputBuscar = view.findViewById(R.id.edit_buscar_personal);

        List<String> roles = Arrays.asList("Todos", "Mesero", "Cocinero", "Administrador");
        List<String> estados = Arrays.asList("Todos", "Activo", "En Descanso");

        spinnerRol.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, roles));
        spinnerEstado.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, estados));

        spinnerRol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rolSeleccionado = roles.get(position);
                filtrar(inputBuscar.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estadoSeleccionado = estados.get(position);
                filtrar(inputBuscar.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        inputBuscar.setOnEditorActionListener((v, actionId, event) -> {
            filtrar(inputBuscar.getText().toString());
            return true;
        });

        Button btnAgregar = view.findViewById(R.id.btn_agregar_personal);
        btnAgregar.setOnClickListener(v -> {
            NuevoEmpleadoDialogFragment dialog = new NuevoEmpleadoDialogFragment();
            dialog.setOnEmpleadoCreadoListener(nuevo -> {
                listaCompleta.add(nuevo);
                DataRepository.empleados.add(nuevo);
                filtrar(inputBuscar.getText().toString());
            });
            dialog.show(getParentFragmentManager(), "NuevoEmpleadoDialog");
        });

        return view;
    }

    private void filtrar(String query) {
        List<Empleado> filtrados = new ArrayList<>();
        for (Empleado e : listaCompleta) {
            boolean coincideRol = rolSeleccionado.equals("Todos") || e.getRol().equalsIgnoreCase(rolSeleccionado);
            boolean coincideEstado = estadoSeleccionado.equals("Todos") || e.getEstado().equalsIgnoreCase(estadoSeleccionado);
            boolean coincideTexto = e.getNombre().toLowerCase().contains(query.toLowerCase());
            if (coincideRol && coincideEstado && coincideTexto) {
                filtrados.add(e);
            }
        }
        adapter = new EmpleadoAdapter(filtrados, requireContext());
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_personal);
        recyclerView.setAdapter(adapter);
    }
}