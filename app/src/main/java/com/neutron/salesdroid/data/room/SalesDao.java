package com.neutron.salesdroid.data.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.neutron.salesdroid.data.model.DebtorsModel;
import com.neutron.salesdroid.data.model.Sales;
import com.neutron.salesdroid.data.model.Transaction;

import java.util.List;

@Dao
public interface SalesDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSales(Sales sales);

    @Query("SELECT sales.date AS date,customer.customerName AS customerName, "
            +"sales.unit AS unit, sales.stockName AS stockName, "
            +"sales.quantitySold AS quantity, sales.price AS price, "
            +"sales.discount AS discount, sales.paymentStatus AS paymentStatus, "
            +"customer.email AS customerEmail, customer.address AS customerAddress, "
            +"customer.phoneNumber AS customerPhnNumber "
            +"FROM sales LEFT JOIN customer " +
            "ON sales.customerId=customer.id " +
            "LEFT JOIN stocks ON stockName=stocks.name")
    LiveData<List<Transaction>> getAllSalesDetails();

    @Query("SELECT COUNT(*) FROM sales")
    int getTableSize();

    @Query("DELETE FROM sales " +
            "WHERE sales.stockName = :stockName AND sales.date = :date AND sales.quantitySold = :quantity")
    void deleteSales(String stockName, String date, int quantity);

    @Query("UPDATE sales SET paymentStatus = :pS, date = :newDate " +
            "WHERE customerId = :customerId AND date = :oldDate")
    void updatePaymentStatus(String pS, int customerId, String oldDate, String newDate);

    @Query("SELECT * FROM sales")
    LiveData<List<Sales>> getAllSales();

    @Query("SELECT customerName AS name, price AS amountOwed " +
            "FROM customer LEFT JOIN sales " +
            "ON sales.customerId=customer.id " +
            "WHERE paymentStatus <> 'Paid'")
    LiveData<List<DebtorsModel>> getDebtors();
}
