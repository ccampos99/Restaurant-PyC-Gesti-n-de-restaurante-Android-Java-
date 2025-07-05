package com.peter.restauranteproyecto.waiter.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.common.model.Reserva;

import java.util.List;

public class ReservasAdapter extends RecyclerView.Adapter<ReservasAdapter.ReservaViewHolder> {

    private final List<Reserva> reservas;
    private final FragmentManager fragmentManager;
    private final OnReservaAccionListener accionListener;

    public interface OnReservaAccionListener {
        void onActualizarEstado(String codigo, String nuevoEstado);
        void onEliminarReserva(String codigo);
    }

    public ReservasAdapter(List<Reserva> reservas, FragmentManager fragmentManager, OnReservaAccionListener accionListener) {
        this.reservas = reservas;
        this.fragmentManager = fragmentManager;
        this.accionListener = accionListener;
    }

    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva, parent, false);
        return new ReservaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        Reserva reserva = reservas.get(position);

        holder.textCodigo.setText(reserva.getCodigo());
        holder.textFechaCreacion.setText("Fecha: " + reserva.getFecha() + " " + reserva.getHora());
        holder.textCliente.setText(reserva.getCliente());
        holder.textMesa.setText(reserva.getMesa().isEmpty() ? "Sin asignar" : reserva.getMesa());
        holder.textComensales.setText(String.valueOf(reserva.getComensales()));
        holder.textEstado.setText(reserva.getEstado());
        holder.textEstado.setBackgroundResource(getBackgroundEstado(reserva.getEstado()));
        holder.textSolicitudes.setText(reserva.getSolicitudes().isEmpty() ? "-" : reserva.getSolicitudes());

        holder.btnConfirmar.setVisibility(reserva.getEstado().equalsIgnoreCase("Pendiente") ? View.VISIBLE : View.GONE);
        holder.btnCancelar.setVisibility(reserva.getEstado().equalsIgnoreCase("Pendiente") ? View.VISIBLE : View.GONE);
        holder.btnCompletar.setVisibility(reserva.getEstado().equalsIgnoreCase("Confirmada") ? View.VISIBLE : View.GONE);
        holder.btnNoAsistio.setVisibility(reserva.getEstado().equalsIgnoreCase("Confirmada") ? View.VISIBLE : View.GONE);

        holder.btnConfirmar.setOnClickListener(v -> accionListener.onActualizarEstado(reserva.getCodigo(), "Confirmada"));
        holder.btnCancelar.setOnClickListener(v -> accionListener.onActualizarEstado(reserva.getCodigo(), "Cancelada"));
        holder.btnCompletar.setOnClickListener(v -> accionListener.onActualizarEstado(reserva.getCodigo(), "Completada"));
        holder.btnNoAsistio.setOnClickListener(v -> accionListener.onActualizarEstado(reserva.getCodigo(), "No Asistió"));

        holder.btnEditar.setOnClickListener(v -> {
            // Pendiente implementar edición
        });

        holder.btnEliminar.setOnClickListener(v -> accionListener.onEliminarReserva(reserva.getCodigo()));
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    public void actualizarLista(List<Reserva> nuevaLista) {
        reservas.clear();
        reservas.addAll(nuevaLista);
        notifyDataSetChanged();
    }

    private int getBackgroundEstado(String estado) {
        switch (estado.toLowerCase()) {
            case "pendiente":
                return R.drawable.bg_estado_pendiente;
            case "confirmada":
                return R.drawable.bg_estado_disponible;
            case "completada":
                return R.drawable.bg_estado_preparando;
            case "no asistió":
                return R.drawable.bg_estado_limpieza;
            case "cancelada":
                return R.drawable.bg_estado_inactivo;
            default:
                return android.R.color.transparent;
        }
    }

    public static class ReservaViewHolder extends RecyclerView.ViewHolder {
        TextView textCodigo, textFechaCreacion, textCliente, textMesa, textComensales, textEstado, textSolicitudes;
        Button btnConfirmar, btnCancelar, btnCompletar, btnNoAsistio;
        ImageButton btnEditar, btnEliminar;

        public ReservaViewHolder(@NonNull View itemView) {
            super(itemView);
            textCodigo = itemView.findViewById(R.id.text_codigo_reserva);
            textFechaCreacion = itemView.findViewById(R.id.text_fecha_hora);
            textCliente = itemView.findViewById(R.id.text_cliente);
            textMesa = itemView.findViewById(R.id.text_mesa);
            textComensales = itemView.findViewById(R.id.text_comensales);
            textEstado = itemView.findViewById(R.id.text_estado);
            textSolicitudes = itemView.findViewById(R.id.text_solicitudes);
            btnConfirmar = itemView.findViewById(R.id.btn_confirmar);
            btnCancelar = itemView.findViewById(R.id.btn_cancelar);
            btnCompletar = itemView.findViewById(R.id.btn_completar);
            btnNoAsistio = itemView.findViewById(R.id.btn_no_asistio);
            btnEditar = itemView.findViewById(R.id.btn_editar_reserva);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar_reserva);
        }
    }
}
