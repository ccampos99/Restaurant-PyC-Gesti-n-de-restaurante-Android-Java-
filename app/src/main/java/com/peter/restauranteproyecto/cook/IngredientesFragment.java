package com.peter.restauranteproyecto.cook;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peter.restauranteproyecto.R;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.model.Ingredientes;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IngredientesFragment extends Fragment {

    private List<Ingredientes> listaCompleta = new ArrayList<>();
    private IngredientesAdapter adapter;
    private String categoriaSeleccionada = "Todas las categorías";
    private String estadoSeleccionado = "Todos los estados";

    public IngredientesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredientes, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_ingredientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Datos de ejemplo
        listaCompleta.add(new Ingredientes("Tomate", "Verduras", "25", "10", "En Stock", "Verduras Frescas SA", 2.50, "2024-01-15"));
        listaCompleta.add(new Ingredientes("Aceite de Oliva", "Aceites", "0", "5", "Sin Stock", "Aceites Premium", 12.00, "2024-01-05"));
        listaCompleta.add(new Ingredientes("Azafrán", "Especias", "2", "5", "Stock Bajo", "Especias Gourmet", 45.00, "2024-01-08"));

        adapter = new IngredientesAdapter(listaCompleta, getParentFragmentManager(), (position, cantidadAgregada) -> {
            Ingredientes ingrediente = listaCompleta.get(position);

            double stockActual = Double.parseDouble(ingrediente.getStockActual());
            double nuevoStock = stockActual + cantidadAgregada;
            double stockMinimo = Double.parseDouble(ingrediente.getStockMinimo());

            // Actualiza stock y estado automáticamente
            ingrediente.setStockActual(String.valueOf(nuevoStock));

            // Aquí aplica la lógica del estado directamente
            String nuevoEstado;
            if (nuevoStock <= 0) {
                nuevoEstado = "Sin Stock";
            } else if (nuevoStock <= stockMinimo) {
                nuevoEstado = "Stock Bajo";
            } else {
                nuevoEstado = "En Stock";
            }
            ingrediente.setEstado(nuevoEstado);

            filtrarIngredientes(""); // actualiza lista con filtros aplicados
        });
        recyclerView.setAdapter(adapter);

        // Filtros y búsqueda
        Spinner spinnerCategoria = view.findViewById(R.id.spinner_categoria);
        Spinner spinnerEstado = view.findViewById(R.id.spinner_estado);
        EditText inputBuscar = view.findViewById(R.id.edit_buscar_ingrediente);
        Button btnAgregar = view.findViewById(R.id.btn_agregar_ingrediente);

        List<String> categorias = Arrays.asList("Todas las categorías", "Verduras", "Cereales", "Aceites", "Carnes", "Especias");
        List<String> estados = Arrays.asList("Todos los estados", "En Stock", "Stock Bajo", "Sin Stock");

        spinnerCategoria.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, categorias));
        spinnerEstado.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, estados));

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoriaSeleccionada = categorias.get(position);
                filtrarIngredientes(inputBuscar.getText().toString());
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estadoSeleccionado = estados.get(position);
                filtrarIngredientes(inputBuscar.getText().toString());
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        inputBuscar.setOnEditorActionListener((v, actionId, event) -> {
            filtrarIngredientes(inputBuscar.getText().toString());
            return true;
        });

        btnAgregar.setOnClickListener(v -> {
            AgregarIngredienteDialogFragment dialog = new AgregarIngredienteDialogFragment();

            dialog.setOnIngredienteAgregadoListener(nuevo -> {
                listaCompleta.add(nuevo);
                filtrarIngredientes(inputBuscar.getText().toString()); // actualiza lista
            });

            dialog.show(getParentFragmentManager(), "AgregarIngredienteDialog");
        });

        return view;
    }

    private void filtrarIngredientes(String query) {
        List<Ingredientes> filtrados = new ArrayList<>();
        for (Ingredientes i : listaCompleta) {
            boolean coincideCategoria = categoriaSeleccionada.equals("Todas las categorías") || i.getCategoria().equalsIgnoreCase(categoriaSeleccionada);
            boolean coincideEstado = estadoSeleccionado.equals("Todos los estados") || i.getEstado().equalsIgnoreCase(estadoSeleccionado);
            boolean coincideTexto = i.getNombre().toLowerCase().contains(query.toLowerCase());

            if (coincideCategoria && coincideEstado && coincideTexto) {
                filtrados.add(i);
            }
        }

        adapter.actualizarLista(filtrados);
    }

}