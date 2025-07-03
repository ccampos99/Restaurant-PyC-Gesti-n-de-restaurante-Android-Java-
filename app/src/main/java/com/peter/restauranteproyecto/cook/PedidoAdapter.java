package com.peter.restauranteproyecto.cook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.peter.restauranteproyecto.R;
import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {

    private final List<Pedido> pedidos;

    public PedidoAdapter(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);
        return new PedidoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Pedido pedido = pedidos.get(position);
        holder.textCodigo.setText(pedido.id);
        holder.textMesa.setText(pedido.mesa);
        holder.textArticulos.setText("• " + String.join("\n• ", pedido.articulos));
        holder.textEstado.setText(pedido.estado);
        holder.textTiempo.setText("Pedido: " + pedido.horaPedido + "\nEst: " + pedido.estimado);
        holder.textTotal.setText(String.format("$%.2f", pedido.total));
        holder.btnAccion.setText(getAccionParaEstado(pedido.estado));

        // Aplicar fondo dinámico al estado
        int backgroundResId = getBackgroundForEstado(pedido.estado.toLowerCase());
        holder.textEstado.setBackgroundResource(backgroundResId);

    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    private int getBackgroundForEstado(String estado) {
        switch (estado) {
            case "pendiente":
                return R.drawable.bg_estado_pendiente;
            case "preparando":
                return R.drawable.bg_estado_preparando;
            case "listo":
                return R.drawable.bg_estado_listo;
            case "servido":
                return R.drawable.bg_estado_servido;
            default:
                return android.R.color.transparent;
        }
    }


    private String getAccionParaEstado(String estado) {
        switch (estado.toLowerCase()) {
            case "pendiente":
                return "Iniciar Preparación";
            case "preparando":
                return "Marcar Listo";
            case "listo":
                return "Marcar Servido";
            default:
                return "Acción";
        }
    }

    public static class PedidoViewHolder extends RecyclerView.ViewHolder {
        TextView textCodigo, textMesa, textArticulos, textEstado, textTiempo, textTotal;
        Button btnAccion;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            textCodigo = itemView.findViewById(R.id.text_codigo);
            textMesa = itemView.findViewById(R.id.text_mesa);
            textArticulos = itemView.findViewById(R.id.text_articulos);
            textEstado = itemView.findViewById(R.id.text_estado);
            textTiempo = itemView.findViewById(R.id.text_tiempo);
            textTotal = itemView.findViewById(R.id.text_total);
            btnAccion = itemView.findViewById(R.id.btn_accion);
        }
    }
}
