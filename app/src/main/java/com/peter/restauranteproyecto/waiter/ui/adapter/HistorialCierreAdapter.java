package com.peter.restauranteproyecto.cashier.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peter.restauranteproyecto.R;

import java.util.List;

public class HistorialCierreAdapter extends RecyclerView.Adapter<HistorialCierreAdapter.ViewHolder> {

    private List<String> cierres;

    public HistorialCierreAdapter(List<String> cierres) {
        this.cierres = cierres;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historial_cierre, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtResumen.setText(cierres.get(position));
    }

    @Override
    public int getItemCount() {
        return cierres.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtResumen;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtResumen = itemView.findViewById(R.id.txt_resumen_cierre);
        }
    }
}
