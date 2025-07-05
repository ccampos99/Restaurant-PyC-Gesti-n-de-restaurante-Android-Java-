package com.peter.restauranteproyecto.cook.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.cook.ui.dialog.AgregarProveedorDialogFragment;
import com.peter.restauranteproyecto.cook.ui.adapter.ProveedoresAdapter;
import com.peter.restauranteproyecto.common.data.DataRepository;
import com.peter.restauranteproyecto.common.model.Proveedor;
import com.peter.restauranteproyecto.common.viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProveedoresFragment extends Fragment {

    private final List<Proveedor> listaCompleta = new ArrayList<>();
    private ProveedoresAdapter adapter;
    private String categoriaSeleccionada = "Todas las categorías";
    private String estadoSeleccionado = "Todos los estados";

    public ProveedoresFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proveedores, container, false);

        Spinner spinnerCategoria = view.findViewById(R.id.spinner_categoria);
        Spinner spinnerEstado = view.findViewById(R.id.spinner_estado);
        RecyclerView recycler = view.findViewById(R.id.recycler_proveedores);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        Button btnAgregar = view.findViewById(R.id.btn_agregar_proveedor);

        List<String> categorias = Arrays.asList("Todas las categorías", "Verduras y Frutas", "Carnes y Aves", "Lácteos", "Aceites y Condimentos");
        List<String> estados = Arrays.asList("Todos los estados", "Activo", "Inactivo", "Pendiente");

        spinnerCategoria.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, categorias));
        spinnerEstado.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, estados));

        // Limpiar y cargar los proveedores en el repositorio compartido
        DataRepository.proveedores.clear();
        DataRepository.proveedores.add(new Proveedor("Verduras Frescas SA", "Tomate, Lechuga, +2 más", "Verduras y Frutas", "+34 912 345 678", "pedidos@verdurasfrescas.com", "Calle Mayor 123, Madrid", 4.5, "Activo", 45, "2024-01-15", "24h"));
        DataRepository.proveedores.add(new Proveedor("Carnes Selectas", "Pollo, Ternera, +2 más", "Carnes y Aves", "+34 913 456 789", "ventas@carnesselectas.com", "Avenida de la Paz 45, Madrid", 4.8, "Activo", 32, "2024-01-16", "12h"));
        DataRepository.proveedores.add(new Proveedor("Aceites Premium", "Aceite de Oliva, Vinagre +1 más", "Aceites y Condimentos", "+34 914 567 890", "info@aceitespremium.com", "Plaza del Sol 8, Madrid", 3.3, "Inactivo", 18, "2024-01-05", "48h"));
        DataRepository.proveedores.add(new Proveedor("Lácteos del Norte", "Leche, Queso, +2 más", "Lácteos", "+34 915 678 901", "pedidos@lacteosdelnorte.com", "Calle Asturias 67, Madrid", 4.6, "Pendiente", 28, "2024-01-12", "18h"));

// Sincronizar lista local
        listaCompleta.clear();
        listaCompleta.addAll(DataRepository.proveedores);


        new ViewModelProvider(requireActivity())
                .get(DashboardViewModel.class)
                .notificarActualizacion();
        adapter = new ProveedoresAdapter(new ArrayList<>(listaCompleta));
        recycler.setAdapter(adapter);

        spinnerCategoria.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int pos, long id) {
                categoriaSeleccionada = categorias.get(pos);
                filtrar(""); // Llama al filtro cada vez que cambia la categoría
            }
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        spinnerEstado.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int pos, long id) {
                estadoSeleccionado = estados.get(pos);
                filtrar(""); // Llama al filtro cada vez que cambia el estado
            }
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });



        btnAgregar.setOnClickListener(v -> {
            AgregarProveedorDialogFragment dialog = new AgregarProveedorDialogFragment();
            dialog.setOnProveedorAgregadoListener(nuevo -> {
                listaCompleta.add(nuevo);
                DataRepository.proveedores.add(nuevo); // ✅ Agrega también al repositorio
                filtrar(""); // Reaplica el filtro
            });
            dialog.show(getParentFragmentManager(), "AgregarProveedorDialog");
        });


        return view;
    }

    private void filtrar(String texto) {
        List<Proveedor> filtrados = new ArrayList<>();
        for (Proveedor p : listaCompleta) {
            boolean coincideCategoria = categoriaSeleccionada.equals("Todas las categorías") || p.getCategoria().equalsIgnoreCase(categoriaSeleccionada);
            boolean coincideEstado = estadoSeleccionado.equals("Todos los estados") || p.getEstado().equalsIgnoreCase(estadoSeleccionado);
            boolean coincideTexto = p.getNombre().toLowerCase().contains(texto.toLowerCase());
            if (coincideCategoria && coincideEstado && coincideTexto) {
                filtrados.add(p);
            }
        }
        adapter.actualizarLista(filtrados);
    }
}
