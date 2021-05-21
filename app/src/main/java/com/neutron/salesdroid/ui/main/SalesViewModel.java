package com.neutron.salesdroid.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.neutron.salesdroid.data.Repository;
import com.neutron.salesdroid.data.model.Transaction;

import java.util.List;

public class SalesViewModel extends AndroidViewModel {
    Repository repository;
    
    public SalesViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<Transaction>> getAllSales(){
        return repository.getAllSalesDetails();
    }
    public int getTablesize(){
        return repository.getTableSize();
    }
}
