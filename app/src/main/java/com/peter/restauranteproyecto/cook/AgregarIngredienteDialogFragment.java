package com.peter.restauranteproyecto.cook;

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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.interfaces.OnIngredienteAgregadoListener;
import com.peter.restauranteproyecto.model.Ingredientes;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class AgregarIngredienteDialogFragment extends DialogFragment {

    private OnIngredienteAgregadoListener listener;

    public void setOnIngredienteAgregadoListener(OnIngredienteAgregadoListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_agregar_ingrediente, null);

        EditText inputNombre = view.findViewById(R.id.input_nombre);
        EditText inputStock = view.findViewById(R.id.input_stock);
        EditText inputMinimo = view.findViewById(R.id.input_minimo);
        EditText inputProveedor = view.findViewById(R.id.input_proveedor);
        EditText inputCosto = view.findViewById(R.id.input_costo);
        Spinner spinnerCategoria = view.findViewById(R.id.spinner_categoria);
        Spinner spinnerUnidad = view.findViewById(R.id.spinner_unidad);
        Button btnCancelar = view.findViewById(R.id.btn_cancelar_ingrediente);
        Button btnAgregar = view.findViewById(R.id.btn_agregar_ingrediente);

        List<String> categorias = Arrays.asList("Verduras", "Cereales", "Aceites", "Carnes", "Especias");
        List<String> unidades = Arrays.asList("kg", "L", "g", "ml", "u");

        spinnerCategoria.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, categorias));
        spinnerUnidad.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, unidades));

        btnCancelar.setOnClickListener(v -> dismiss());

        btnAgregar.setOnClickListener(v -> {
            String nombre = inputNombre.getText().toString().trim();
            String stock = inputStock.getText().toString().trim();
            String minimo = inputMinimo.getText().toString().trim();
            String proveedor = inputProveedor.getText().toString().trim();
            String costo = inputCosto.getText().toString().trim();
            String categoria = spinnerCategoria.getSelectedItem().toString();
            String unidad = spinnerUnidad.getSelectedItem().toString();

            if (!nombre.isEmpty() && !stock.isEmpty() && !minimo.isEmpty() && !proveedor.isEmpty() && !costo.isEmpty()) {
                double costoDouble = Double.parseDouble(costo);

                Ingredientes nuevo = new Ingredientes(
                        nombre, categoria, stock, minimo, "En Stock", proveedor, costoDouble, "2025-07-03"
                );

                if (listener != null) {
                    listener.onIngredienteAgregado(nuevo);
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
