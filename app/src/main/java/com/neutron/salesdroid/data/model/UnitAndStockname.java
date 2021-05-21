package com.neutron.salesdroid.data.model;

public class UnitAndStockname {
    private String unit;
    private String stockName;

    public UnitAndStockname(String unit, String stockName) {
        this.unit = unit;
        this.stockName = stockName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}
