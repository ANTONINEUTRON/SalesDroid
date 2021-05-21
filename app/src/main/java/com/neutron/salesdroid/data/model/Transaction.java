package com.neutron.salesdroid.data.model;

import java.io.Serializable;

public class Transaction implements Serializable {
    private String date;
    private String customerName;
    private String unit;
    private String stockName;
    private int quantity;
    private double price;
    private double discount;
    private String paymentStatus;
    private String customerEmail;
    private String customerAddress;
    private String customerPhnNumber;

    public  Transaction(){
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
        if(unit==null){
            this.unit = "unit";
        }
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhnNumber() {
        return customerPhnNumber;
    }

    public void setCustomerPhnNumber(String customerPhnNumber) {
        this.customerPhnNumber = customerPhnNumber;
    }
}
