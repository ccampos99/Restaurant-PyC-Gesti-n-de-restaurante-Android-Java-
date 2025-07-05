package com.peter.restauranteproyecto.cook.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.cook.ui.dialog.ReabastecerIngredienteDialogFragment;
import com.peter.restauranteproyecto.common.interfaces.OnIngredienteReabastecidoListener;
import com.peter.restauranteproyecto.common.model.Ingredientes;

import java.util.List;

public class IngredientesAdapter extends RecyclerView.Adapter<IngredientesAdapter.IngredienteViewHolder> {

    private final List<Ingredientes> ingredientes;
    private final FragmentManager fragmentManager;
    private final OnIngredienteReabastecidoListener reabastecerListener;

    public IngredientesAdapter(List<Ingredientes> ingredientes, FragmentManager fragmentManager, OnIngredienteReabastecidoListener listener) {
        this.ingredientes = ingredientes;
        this.fragmentManager = fragmentManager;
        this.reabastecerListener = listener;
    }

    @NonNull
    @Override
    public IngredienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredientes, parent, false);
        return new IngredienteViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredienteViewHolder holder, int position) {
        Ingredientes ingrediente = ingredientes.get(position);

        holder.textNombre.setText(ingrediente.getNombre());
        holder.textStock.setText(ingrediente.getStockActual() + " (mínimo " + ingrediente.getStockMinimo() + ")");
        holder.textEstado.setText(ingrediente.getEstado());
        holder.textEstado.setBackgroundResource(getBackgroundEstado(ingrediente.getEstado().toLowerCase()));
        holder.textProveedor.setText("Proveedor: " + ingrediente.getProveedor());
        holder.textCosto.setText(String.format("Costo/U: $%.2f", ingrediente.getCostoUnidad()));
        holder.textFechaReposicion.setText("Última reposición: " + ingrediente.getFechaReposicion());

        // Acciones
        holder.btnEditar.setOnClickListener(v -> {
            // Puedes implementar un diálogo de edición como en AgregarPlatoDialogFragment
        });

        holder.btnComprar.setOnClickListener(v -> {
            ReabastecerIngredienteDialogFragment dialog = new ReabastecerIngredienteDialogFragment();
            dialog.setIngrediente(ingrediente, holder.getAdapterPosition());
            dialog.setOnIngredienteReabastecidoListener(reabastecerListener);
            dialog.show(fragmentManager, "ReabastecerIngrediente");
        });
    }

    @Override
    public int getItemCount() {
        return ingredientes.size();
    }

    private int getBackgroundEstado(String estado) {
        switch (estado) {
            case "en stock":
                return R.drawable.bg_estado_ok;
            case "stock bajo":
                return R.drawable.bg_estado_bajo;
            case "sin stock":
                return R.drawable.bg_estado_sin_stock;
            default:
                return android.R.color.transparent;
        }
    }

    public static class IngredienteViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textStock, textEstado, textProveedor, textCosto, textFechaReposicion;
        ImageButton btnEditar, btnComprar;

        public IngredienteViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.text_nombre);
            textStock = itemView.findViewById(R.id.text_stock);
            textEstado = itemView.findViewById(R.id.text_estado);
            textProveedor = itemView.findViewById(R.id.text_proveedor);
            textCosto = itemView.findViewById(R.id.text_costo);
            textFechaReposicion = itemView.findViewById(R.id.text_fecha_reposicion);
            btnEditar = itemView.findViewById(R.id.btn_editar);
            btnComprar = itemView.findViewById(R.id.btn_comprar);
        }
    }

    public void actualizarLista(List<Ingredientes> nuevaLista) {
        ingredientes.clear();
        ingredientes.addAll(nuevaLista);
        notifyDataSetChanged();
    }
}
