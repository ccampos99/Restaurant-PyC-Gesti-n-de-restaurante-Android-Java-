package com.peter.restauranteproyecto.waiter.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.common.data.DatabaseHelper;
import com.peter.restauranteproyecto.common.model.Mesa;

import java.util.List;

public class MesaAdapter extends RecyclerView.Adapter<MesaAdapter.MesaViewHolder> {

    private final List<Mesa> mesas;
    private final DatabaseHelper dbHelper;

    public MesaAdapter(List<Mesa> mesas, DatabaseHelper dbHelper) {
        this.mesas = mesas;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public MesaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mesa_waiter, parent, false);
        return new MesaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull MesaViewHolder holder, int position) {
        Mesa mesa = mesas.get(position);

        holder.textNombre.setText(mesa.getNombre());
        holder.textCapacidad.setText(String.valueOf(mesa.getCapacidad()));
        holder.textEstado.setText(mesa.getEstado());
        holder.textMesero.setText(mesa.getMesero());
        holder.textClientes.setText(mesa.getClientes());
        holder.textInformacion.setText(mesa.getInformacion());

        holder.textEstado.setBackgroundResource(getBackgroundEstado(mesa.getEstado().toLowerCase()));

        holder.btnAccion1.setVisibility(View.VISIBLE);
        holder.btnAccion2.setVisibility(View.GONE); // Ocultar siempre al inicio

        switch (mesa.getEstado().toLowerCase()) {
            case "ocupada":
                holder.btnAccion1.setText("Liberar");
                holder.btnAccion2.setVisibility(View.GONE); // Refuerzo
                break;
            case "limpieza":
                holder.btnAccion1.setText("Listo");
                holder.btnAccion2.setVisibility(View.GONE); // Refuerzo
                break;
            case "libre":
                holder.btnAccion1.setText("Asignar");
                holder.btnAccion2.setText("Reservar");
                holder.btnAccion2.setVisibility(View.VISIBLE);
                break;
            case "reservada":
                holder.btnAccion1.setText("Confirmar");
                holder.btnAccion2.setText("Cancelar");
                holder.btnAccion2.setVisibility(View.VISIBLE);
                break;
            default:
                holder.btnAccion1.setVisibility(View.GONE);
                holder.btnAccion2.setVisibility(View.GONE);
                break;
        }


        holder.btnAccion1.setOnClickListener(v -> {
            switch (mesa.getEstado().toLowerCase()) {
                case "ocupada":
                    mesa.setEstado("Limpieza");
                    break;
                case "limpieza":
                    mesa.setEstado("Libre");
                    break;
                case "libre":
                    mesa.setEstado("Ocupada");
                    break;
                case "reservada":
                    mesa.setEstado("Ocupada");
                    break;
            }
            dbHelper.actualizarEstadoMesa(mesa.getNombre(), mesa.getEstado());
            notifyItemChanged(position);
        });

        holder.btnAccion2.setOnClickListener(v -> {
            switch (mesa.getEstado().toLowerCase()) {
                case "libre":
                    mesa.setEstado("Reservada");
                    break;
                case "reservada":
                    mesa.setEstado("Libre");
                    break;
            }
            dbHelper.actualizarEstadoMesa(mesa.getNombre(), mesa.getEstado());
            notifyItemChanged(position);
        });
    }


    @Override
    public int getItemCount() {
        return mesas.size();
    }

    private int getBackgroundEstado(String estado) {
        switch (estado) {
            case "ocupada": return R.drawable.bg_estado_ocupada;
            case "libre": return R.drawable.bg_estado_libre;
            case "reservada": return R.drawable.bg_estado_reservada;
            case "limpieza": return R.drawable.bg_estado_limpieza;
            default: return android.R.color.transparent;
        }
    }

    public void actualizarLista(List<Mesa> nuevasMesas) {
        mesas.clear();
        mesas.addAll(nuevasMesas);
        notifyDataSetChanged();
    }

    public static class MesaViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textCapacidad, textEstado, textMesero, textClientes, textInformacion;
        Button btnAccion1, btnAccion2;

        public MesaViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.text_mesa);
            textCapacidad = itemView.findViewById(R.id.text_capacidad);
            textEstado = itemView.findViewById(R.id.text_estado);
            textMesero = itemView.findViewById(R.id.text_mesero);
            textClientes = itemView.findViewById(R.id.text_clientes);
            textInformacion = itemView.findViewById(R.id.text_info);
            btnAccion1 = itemView.findViewById(R.id.btn_accion1);
            btnAccion2 = itemView.findViewById(R.id.btn_accion2);
        }
    }
}
