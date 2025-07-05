package com.peter.restauranteproyecto.cook.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.common.model.Proveedor;

import java.util.List;

public class ProveedoresAdapter extends RecyclerView.Adapter<ProveedoresAdapter.ProveedorViewHolder> {

    private final List<Proveedor> lista;

    public ProveedoresAdapter(List<Proveedor> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ProveedorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proveedor, parent, false);
        return new ProveedorViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ProveedorViewHolder holder, int position) {
        Proveedor proveedor = lista.get(position);

        holder.textNombre.setText(proveedor.getNombre());
        holder.textProductos.setText("Productos: " + proveedor.getProductos());
        holder.textCategoria.setText(proveedor.getCategoria());
        holder.textTelefono.setText(proveedor.getTelefono());
        holder.textCorreo.setText(proveedor.getCorreo());
        holder.textDireccion.setText(proveedor.getDireccion());
        holder.textEstado.setText(proveedor.getEstado());
        holder.textEstado.setBackgroundResource(getBackgroundEstado(proveedor.getEstado().toLowerCase()));
        holder.textCalificacion.setText(String.format("%.1f", proveedor.getCalificacion()));
        holder.textPedidos.setText("Pedidos: " + proveedor.getTotalPedidos());
        holder.textUltimo.setText("Último: " + proveedor.getUltimaEntrega());
        holder.textEntrega.setText("Entrega: " + proveedor.getTiempoEntrega());

        mostrarEstrellas(proveedor.getCalificacion(), holder.estrella1, holder.estrella2, holder.estrella3, holder.estrella4, holder.estrella5);

        holder.btnEditar.setOnClickListener(v -> {
            // Implementar edición
        });

        holder.btnEliminar.setOnClickListener(v -> {
            lista.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    private int getBackgroundEstado(String estado) {
        switch (estado) {
            case "activo":
                return R.drawable.bg_estado_ok;
            case "inactivo":
                return R.drawable.bg_estado_sin_stock;
            case "pendiente":
                return R.drawable.bg_estado_bajo;
            default:
                return android.R.color.transparent;
        }
    }

    private void mostrarEstrellas(double calificacion, ImageView... estrellas) {
        int llenas = (int) calificacion;
        for (int i = 0; i < estrellas.length; i++) {
            estrellas[i].setColorFilter(i < llenas ? Color.parseColor("#FFD700") : Color.parseColor("#CCCCCC"));
        }
    }

    public static class ProveedorViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textProductos, textCategoria, textTelefono, textCorreo, textDireccion;
        TextView textEstado, textCalificacion, textPedidos, textUltimo, textEntrega;
        ImageView estrella1, estrella2, estrella3, estrella4, estrella5;
        ImageButton btnEditar, btnEliminar;

        public ProveedorViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.text_nombre);
            textProductos = itemView.findViewById(R.id.text_productos);
            textCategoria = itemView.findViewById(R.id.text_categoria);
            textTelefono = itemView.findViewById(R.id.text_telefono);
            textCorreo = itemView.findViewById(R.id.text_email);
            textDireccion = itemView.findViewById(R.id.text_direccion);
            textEstado = itemView.findViewById(R.id.text_estado);
            textCalificacion = itemView.findViewById(R.id.text_puntaje);
            textPedidos = itemView.findViewById(R.id.text_pedidos);
            textUltimo = itemView.findViewById(R.id.text_ultimo);
            textEntrega = itemView.findViewById(R.id.text_entrega);

            estrella1 = itemView.findViewById(R.id.star1);
            estrella2 = itemView.findViewById(R.id.star2);
            estrella3 = itemView.findViewById(R.id.star3);
            estrella4 = itemView.findViewById(R.id.star4);
            estrella5 = itemView.findViewById(R.id.star5);

            btnEditar = itemView.findViewById(R.id.btn_editar);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar);
        }
    }

    public void actualizarLista(List<Proveedor> nueva) {
        lista.clear();
        lista.addAll(nueva);
        notifyDataSetChanged();
    }
}