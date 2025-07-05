package com.peter.restauranteproyecto.cook.ui.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peter.restauranteproyecto.R;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.peter.restauranteproyecto.cook.ui.dialog.AgregarPlatoDialogFragment;
import com.peter.restauranteproyecto.cook.ui.adapter.MenuAdapter;
import com.peter.restauranteproyecto.common.data.DataRepository;
import com.peter.restauranteproyecto.common.model.Plato;
import com.peter.restauranteproyecto.common.viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuFragment extends Fragment {

    private List<Plato> listaCompleta = new ArrayList<>();
    private MenuAdapter adapter;
    private String categoriaSeleccionada = "Todas las categorias";
    private String estadoSeleccionado = "Todos los estados";

    public MenuFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // ✅ Sincronizar con DataRepository
        DataRepository.platos.clear();
        DataRepository.platos.add(new Plato("Paella Valenciana", "Arroz con pollo, conejo, judías verdes y azafrán", "Platos Principales", 18.5, "Disponible", 25));
        DataRepository.platos.add(new Plato("Gazpacho Andaluz", "Sopa fría de tomate con verduras frescas", "Entrantes", 8.0, "Disponible", 5));
        DataRepository.platos.add(new Plato("Tarta de Santiago", "Tarta tradicional gallega de almendra", "Postres", 6.5, "Temporada", 10));
        DataRepository.platos.add(new Plato("Pulpo a la Gallega", "Pulpo cocido con patatas, pimentón y aceite de oliva", "Entrantes", 14.0, "No Disponible", 15));

        // Copiar a lista local para filtros
        listaCompleta.clear();
        listaCompleta.addAll(DataRepository.platos);


        new ViewModelProvider(requireActivity())
                .get(DashboardViewModel.class)
                .notificarActualizacion();
        adapter = new MenuAdapter(listaCompleta);
        recyclerView.setAdapter(adapter);



        Spinner spinnerCategoria = view.findViewById(R.id.spinner_categoria);
        Spinner spinnerEstado = view.findViewById(R.id.spinner_estado);
        EditText inputBuscar = view.findViewById(R.id.edit_buscar_plato);

        List<String> categorias = Arrays.asList("Todas las categorias", "Platos Principales", "Entrantes", "Postres");
        List<String> estados = Arrays.asList("Todos los estados", "Disponible", "Temporada", "No Disponible");


        Button btnAgregar = view.findViewById(R.id.btn_agregar);
        btnAgregar.setOnClickListener(v -> {
            AgregarPlatoDialogFragment dialog = new AgregarPlatoDialogFragment();
            dialog.setOnPlatoAgregadoListener(nuevo -> {
                listaCompleta.add(nuevo);
                DataRepository.platos.add(nuevo); // ✅ también agregar aquí
                filtrarPlatos(inputBuscar.getText().toString());
            });
            dialog.show(getParentFragmentManager(), "AgregarPlatoDialog");
        });

        spinnerCategoria.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, categorias));
        spinnerEstado.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, estados));

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoriaSeleccionada = categorias.get(position);
                filtrarPlatos(inputBuscar.getText().toString());
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estadoSeleccionado = estados.get(position);
                filtrarPlatos(inputBuscar.getText().toString());
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });

        inputBuscar.setOnEditorActionListener((v, actionId, event) -> {
            filtrarPlatos(inputBuscar.getText().toString());
            return true;
        });

        return view;
    }

    private void filtrarPlatos(String query) {
        List<Plato> filtrados = new ArrayList<>();
        for (Plato p : listaCompleta) {
            boolean coincideCategoria = categoriaSeleccionada.equals("Todas las categorias") || p.getCategoria().equalsIgnoreCase(categoriaSeleccionada);
            boolean coincideEstado = estadoSeleccionado.equals("Todos los estados") || p.getEstado().equalsIgnoreCase(estadoSeleccionado);
            boolean coincideTexto = p.getNombre().toLowerCase().contains(query.toLowerCase());
            if (coincideCategoria && coincideEstado && coincideTexto) {
                filtrados.add(p);
            }
        }
        adapter = new MenuAdapter(filtrados);
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_menu);
        recyclerView.setAdapter(adapter);
    }
}
