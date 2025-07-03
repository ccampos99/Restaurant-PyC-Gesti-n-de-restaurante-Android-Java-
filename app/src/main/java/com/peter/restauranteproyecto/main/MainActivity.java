package com.peter.restauranteproyecto.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.peter.restauranteproyecto.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Encontramos las tarjetas
        View cardCook = findViewById(R.id.card_cook);
        View cardWaiter = findViewById(R.id.card_waiter);
        View cardCashier = findViewById(R.id.card_cashier);

        // Asignamos los listeners
        cardCook.setOnClickListener(v -> {
            // Opcional: podés dejar el toast si querés
            Toast.makeText(MainActivity.this, "Cocinero seleccionado", Toast.LENGTH_SHORT).show();

            // Lanzamos el CookActivity
            Intent intent = new Intent(MainActivity.this, com.peter.restauranteproyecto.cook.CookActivity.class);
            startActivity(intent);
        });

        cardWaiter.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "Mesero seleccionado", Toast.LENGTH_SHORT).show()
        );

        cardCashier.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "Cajero seleccionado", Toast.LENGTH_SHORT).show()
        );
    }
}
