package com.peter.restauranteproyecto.waiter.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.common.data.DatabaseHelper;
import com.peter.restauranteproyecto.common.model.Reserva;
import com.peter.restauranteproyecto.common.interfaces.OnReservaCreadaListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NuevaReservaDialogFragment extends DialogFragment {

    private OnReservaCreadaListener listener;
    private DatabaseHelper dbHelper;

    public void setOnReservaCreadaListener(OnReservaCreadaListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(requireContext());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_nueva_reserva, null);

        EditText inputCliente = view.findViewById(R.id.input_cliente);
        EditText inputComensales = view.findViewById(R.id.input_comensales);
        EditText inputMesa = view.findViewById(R.id.input_mesa);
        EditText inputSolicitudes = view.findViewById(R.id.input_solicitudes);
        Spinner spinnerMesero = view.findViewById(R.id.spinner_mesero);
        DatePicker datePicker = view.findViewById(R.id.date_picker);
        TimePicker timePicker = view.findViewById(R.id.time_picker);
        Button btnCancelar = view.findViewById(R.id.btn_cancelar_reserva);
        Button btnGuardar = view.findViewById(R.id.btn_guardar_reserva);

        timePicker.setIs24HourView(true);

        // Cargar meseros (dummy)
        String[] meseros = {"María García", "Carlos López", "Ana Martín", "Pedro Ruiz"};
        spinnerMesero.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, meseros));

        btnCancelar.setOnClickListener(v -> dismiss());

        btnGuardar.setOnClickListener(v -> {
            String cliente = inputCliente.getText().toString().trim();
            String mesa = inputMesa.getText().toString().trim();
            String solicitudes = inputSolicitudes.getText().toString().trim();
            int comensales = Integer.parseInt(inputComensales.getText().toString().trim());
            String mesero = spinnerMesero.getSelectedItem().toString();

            Calendar calendar = Calendar.getInstance();
            calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                    timePicker.getHour(), timePicker.getMinute());

            Date fechaCompleta = calendar.getTime();
            String fecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(fechaCompleta);
            String hora = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(fechaCompleta);

            String codigo = "RES-" + System.currentTimeMillis();
            String estado = "Pendiente";

            Reserva nueva = new Reserva(codigo, cliente, fecha, hora, comensales, mesa, estado, solicitudes, mesero);

            boolean exito = dbHelper.insertarReserva(nueva);
            if (exito) {
                if (listener != null) {
                    listener.onReservaCreada(nueva);
                }
                Toast.makeText(getContext(), "Reserva guardada exitosamente", Toast.LENGTH_SHORT).show();
                dismiss();
            } else {
                Toast.makeText(getContext(), "Error al guardar la reserva", Toast.LENGTH_SHORT).show();
            }
        });

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
