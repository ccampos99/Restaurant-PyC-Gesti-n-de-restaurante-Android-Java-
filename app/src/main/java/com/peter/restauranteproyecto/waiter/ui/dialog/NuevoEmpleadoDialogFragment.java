package com.peter.restauranteproyecto.waiter.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.common.data.DataRepository;
import com.peter.restauranteproyecto.common.model.Empleado;


import java.util.Arrays;

public class NuevoEmpleadoDialogFragment extends DialogFragment {

    private EditText inputNombre, inputEmail, inputTelefono, inputSalario;
    private Spinner spinnerEstado, spinnerTurno;
    private Button btnGuardar;

    public interface OnEmpleadoCreadoListener {
        void onEmpleadoCreado(Empleado nuevo);
    }

    private OnEmpleadoCreadoListener listener;

    public void setOnEmpleadoCreadoListener(OnEmpleadoCreadoListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_nuevo_empleado, container, false);

        inputNombre = view.findViewById(R.id.input_nombre);
        inputEmail = view.findViewById(R.id.input_email);
        inputTelefono = view.findViewById(R.id.input_telefono);
        inputSalario = view.findViewById(R.id.input_salario);
        spinnerEstado = view.findViewById(R.id.spinner_estado);
        spinnerTurno = view.findViewById(R.id.spinner_turno);
        btnGuardar = view.findViewById(R.id.btn_guardar);

        btnGuardar.setOnClickListener(v -> guardarEmpleado());

        return view;
    }

    private void guardarEmpleado() {
        String nombre = inputNombre.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String telefono = inputTelefono.getText().toString().trim();
        String estado = spinnerEstado.getSelectedItem().toString();
        String turno = spinnerTurno.getSelectedItem().toString();
        String salarioStr = inputSalario.getText().toString().trim();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(email) || TextUtils.isEmpty(telefono) || TextUtils.isEmpty(salarioStr)) {
            Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double salario = Double.parseDouble(salarioStr);

        Empleado nuevo = new Empleado(
                nombre,
                "2025-07-05",
                "Mesero",
                email,
                telefono,
                estado,
                turno,
                Arrays.asList("L", "M", "X", "J", "V"),
                4.0,
                salario
        );

        DataRepository.empleados.add(nuevo);


        if (listener != null) listener.onEmpleadoCreado(nuevo);

        dismiss();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(true);
        return super.onCreateDialog(savedInstanceState);
    }
}

