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
import com.peter.restauranteproyecto.common.model.Pedido;

import java.util.List;

public class PedidoCajaAdapter extends RecyclerView.Adapter<PedidoCajaAdapter.ViewHolder> {

    private List<Pedido> pedidos;
    private DatabaseHelper dbHelper;

    public PedidoCajaAdapter(List<Pedido> pedidos, DatabaseHelper dbHelper) {
        this.pedidos = pedidos;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido_caja, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pedido pedido = pedidos.get(position);

        holder.txtTitulo.setText("Pedido: " + pedido.getId());
        holder.txtTotal.setText(String.format("Total: S/ %.2f", pedido.getTotal()));
        holder.txtEstado.setText("Estado: " + pedido.getEstado());

        holder.btnPagar.setOnClickListener(v -> {
            pedido.setEstado("Pagado");
            dbHelper.actualizarEstadoPedido(pedido.getId(), "Pagado");
            notifyItemChanged(position);
        });

    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    // ✅ MÉTODO correcto para marcar todos como pagados
    public void marcarPedidosComoPagados() {
        for (Pedido p : pedidos) {
            dbHelper.actualizarEstadoPedido(p.getId(), "Pagado");
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtTotal, txtEstado;
        Button btnPagar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txt_pedido_titulo);
            txtTotal = itemView.findViewById(R.id.txt_pedido_total);
            txtEstado = itemView.findViewById(R.id.txt_pedido_estado);
            btnPagar = itemView.findViewById(R.id.btn_pagar);
        }
    }
}
