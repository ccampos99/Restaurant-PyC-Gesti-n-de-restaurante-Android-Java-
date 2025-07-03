package com.peter.restauranteproyecto.cook;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.model.Plato;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class EditarPlatoDialogFragment extends DialogFragment {

    private static final String ARG_PLATO = "plato";

    public interface OnPlatoEditadoListener {
        void onPlatoEditado(Plato actualizado);
    }

    private OnPlatoEditadoListener listener;
    private Plato platoOriginal;

    public static EditarPlatoDialogFragment newInstance(Plato plato) {
        EditarPlatoDialogFragment fragment = new EditarPlatoDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLATO, plato);
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnPlatoEditadoListener(OnPlatoEditadoListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_agregar_plato, null);

        // Recuperar objeto Plato
        platoOriginal = (Plato) getArguments().getSerializable(ARG_PLATO);

        EditText editNombre = view.findViewById(R.id.edit_nombre_plato);
        EditText editPrecio = view.findViewById(R.id.edit_precio);
        EditText editDescripcion = view.findViewById(R.id.edit_descripcion);
        EditText editTiempo = view.findViewById(R.id.edit_tiempo_preparacion);
        Spinner spinnerCategoria = view.findViewById(R.id.spinner_categoria_dialog);
        Spinner spinnerEstado = view.findViewById(R.id.spinner_estado_dialog);
        Button btnCancelar = view.findViewById(R.id.btn_cancelar_plato);
        Button btnActualizar = view.findViewById(R.id.btn_agregar_plato);

        List<String> categorias = Arrays.asList("Platos Principales", "Entrantes", "Postres");
        List<String> estados = Arrays.asList("Disponible", "Temporada", "No Disponible");

        spinnerCategoria.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, categorias));
        spinnerEstado.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, estados));

        // Llenar campos
        if (platoOriginal != null) {
            editNombre.setText(platoOriginal.nombre);
            editPrecio.setText(String.valueOf(platoOriginal.precio));
            editDescripcion.setText(platoOriginal.descripcion);
            editTiempo.setText(String.valueOf(platoOriginal.tiempoPrep));
            spinnerCategoria.setSelection(categorias.indexOf(platoOriginal.categoria));
            spinnerEstado.setSelection(estados.indexOf(platoOriginal.estado));
        }

        btnActualizar.setText("Actualizar");

        btnCancelar.setOnClickListener(v -> dismiss());

        btnActualizar.setOnClickListener(v -> {
            String nombre = editNombre.getText().toString().trim();
            String descripcion = editDescripcion.getText().toString().trim();
            String categoria = spinnerCategoria.getSelectedItem().toString();
            String estado = spinnerEstado.getSelectedItem().toString();
            String precioStr = editPrecio.getText().toString().trim();
            String tiempoStr = editTiempo.getText().toString().trim();

            if (!nombre.isEmpty() && !descripcion.isEmpty() && !precioStr.isEmpty() && !tiempoStr.isEmpty()) {
                double precio = Double.parseDouble(precioStr);
                int tiempo = Integer.parseInt(tiempoStr);

                Plato actualizado = new Plato(nombre, descripcion, categoria, precio, estado, tiempo);
                if (listener != null) listener.onPlatoEditado(actualizado);
                dismiss();
            }
        });

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
