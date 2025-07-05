package com.peter.restauranteproyecto.cashier.ui.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.common.data.DatabaseHelper;
import com.peter.restauranteproyecto.cashier.ui.adapter.HistorialCierreAdapter;

import java.util.List;

public class HistorialCierresFragment extends Fragment {

    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historial_cierres, container, false);

        dbHelper = new DatabaseHelper(requireContext());

        RecyclerView recyclerView = view.findViewById(R.id.recycler_historial_cierres);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<String> historial = dbHelper.obtenerHistorialCierres();
        recyclerView.setAdapter(new HistorialCierreAdapter(historial));

        return view;
    }
}
