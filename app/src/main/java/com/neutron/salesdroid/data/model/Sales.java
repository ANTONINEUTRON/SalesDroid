package com.neutron.salesdroid.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sales")
public class Sales {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private int customerId;
    private String stockName;
    private int quantitySold;
    private String unit;
    private double price;
    private String paymentStatus;
    private String date;
    private double discount;

    public Sales(int customerId, String stockName, int quantitySold, String unit, double price, String paymentStatus, String date, double discount) {
        this.customerId = customerId;
        this.stockName = stockName;
        this.quantitySold = quantitySold;
        this.unit = unit;
        this.price = price;
        this.paymentStatus = paymentStatus;
        this.date = date;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}