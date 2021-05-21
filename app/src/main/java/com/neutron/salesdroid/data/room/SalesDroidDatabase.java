package com.neutron.salesdroid.data.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.neutron.salesdroid.data.model.Customer;
import com.neutron.salesdroid.data.model.Sales;
import com.neutron.salesdroid.data.model.Stock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Sales.class, Customer.class, Stock.class}, version = 3, exportSchema = false)
public abstract class SalesDroidDatabase extends RoomDatabase{
    private static final String DB_NAME = "salesdroiddb";
    private static SalesDroidDatabase instance;
    public static ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);

    public static synchronized SalesDroidDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), SalesDroidDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract SalesDao salesDao();

    public abstract StockDao stockDao();

    public abstract CustomerDao customerDao();
}
