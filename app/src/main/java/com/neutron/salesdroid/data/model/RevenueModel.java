package com.neutron.salesdroid.data.model;

public class RevenueModel {
    private double price;
    private String date;

    public RevenueModel(double price, String date) {
        this.price = price;
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
