package com.AbdUlla.a4_order_station_driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Result<T> extends Message implements Serializable {

    @SerializedName(value = "data", alternate = {"app_settings", "wallets", "cities", "public_order"})
    @Expose
    private T data;

    @SerializedName("errors")
    @Expose
    private ArrayList<String> errors;

    public T getData() {
        return data;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        return "Result{" +
                "data=" + data +
                ", errors=" + errors +
                '}';
    }
}
