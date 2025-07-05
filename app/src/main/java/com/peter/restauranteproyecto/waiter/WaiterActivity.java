package com.peter.restauranteproyecto.waiter;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.peter.restauranteproyecto.R;

public class WaiterActivity extends AppCompatActivity {

    private String[] tabTitles = {"Inicio", "Mesas", "Pedidos", "Reservas", "Personal"};
    private int[] tabIcons = {
            R.drawable.ic_inicio,
            R.drawable.ic_mesas,         // o crea un ic_mesas
            R.drawable.ic_pedidos,
            R.drawable.ic_reserva,         // o crea un ic_reservas
            R.drawable.ic_personal          // o crea un ic_personal
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        WaiterPagerAdapter adapter = new WaiterPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(tabTitles[position]);
            tab.setIcon(tabIcons[position]);
        }).attach();
    }
}
