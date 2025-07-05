package com.peter.restauranteproyecto.cook.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.peter.restauranteproyecto.common.model.Proveedor;

import java.util.Arrays;
import java.util.List;

public class AgregarProveedorDialogFragment extends DialogFragment {

    public interface OnProveedorAgregadoListener {
        void onProveedorAgregado(Proveedor nuevo);
    }

    private OnProveedorAgregadoListener listener;

    public void setOnProveedorAgregadoListener(OnProveedorAgregadoListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_agregar_proveedor, null);

        EditText inputNombre = view.findViewById(R.id.input_nombre);
        EditText inputTelefono = view.findViewById(R.id.input_telefono);
        EditText inputEmail = view.findViewById(R.id.input_email);
        EditText inputDireccion = view.findViewById(R.id.input_direccion);
        EditText inputEntrega = view.findViewById(R.id.input_entrega);

        Spinner spinnerCategoria = view.findViewById(R.id.spinner_categoria);
        Spinner spinnerEstado = view.findViewById(R.id.spinner_estado);
        Spinner spinnerCalificacion = view.findViewById(R.id.spinner_calificacion);

        Button btnCancelar = view.findViewById(R.id.btn_cancelar_proveedor);
        Button btnAgregar = view.findViewById(R.id.btn_agregar_proveedor);

        List<String> categorias = Arrays.asList("Verduras y Frutas", "Carnes y Aves", "Lácteos", "Aceites y Condimentos");
        List<String> estados = Arrays.asList("Activo", "Inactivo", "Pendiente");
        List<String> calificaciones = Arrays.asList("1 - Malo", "2 - Regular", "3 - Bueno", "4 - Muy Bueno", "5 - Excelente");

        spinnerCategoria.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, categorias));
        spinnerEstado.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, estados));
        spinnerCalificacion.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, calificaciones));

        btnCancelar.setOnClickListener(v -> dismiss());

        btnAgregar.setOnClickListener(v -> {
            String nombre = inputNombre.getText().toString().trim();
            String telefono = inputTelefono.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String direccion = inputDireccion.getText().toString().trim();
            String entrega = inputEntrega.getText().toString().trim();

            String categoria = spinnerCategoria.getSelectedItem().toString();
            String estado = spinnerEstado.getSelectedItem().toString();
            int calificacionIndex = spinnerCalificacion.getSelectedItemPosition();
            double calificacion = calificacionIndex + 1;

            if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(telefono) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(direccion) && !TextUtils.isEmpty(entrega)) {
                Proveedor nuevo = new Proveedor(nombre, "", categoria, telefono, email, direccion, calificacion, estado, 0, "2025-07-04", entrega + "h");
                if (listener != null) {
                    listener.onProveedorAgregado(nuevo);
                }
                dismiss();
            }
        });

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}

