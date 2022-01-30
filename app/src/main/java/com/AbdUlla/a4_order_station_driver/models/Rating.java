package com.AbdUlla.a4_order_station_driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Rating extends Message {

    @SerializedName("data")
    @Expose
    private ArrayList<RatingData> data;

    @SerializedName("rate")
    @Expose
    private String rate;

    public String getRate() {
        return rate;
    }

    public ArrayList<RatingData> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "TestRating{" +
                "rate='" + rate + '\'' +
                '}';
    }

    public class RatingData {

    }
}
