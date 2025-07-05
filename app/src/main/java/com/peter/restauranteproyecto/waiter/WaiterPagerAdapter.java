package com.peter.restauranteproyecto.waiter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.peter.restauranteproyecto.waiter.ui.fragment.MesasFragment;
import com.peter.restauranteproyecto.waiter.ui.fragment.PedidosMeseroFragment;
import com.peter.restauranteproyecto.waiter.ui.fragment.PersonalFragment;
import com.peter.restauranteproyecto.waiter.ui.fragment.ReservasFragment;
import com.peter.restauranteproyecto.waiter.ui.fragment.WaiterInicioFragment;


public class WaiterPagerAdapter extends FragmentStateAdapter {

    public WaiterPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new WaiterInicioFragment();
            case 1: return new MesasFragment();
            case 2: return new PedidosMeseroFragment();
            case 3: return new ReservasFragment();
            case 4: return new PersonalFragment();
            default: return new WaiterInicioFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
