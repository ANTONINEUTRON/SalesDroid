package com.neutron.salesdroid.ui.addStock;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.neutron.salesdroid.data.Repository;
import com.neutron.salesdroid.data.model.Stock;

public class AddStockViewModel extends AndroidViewModel {
    Repository repository;
    public AddStockViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void createNewStock(Stock stock){
        repository.createStock(stock);
    }
}
