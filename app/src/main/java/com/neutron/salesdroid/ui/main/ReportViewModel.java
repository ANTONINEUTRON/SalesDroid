package com.neutron.salesdroid.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.neutron.salesdroid.data.Repository;
import com.neutron.salesdroid.data.model.DebtorsModel;
import com.neutron.salesdroid.data.model.Sales;

import java.util.List;

public class ReportViewModel extends AndroidViewModel{
    Repository repository;
    public ReportViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<Sales>> getAllSales(){
        return repository.getAllSales();
    }

    public LiveData<List<DebtorsModel>> getDebtorsList(){
        return repository.getDebtors();
    }
}