package com.peter.restauranteproyecto.cook;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.peter.restauranteproyecto.cook.ui.fragment.IngredientesFragment;
import com.peter.restauranteproyecto.cook.ui.fragment.InicioFragment;
import com.peter.restauranteproyecto.cook.ui.fragment.MenuFragment;
import com.peter.restauranteproyecto.cook.ui.fragment.PedidosFragment;
import com.peter.restauranteproyecto.cook.ui.fragment.ProveedoresFragment;

public class CookPagerAdapter extends FragmentStateAdapter {

    public CookPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new InicioFragment();
            case 1: return new PedidosFragment();
            case 2: return new MenuFragment();
            case 3: return new IngredientesFragment();
            case 4: return new ProveedoresFragment();
            default: return new InicioFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
