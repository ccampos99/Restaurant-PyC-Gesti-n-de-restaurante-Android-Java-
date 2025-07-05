package com.peter.restauranteproyecto.cashier.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.common.data.DatabaseHelper;
import com.peter.restauranteproyecto.common.model.Pedido;
import com.peter.restauranteproyecto.waiter.ui.adapter.PedidoCajaAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CajaFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private List<Pedido> pedidosPagables;
    private PedidoCajaAdapter adapter;
    private TextView txtTotalBoletas, txtImporteTotal;
    private LinearLayout layoutResumen;
    private TextView txtHistorialTitulo, txtHistorialContenido;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_caja, container, false);

        dbHelper = new DatabaseHelper(requireContext());

        // Inicializar vistas
        RecyclerView recyclerView = view.findViewById(R.id.recycler_pedidos_caja);
        Button btnCierreCaja = view.findViewById(R.id.btn_cierre_caja);
        Button btnGenerarCierre = view.findViewById(R.id.btn_generar_cierre);
//        Button btnIrHistorial = view.findViewById(R.id.btn_ir_historial);
        txtTotalBoletas = view.findViewById(R.id.txt_total_boletas);
        txtHistorialTitulo = view.findViewById(R.id.txt_historial_titulo);
        txtHistorialContenido = view.findViewById(R.id.txt_historial_contenido);
        txtImporteTotal = view.findViewById(R.id.txt_importe_total);
        layoutResumen = view.findViewById(R.id.layout_resumen_cierre);

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        pedidosPagables = obtenerPedidosPagables();
        adapter = new PedidoCajaAdapter(pedidosPagables, dbHelper);
        recyclerView.setAdapter(adapter);

        // Acciones de botones
        btnCierreCaja.setOnClickListener(v -> mostrarResumenCierre());

        btnGenerarCierre.setOnClickListener(v -> {
            guardarCierreEnBD();
            generarCierre();
        });

//        btnIrHistorial.setOnClickListener(v -> {
//            List<String> historial = dbHelper.obtenerHistorialCierres();
//
//            if (!historial.isEmpty()) {
//                txtHistorialTitulo.setVisibility(View.VISIBLE);
//                txtHistorialContenido.setVisibility(View.VISIBLE);
//
//                StringBuilder contenido = new StringBuilder();
//                for (String item : historial) {
//                    contenido.append("• ").append(item).append("\n\n");
//                }
//                txtHistorialContenido.setText(contenido.toString());
//            } else {
//                Toast.makeText(requireContext(), "No hay historial disponible", Toast.LENGTH_SHORT).show();
//            }
//        });


        return view;
    }

    private void mostrarResumenCierre() {
        int totalBoletas = 0;
        double importeTotal = 0.0;

        for (Pedido p : pedidosPagables) {
            if ("Pagado".equalsIgnoreCase(p.getEstado())) {
                totalBoletas++;
                importeTotal += p.getTotal();
            }
        }

        txtTotalBoletas.setText("Total de boletas: " + totalBoletas);
        txtImporteTotal.setText(String.format(Locale.getDefault(), "Importe total del día: S/ %.2f", importeTotal));
        layoutResumen.setVisibility(View.VISIBLE);
    }


    private double calcularImporteTotal() {
        double total = 0.0;
        for (Pedido p : pedidosPagables) {
            total += p.getTotal();
        }
        return total;
    }
    private List<Pedido> obtenerPedidosPagables() {
        List<Pedido> lista = new ArrayList<>();
        for (Pedido p : dbHelper.obtenerTodosLosPedidos()) {
            if ("Listo".equalsIgnoreCase(p.getEstado()) || "Servido".equalsIgnoreCase(p.getEstado())) {
                lista.add(p);
            }
        }
        return lista;
    }
    private void guardarCierreEnBD() {
        int totalBoletas = pedidosPagables.size();
        double importeTotal = calcularImporteTotal();
        String fechaActual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        dbHelper.insertarCierreCaja(totalBoletas, importeTotal, fechaActual);
    }

    private void generarCierre() {
        int totalBoletas = 0;
        double importeTotal = 0.0;

        // Filtrar solo pedidos pagados
        List<Pedido> pedidosPagados = new ArrayList<>();
        for (Pedido p : pedidosPagables) {
            if ("Pagado".equalsIgnoreCase(p.getEstado())) {
                pedidosPagados.add(p);
                totalBoletas++;
                importeTotal += p.getTotal();
            }
        }

        if (totalBoletas == 0) {
            Toast.makeText(requireContext(), "No hay pedidos pagados para cerrar.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Guardar en la BD
        String fechaActual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        dbHelper.insertarCierreCaja(totalBoletas, importeTotal, fechaActual);

        // Eliminar los pedidos pagados de la lista visual
        pedidosPagables.removeAll(pedidosPagados);
        adapter.notifyDataSetChanged();

        layoutResumen.setVisibility(View.GONE);

        // Mostrar mensaje
        Toast.makeText(requireContext(), "¡Cierre de caja generado exitosamente!", Toast.LENGTH_SHORT).show();
    }


}
