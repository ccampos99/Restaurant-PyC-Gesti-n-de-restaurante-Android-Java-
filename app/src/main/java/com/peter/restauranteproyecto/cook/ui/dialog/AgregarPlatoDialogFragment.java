package com.peter.restauranteproyecto.cook.ui.dialog;

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
import com.peter.restauranteproyecto.common.model.Plato;
import java.util.Arrays;
import java.util.List;

public class AgregarPlatoDialogFragment extends DialogFragment {

    public interface OnPlatoAgregadoListener {
        void onPlatoAgregado(Plato nuevoPlato);
    }

    private OnPlatoAgregadoListener listener;

    public void setOnPlatoAgregadoListener(OnPlatoAgregadoListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_agregar_plato, null);

        EditText editNombre = view.findViewById(R.id.edit_nombre_plato);
        EditText editPrecio = view.findViewById(R.id.edit_precio);
        EditText editDescripcion = view.findViewById(R.id.edit_descripcion);
        EditText editTiempo = view.findViewById(R.id.edit_tiempo_preparacion);
        Spinner spinnerCategoria = view.findViewById(R.id.spinner_categoria_dialog);
        Spinner spinnerEstado = view.findViewById(R.id.spinner_estado_dialog);
        Button btnCancelar = view.findViewById(R.id.btn_cancelar_plato);
        Button btnAgregar = view.findViewById(R.id.btn_agregar_plato);

        // Cargar opciones en los spinners
        List<String> categorias = Arrays.asList("Platos Principales", "Entrantes", "Postres");
        List<String> estados = Arrays.asList("Disponible", "Temporada", "No Disponible");

        spinnerCategoria.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, categorias));
        spinnerEstado.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, estados));

        btnCancelar.setOnClickListener(v -> dismiss());

        btnAgregar.setOnClickListener(v -> {
            String nombre = editNombre.getText().toString().trim();
            String descripcion = editDescripcion.getText().toString().trim();
            String categoria = spinnerCategoria.getSelectedItem().toString();
            String estado = spinnerEstado.getSelectedItem().toString();
            String precioStr = editPrecio.getText().toString().trim();
            String tiempoStr = editTiempo.getText().toString().trim();

            if (!nombre.isEmpty() && !descripcion.isEmpty() && !precioStr.isEmpty() && !tiempoStr.isEmpty()) {
                double precio = Double.parseDouble(precioStr);
                int tiempo = Integer.parseInt(tiempoStr);

                Plato nuevo = new Plato(nombre, descripcion, categoria, precio, estado, tiempo);

                if (listener != null) {
                    listener.onPlatoAgregado(nuevo);
                }
                dismiss();
            } else {
                // podrías mostrar un Toast si quieres avisar al usuario
            }
        });

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
