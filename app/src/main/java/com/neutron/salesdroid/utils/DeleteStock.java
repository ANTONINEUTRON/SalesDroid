package com.neutron.salesdroid.utils;

import android.app.Application;
import android.content.Context;

import com.neutron.salesdroid.data.Repository;
import com.neutron.salesdroid.data.model.Stock;

public class DeleteStock {
    private Context context;
    private Repository repository;

    public DeleteStock(Context context) {
        this.context = context;
        repository = new Repository((Application)context.getApplicationContext());
    }

    public void delete(Stock stock){
        repository.deleteStock(stock);
    }
}
