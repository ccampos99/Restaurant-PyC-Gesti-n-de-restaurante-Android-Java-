package com.peter.restauranteproyecto.waiter.ui.dialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.common.data.DataRepository;
import com.peter.restauranteproyecto.common.model.Pedido;
import com.peter.restauranteproyecto.common.model.Plato;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NuevoPedidoDialogFragment extends DialogFragment {

    public interface OnPedidoCreadoListener {
        void onPedidoCreado(Pedido nuevoPedido);
    }

    private OnPedidoCreadoListener listener;

    public void setOnPedidoCreadoListener(OnPedidoCreadoListener listener) {
        this.listener = listener;
    }

    private final List<String> articulosSeleccionados = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_nuevo_pedido, container, false);

        EditText inputMesa = view.findViewById(R.id.input_mesa);
        EditText inputTotal = view.findViewById(R.id.input_total);
        Spinner spinnerMesero = view.findViewById(R.id.spinner_mesero);
        Spinner spinnerPlatos = view.findViewById(R.id.spinner_platos);
        ImageButton btnAgregarArticulo = view.findViewById(R.id.btn_agregar_articulo);
        TextView textArticulosAgregados = view.findViewById(R.id.text_articulos_agregados);
        Button btnGuardar = view.findViewById(R.id.btn_guardar); // <-- TU botón personalizado
        Button btnCancelar = view.findViewById(R.id.btn_cancelar);

        // Poblar spinner de meseros
        List<String> meseros = Arrays.asList("María García", "Carlos López", "Ana Martín", "Pedro Ruiz");
        spinnerMesero.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, meseros));

        // Poblar spinner de platos
        List<Plato> platosDisponibles = new ArrayList<>();
        List<String> nombresPlatos = new ArrayList<>();
        for (Plato plato : DataRepository.platos) {
            if (plato.getEstado().equalsIgnoreCase("Disponible") || plato.getEstado().equalsIgnoreCase("Temporada")) {
                platosDisponibles.add(plato);
                nombresPlatos.add(plato.getNombre());
            }
        }
        spinnerPlatos.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, nombresPlatos));

        btnCancelar.setOnClickListener(v -> dismiss());

        // Agregar artículo
        btnAgregarArticulo.setOnClickListener(v -> {
            String platoSeleccionado = spinnerPlatos.getSelectedItem().toString();
            articulosSeleccionados.add(platoSeleccionado);

            StringBuilder builder = new StringBuilder();
            for (String art : articulosSeleccionados) {
                builder.append("• ").append(art).append("\n");
            }
            textArticulosAgregados.setText(builder.toString().trim());

            if (inputTotal.getText().toString().trim().isEmpty()) {
                double total = 0.0;
                for (String art : articulosSeleccionados) {
                    for (Plato plato : platosDisponibles) {
                        if (plato.getNombre().equalsIgnoreCase(art)) {
                            total += plato.getPrecio();
                        }
                    }
                }
                inputTotal.setText(String.format(Locale.getDefault(), "%.2f", total));
            }
        });

        // Guardar pedido
        btnGuardar.setOnClickListener(v -> {
            String codigo = "ORD-" + System.currentTimeMillis();
            String mesa = inputMesa.getText().toString().trim();
            double total = Double.parseDouble(inputTotal.getText().toString().trim());
            String estado = "Pendiente";
            String horaPedido = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            String estimado = "15 min";
            String prioridad = calcularPrioridad(estimado);
            String mesero = spinnerMesero.getSelectedItem().toString();

            Pedido nuevoPedido = new Pedido(codigo, mesa, articulosSeleccionados, estado, horaPedido, estimado, total, prioridad, mesero);
            if (listener != null) {
                listener.onPedidoCreado(nuevoPedido);
            }
            dismiss(); // Cierra el diálogo
        });

        return view;
    }


    private String calcularPrioridad(String estimado) {
        try {
            int minutos = Integer.parseInt(estimado.replace("min", "").trim());
            if (minutos <= 10) return "Alta";
            else if (minutos <= 20) return "Media";
            else return "Baja";
        } catch (Exception e) {
            return "Media";
        }
    }
}
