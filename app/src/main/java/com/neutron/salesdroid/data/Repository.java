package com.neutron.salesdroid.data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.neutron.salesdroid.data.model.Customer;
import com.neutron.salesdroid.data.model.DebtorsModel;
import com.neutron.salesdroid.data.model.Sales;
import com.neutron.salesdroid.data.model.Stock;
import com.neutron.salesdroid.data.model.Transaction;
import com.neutron.salesdroid.data.model.UnitAndStockname;
import com.neutron.salesdroid.data.room.SalesDroidDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static com.neutron.salesdroid.data.room.SalesDroidDatabase.databaseExecutor;

public class Repository {
    private static SalesDroidDatabase salesDroidDatabase;

    public Repository(Application application) {
        salesDroidDatabase = SalesDroidDatabase.getInstance(application);
    }

    public void createSales(final Sales sales) {
        databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                salesDroidDatabase.salesDao().insertSales(sales);
                //Reduce stock quantity
                salesDroidDatabase.stockDao().reduceStockQty(sales.getQuantitySold(), sales.getStockName(),sales.getUnit());
            }
        });
    }

    public void updatePaymentStatus(final String paymentStatus, final int customerId, final String oldDate, final String newDate) {
        databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                salesDroidDatabase.salesDao().updatePaymentStatus(paymentStatus,customerId,oldDate,newDate);
            }
        });
    }

    public LiveData<List<Transaction>> getAllSalesDetails(){
        Future<LiveData<List<Transaction>>> task = databaseExecutor.submit(new Callable<LiveData<List<Transaction>>>() {
            @Override
            public LiveData<List<Transaction>> call() throws Exception {
                return salesDroidDatabase.salesDao().getAllSalesDetails();
            }
        });
        try{
            return task.get();
        }catch (Throwable throwable){
            Log.e("SALESDROID","Exception occur while trying to getAllSales()",throwable);
        }
        return null;
    }

    public void deleteSales(final Sales sales) {
        databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                salesDroidDatabase.salesDao().deleteSales(sales.getStockName(),sales.getDate(),sales.getQuantitySold());
            }
        });
    }

    public Integer getTableSize(){
        Future<Integer> task = databaseExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return salesDroidDatabase.salesDao().getTableSize();
            }
        });
        try{
            return task.get();
        }catch(Throwable throwable){
            Log.e("SALESDROID","Trouble while getting tablessize",throwable);
        }
        return null;
    }

    public void createStock(final Stock stock){
        databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                salesDroidDatabase.stockDao().insertStock(stock);
            }
        });
    }

    public LiveData<List<Stock>> getAllStocks(){
        Future<LiveData<List<Stock>>> task = databaseExecutor.submit(new Callable<LiveData<List<Stock>>>() {
            @Override
            public LiveData<List<Stock>> call() throws Exception {
                return salesDroidDatabase.stockDao().getStock();
            }
        });
        try {
            return task.get();
        }catch (Throwable throwable){
            Log.e("Salesdroid", "problem getting all stock()",throwable);
        }
        return null;
    }

    public LiveData<List<UnitAndStockname>> getUnitAndStockname(){
        Future<LiveData<List<UnitAndStockname>>> task = databaseExecutor.submit(new Callable<LiveData<List<UnitAndStockname>>>() {
            @Override
            public LiveData<List<UnitAndStockname>> call() throws Exception {
                return salesDroidDatabase.stockDao().getUnitAndStockname();
            }
        });

        try{
            return task.get();
        }catch(Throwable throwable){
            Log.e("Salesdroid","Error getting unitandstockname() ",throwable);
        }
        return null;
    }

    public int customerExists(final Customer customer){
        Future<Integer> task = databaseExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return salesDroidDatabase.customerDao().getCustomerId(customer.getCustomerName(),customer.getEmail());
            }
        });
        try{
            return task.get();
        }catch(Throwable throwable){
            Log.e("SALESDROID","Error Checking If customerExists(Customer) ",throwable);
        }
        return 0;
    }

    public long createNewCustomer(final Customer customer) {
        Future<Long> task = databaseExecutor.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return salesDroidDatabase.customerDao().insertCustomer(customer);
            }
        });
        try{
            return task.get();
        }catch(Throwable throwable){
            Log.e("SALESDROID","Error Creating New customer",throwable);
        }
        return 0;
    }

    public LiveData<List<Customer>> getAllCustomerDetails(){
        Future<LiveData<List<Customer>>> task = databaseExecutor.submit(new Callable<LiveData<List<Customer>>>() {
            @Override
            public LiveData<List<Customer>> call() throws Exception {
                return salesDroidDatabase.customerDao().getAllCustomerDetails();
            }
        });
        try{
            return task.get();
        }catch(Throwable throwable){
            Log.e("Salesdroid","Error while getting all custmers Details", throwable);
        }
        return  null;
    }

    public void deleteStock(final Stock stock) {
        databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                salesDroidDatabase.stockDao().deleteStock(stock);
            }
        });
    }

    public LiveData<List<Sales>> getAllSales() {
        Future<LiveData<List<Sales>>> task = databaseExecutor.submit(new Callable<LiveData<List<Sales>>>() {
            @Override
            public LiveData<List<Sales>> call() throws Exception {
                return salesDroidDatabase.salesDao().getAllSales();
            }
        });
        try{
            return task.get();
        }catch (Throwable throwable){
            Log.e("SalesDroid","Error getting all sales",throwable);
        }
        return null;
    }

    public LiveData<List<DebtorsModel>> getDebtors() {
        Future<LiveData<List<DebtorsModel>>> task = databaseExecutor.submit(new Callable<LiveData<List<DebtorsModel>>>() {
            @Override
            public LiveData<List<DebtorsModel>> call() throws Exception {
                return salesDroidDatabase.salesDao().getDebtors();
            }
        });
        try{
            return task.get();
        }catch(Exception e){
            Log.e("SalesDroid","Error getting Debtors List",e);
        }
        return null;
    }
}