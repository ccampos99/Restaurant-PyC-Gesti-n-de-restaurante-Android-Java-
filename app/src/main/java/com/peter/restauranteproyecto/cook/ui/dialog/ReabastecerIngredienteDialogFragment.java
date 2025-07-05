package com.peter.restauranteproyecto.cook.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.common.interfaces.OnIngredienteReabastecidoListener;
import com.peter.restauranteproyecto.common.model.Ingredientes;

import java.util.ArrayList;
import java.util.List;

public class ReabastecerIngredienteDialogFragment extends DialogFragment {

    private Ingredientes ingrediente;
    private int position;
    private OnIngredienteReabastecidoListener listener;

    public void setIngrediente(Ingredientes ingrediente, int position) {
        this.ingrediente = ingrediente;
        this.position = position;
    }

    public void setOnIngredienteReabastecidoListener(OnIngredienteReabastecidoListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_reabastecer_ingrediente, null);

        TextView textTitulo = view.findViewById(R.id.text_titulo_reabastecer);
        TextView textStockActual = view.findViewById(R.id.text_stock_actual);
        Spinner spinnerCantidad = view.findViewById(R.id.spinner_cantidad);
        TextView textResultadoStock = view.findViewById(R.id.text_resultado_stock);
        TextView textCostoEstimado = view.findViewById(R.id.text_costo_estimado);
        Button btnCancelar = view.findViewById(R.id.btn_cancelar_dialog);
        Button btnReabastecer = view.findViewById(R.id.btn_reabastecer_dialog);

        // Configura datos iniciales
        textTitulo.setText("Reabastecer " + ingrediente.getNombre());
        textStockActual.setText("Stock actual: " + ingrediente.getStockActual());

        // Simula cantidades de 1 a 20
        List<String> cantidades = new ArrayList<>();
        for (int i = 1; i <= 20; i++) cantidades.add(String.valueOf(i));

        spinnerCantidad.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, cantidades));

        spinnerCantidad.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int positionSpinner, long id) {
                double cantidad = Double.parseDouble(cantidades.get(positionSpinner));
                double stockActual = Double.parseDouble(ingrediente.getStockActual());
                double nuevoStock = stockActual + cantidad;
                double costo = cantidad * ingrediente.getCostoUnidad();

                textResultadoStock.setText("Stock después del reabastecimiento: " + nuevoStock);
                textCostoEstimado.setText(String.format("Costo estimado: $%.2f", costo));
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        btnCancelar.setOnClickListener(v -> dismiss());

        btnReabastecer.setOnClickListener(v -> {
            String seleccion = spinnerCantidad.getSelectedItem().toString();
            double cantidad = Double.parseDouble(seleccion);
            if (listener != null) {
                listener.onIngredienteReabastecido(position, cantidad);
            }
            dismiss();
        });

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
