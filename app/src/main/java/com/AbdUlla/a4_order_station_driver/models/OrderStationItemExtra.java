package com.AbdUlla.a4_order_station_driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderStationItemExtra implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("item_name")
    @Expose
    private String item_name;

    public int getId() {
        return id;
    }

    public String getName() {
        return item_name;
    }

    @Override
    public String toString() {
        return "OrderStationItemExtra{" +
                "id=" + id +
                ", item_name='" + item_name + '\'' +
                '}';
    }
}

