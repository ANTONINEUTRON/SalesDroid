package com.neutron.salesdroid.utils;

import android.app.Application;
import android.content.Context;

import com.neutron.salesdroid.data.Repository;
import com.neutron.salesdroid.data.model.Customer;
import com.neutron.salesdroid.data.model.Sales;
import com.neutron.salesdroid.data.model.Transaction;

public class DeleteTransaction {
    Context context;
    private Repository repository;

    public DeleteTransaction(Context context) {
        this.context = context;
    }

    public void delete(Transaction transaction){
        repository = new Repository((Application)context.getApplicationContext());
        //get customer id
        Customer customer = new Customer(transaction.getCustomerName(),transaction.getCustomerPhnNumber(),transaction.getCustomerAddress(),transaction.getCustomerEmail());
        int customerId = repository.customerExists(customer);
        //create sales instance
        Sales sales = new Sales(customerId,transaction.getStockName(),transaction.getQuantity(),transaction.getUnit(),transaction.getPrice(),transaction.getPaymentStatus(),transaction.getDate(),transaction.getDiscount());
        //pass to repository
        repository.deleteSales(sales);
    }
}
