package com.peter.restauranteproyecto.cook;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peter.restauranteproyecto.R;

public class ProveedoresFragment extends Fragment {
    public ProveedoresFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_proveedores, container, false);
    }
}
