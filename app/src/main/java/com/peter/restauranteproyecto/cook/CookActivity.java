package com.peter.restauranteproyecto.cook;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.peter.restauranteproyecto.R;

public class CookActivity extends AppCompatActivity {

    private String[] tabTitles = {"Inicio", "Pedidos", "Menú", "Ingredientes", "Proveedores"};
    private int[] tabIcons = {
            R.drawable.ic_inicio,
            R.drawable.ic_pedidos,
            R.drawable.ic_menu,
            R.drawable.ic_ingredientes,
            R.drawable.ic_proveedores
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        CookPagerAdapter adapter = new CookPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(tabTitles[position]);
            tab.setIcon(tabIcons[position]);
        }).attach();
    }
}
