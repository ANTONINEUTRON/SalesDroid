package com.neutron.salesdroid.data.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.neutron.salesdroid.data.model.Customer;

import java.util.List;

@Dao
public interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertCustomer(Customer customer);

    @Query("SELECT DISTINCT id FROM customer WHERE customerName = :customerName and email = :customerEmail")
    int getCustomerId(String customerName,String customerEmail);

    @Query("SELECT * FROM customer")
    LiveData<List<Customer>> getAllCustomerDetails();
}
