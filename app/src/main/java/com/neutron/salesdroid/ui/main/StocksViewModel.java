package com.neutron.salesdroid.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.neutron.salesdroid.data.Repository;
import com.neutron.salesdroid.data.model.Stock;

import java.util.List;

public class StocksViewModel extends AndroidViewModel {
    Repository repository;

    public StocksViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<Stock>> getAllStocks(){
        return repository.getAllStocks();
    }
}
