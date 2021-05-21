package com.neutron.salesdroid.ui.addSales;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.neutron.salesdroid.data.Repository;
import com.neutron.salesdroid.data.model.Customer;
import com.neutron.salesdroid.data.model.Sales;
import com.neutron.salesdroid.data.model.Stock;
import com.neutron.salesdroid.data.model.Transaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddSalesViewModel extends AndroidViewModel {
    private Repository repository;
    private Transaction transactionDetails;

    public AddSalesViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void setTransactionDetails(Transaction transactionDetails){
        this.transactionDetails = transactionDetails;
    }

    public  void saveValues(){
        Sales sales = prepareValues();
        //access repo and save
        repository.createSales(sales);
    }

    public void updatePS() {
        Sales sales = prepareValues();
        //update values
        repository.updatePaymentStatus(sales.getPaymentStatus(),sales.getCustomerId(),transactionDetails.getDate(),sales.getDate());//transactiondetails contains old date
    }

    public Sales prepareValues() {
        //check if customer details exist, create if not
        Customer customer = new Customer(
                transactionDetails.getCustomerName(),
                transactionDetails.getCustomerPhnNumber(),
                transactionDetails.getCustomerAddress(),
                transactionDetails.getCustomerEmail());
        int customerId = repository.customerExists(customer);
        if(customerId == 0){
            Long id = repository.createNewCustomer(customer);
            customerId = id.intValue();
        }
        //fit into sales model
        DateFormat df = new SimpleDateFormat("EEEE, dd/MM/yyyy HH:mm");
        return new Sales(customerId,transactionDetails.getStockName(),transactionDetails.getQuantity(),transactionDetails.getUnit(),transactionDetails.getPrice(),
                transactionDetails.getPaymentStatus(),df.format(new Date()),transactionDetails.getDiscount());
    }

    public LiveData<List<Stock>> getStockDetails(){
        return repository.getAllStocks();
    }

    public LiveData<List<Customer>> getAllCustomerDetails(){
        return repository.getAllCustomerDetails();
    }
}
