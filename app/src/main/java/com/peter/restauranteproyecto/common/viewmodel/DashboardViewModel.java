package com.peter.restauranteproyecto.common.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<Boolean> actualizarDashboard = new MutableLiveData<>(false);

    public LiveData<Boolean> getActualizarDashboard() {
        return actualizarDashboard;
    }

    public void notificarActualizacion() {
        actualizarDashboard.setValue(true);
    }

    public void actualizarCompletada() {
        actualizarDashboard.setValue(false);
    }
}
