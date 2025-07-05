package com.peter.restauranteproyecto.cashier;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.peter.restauranteproyecto.R;
import com.peter.restauranteproyecto.cashier.ui.fragment.CajaFragment;

public class CashierActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cajero_main); // Lo veremos abajo

        // Cargar CajaFragment en el contenedor
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_cashier, new CajaFragment());
        transaction.commit();
    }
}
