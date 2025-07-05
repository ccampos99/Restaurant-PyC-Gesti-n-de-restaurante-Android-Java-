package com.peter.restauranteproyecto.cook.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.common.model.Plato;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private final List<Plato> platos;

    public MenuAdapter(List<Plato> platos) {
        this.platos = platos;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Plato plato = platos.get(position);
        holder.textNombre.setText(plato.getNombre());
        holder.textDescripcion.setText(plato.getDescripcion());
        holder.textCategoria.setText(plato.getCategoria());
        holder.textPrecio.setText(String.format("$%.2f", plato.getPrecio()));
        holder.textEstado.setText(plato.getEstado());
        holder.textTiempo.setText(plato.getTiempoPrep() + " min");
        holder.textEstado.setBackgroundResource(getBackgroundEstado(plato.getEstado().toLowerCase()));


        // Acciones (editar y eliminar)
        holder.btnEditar.setOnClickListener(v -> {
            // Lógica para editar (puedes lanzar un diálogo o actividad)
        });

        holder.btnEliminar.setOnClickListener(v -> {
            platos.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return platos.size();
    }

    private int getBackgroundEstado(String estado) {
        switch (estado) {
            case "disponible":
                return R.drawable.bg_estado_disponible;
            case "temporada":
                return R.drawable.bg_estado_temporada;
            case "no disponible":
                return R.drawable.bg_estado_no_disponible;
            default:
                return android.R.color.transparent;
        }
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textDescripcion, textCategoria, textPrecio, textEstado, textTiempo;
        ImageButton btnEditar, btnEliminar;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.text_nombre);
            textDescripcion = itemView.findViewById(R.id.text_descripcion);
            textCategoria = itemView.findViewById(R.id.text_categoria);
            textPrecio = itemView.findViewById(R.id.text_precio);
            textEstado = itemView.findViewById(R.id.text_estado);
            textTiempo = itemView.findViewById(R.id.text_tiempo);
            btnEditar = itemView.findViewById(R.id.btn_editar);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar);
        }
    }
}
