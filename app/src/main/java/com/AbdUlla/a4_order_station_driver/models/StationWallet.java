package com.AbdUlla.a4_order_station_driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StationWallet extends Message {

    @SerializedName("details")
    @Expose
    private ArrayList<Ongoing> details;

    @SerializedName("orders_number")
    @Expose
    private String orders_number;

    public ArrayList<Ongoing> getDetails() {
        return details;
    }

    public String getOrdersNumber() {
        return orders_number;
    }
}
