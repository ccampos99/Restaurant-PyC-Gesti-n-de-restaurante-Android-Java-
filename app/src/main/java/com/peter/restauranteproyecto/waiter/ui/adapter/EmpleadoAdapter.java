package com.peter.restauranteproyecto.waiter.ui.adapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.common.model.Empleado;

import java.util.List;

public class EmpleadoAdapter extends RecyclerView.Adapter<EmpleadoAdapter.EmpleadoViewHolder> {

    private final List<Empleado> empleados;
    private final Context context;

    public EmpleadoAdapter(List<Empleado> empleados, Context context) {
        this.empleados = empleados;
        this.context = context;
    }

    @NonNull
    @Override
    public EmpleadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_empleado, parent, false);
        return new EmpleadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpleadoViewHolder holder, int position) {
        Empleado e = empleados.get(position);

        holder.textNombre.setText(e.getNombre());
        holder.textFecha.setText("Desde: " + e.getFechaIngreso());
        holder.textRol.setText(e.getRol());
        holder.textEmail.setText("📧 " + e.getEmail());
        holder.textTelefono.setText("📞 " + e.getTelefono());
        holder.textEstado.setText(e.getEstado());
        holder.textTurno.setText(e.getTurno());
        holder.textSalario.setText("€" + String.format("%.0f", e.getSalario()));
        holder.textRendimiento.setText("★★★★★ (" + e.getRendimiento() + ")");

        // Cambia el fondo según estado
        if (e.getEstado().equalsIgnoreCase("Activo")) {
            holder.textEstado.setBackgroundResource(R.drawable.bg_estado_activo);
            holder.btnEstado.setText("Descansar");

        } else {
            holder.textEstado.setBackgroundResource(R.drawable.bg_estado_libre);
            holder.btnEstado.setText("Activar");

        }

        // Limpia e infla días laborales
        holder.layoutHorario.removeAllViews();
        for (String dia : e.getHorario()) {
            TextView diaView = new TextView(context);
            diaView.setText(dia);
            diaView.setTextColor(ContextCompat.getColor(context, R.color.white));
            diaView.setTextSize(12);
            diaView.setPadding(12, 6, 12, 6);
            diaView.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_cart));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMarginEnd(8);
            diaView.setLayoutParams(params);
            holder.layoutHorario.addView(diaView);
        }

        // Acciones
        holder.btnEditar.setOnClickListener(v -> {
            // lógica para editar
        });

        holder.btnEliminar.setOnClickListener(v -> {
            empleados.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, empleados.size());
        });

        holder.btnEstado.setOnClickListener(v -> {
            if (e.getEstado().equals("Activo")) {
                e.setEstado("En Descanso");
            } else {
                e.setEstado("Activo");
            }
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return empleados.size();
    }

    public static class EmpleadoViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textFecha, textRol, textEmail, textTelefono,
                textEstado, textTurno, textRendimiento, textSalario;
        LinearLayout layoutHorario;
        ImageButton btnEditar, btnEliminar;
        Button btnEstado;

        public EmpleadoViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.text_nombre);
            textFecha = itemView.findViewById(R.id.text_fecha);
            textRol = itemView.findViewById(R.id.text_rol);
            textEmail = itemView.findViewById(R.id.text_email);
            textTelefono = itemView.findViewById(R.id.text_telefono);
            textEstado = itemView.findViewById(R.id.text_estado);
            textTurno = itemView.findViewById(R.id.text_turno);
            textRendimiento = itemView.findViewById(R.id.text_rendimiento);
            textSalario = itemView.findViewById(R.id.text_salario);
            layoutHorario = itemView.findViewById(R.id.layout_horario);
            btnEditar = itemView.findViewById(R.id.btn_editar);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar);
            btnEstado = itemView.findViewById(R.id.btn_estado);
        }
    }
}