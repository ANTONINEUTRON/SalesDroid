package com.neutron.salesdroid.data.model;

public class DebtorsModel {
    private String name;
    private double amountOwed;

    public DebtorsModel(String name, double amountOwed) {
        this.name = name;
        this.amountOwed = amountOwed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmountOwed() {
        return amountOwed;
    }

    public void setAmountOwed(double amountOwed) {
        this.amountOwed = amountOwed;
    }
}