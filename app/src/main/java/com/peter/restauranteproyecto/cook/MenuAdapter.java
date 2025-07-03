package com.peter.restauranteproyecto.cook;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.model.Plato;

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
        holder.textNombre.setText(plato.nombre);
        holder.textDescripcion.setText(plato.descripcion);
        holder.textCategoria.setText(plato.categoria);
        holder.textPrecio.setText(String.format("$%.2f", plato.precio));
        holder.textEstado.setText(plato.estado);
        holder.textTiempo.setText(plato.tiempoPrep + " min");

        // Fondo dinámico para estado
        holder.textEstado.setBackgroundResource(getBackgroundEstado(plato.estado.toLowerCase()));

        // Acciones (editar y eliminar)
        holder.btnEditar.setOnClickListener(v -> {
            // Lógica para editar (puedes lanzar un diálogo o actividad)
        });

        holder.btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Eliminar Plato")
                    .setMessage("¿Estás seguro que deseas eliminar:\n\n" + plato.nombre + "?")
                    .setPositiveButton("Eliminar", (dialog, which) -> {
                        platos.remove(position);
                        notifyItemRemoved(position);
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
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
