package com.neutron.salesdroid.utils;

import com.neutron.salesdroid.data.model.RevenueModel;
import com.neutron.salesdroid.data.model.Sales;

import java.util.ArrayList;
import java.util.List;

public class RevenueProcessor {
    private List<Sales> salesList;
    private List<RevenueModel> revenueList;

    public RevenueProcessor(List<Sales> salesList) {
        this.salesList = salesList;
        revenueList = new ArrayList<RevenueModel>();
    }

    public List<RevenueModel> getRevenue(Key key){
        doCalculation(key);
        return revenueList;
    }

    private String getDay(String date){
         return date.substring(0,date.length() - 6);
    }

    private String getMonth(String date) {
        String mnth = date.substring(date.indexOf("/")+1, date.length() - 6);//month in number format 01/2021
        String year = mnth.substring(mnth.indexOf('/')+1);
        switch (mnth.substring(0,2)){
            case "01":
                return "January "+year;
            case "02":
                return "February "+year;
            case "03":
                return "March "+year;
            case "04":
                return "April "+year;
            case "05":
                return "May "+year;
            case "06":
                return "June "+year;
            case "07":
                return "July "+year;
            case "08":
                return "August "+year;
            case "09":
                return "September "+year;
            case "10":
                return "October "+year;
            case "11":
                return "November "+year;
            case "12":
                return "December "+year;
        }
        return"Nothing";
    }
    public String get(String date, Key key){
        switch(key){
            case MONTH:
                return getMonth(date);
            default:
                return getDay(date);
        }
    }

    private void doCalculation(Key key){
        double revenue = 0;
        for(int i = 0; i < salesList.size(); i++){
            Sales sales = salesList.get(i);
            String present = get(sales.getDate(), key);
            String next = null;
            if(i != salesList.size()-1){
                next = get(salesList.get(i+1).getDate(),key);
            }
            if(present.equals(next)){
                revenue += (sales.getPrice()-sales.getDiscount());
            }else{
                revenue += (sales.getPrice()-sales.getDiscount());
                RevenueModel revenueModel = new RevenueModel(revenue,present);
                revenueList.add(revenueModel);
                revenue = 0;
            }
        }
    }

    public enum Key{
        DAY,MONTH
    }
}
