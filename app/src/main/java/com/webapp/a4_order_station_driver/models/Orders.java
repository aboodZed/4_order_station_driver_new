package com.webapp.a4_order_station_driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Orders {

    @SerializedName("has_more_page")
    @Expose
    private boolean more_page;

    @SerializedName("in_progress_order")
    @Expose
    private ArrayList<Order> in_progress_order;

    @SerializedName("delivered_orders")
    @Expose
    private ArrayList<OrderStation> delivered_orders;

    @SerializedName("delivered_public_orders")
    @Expose
    private ArrayList<PublicOrder> delivered_public_orders;

    public boolean isMore_page() {
        return more_page;
    }

    public ArrayList<Order> getIn_progress_order() {
        return in_progress_order;
    }

    public ArrayList<OrderStation> getDelivered_orders() {
        return delivered_orders;
    }

    public ArrayList<PublicOrder> getDelivered_public_orders() {
        return delivered_public_orders;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "more_page=" + more_page +
                ", in_progress_order=" + in_progress_order +
                ", delivered_orders=" + delivered_orders +
                ", delivered_public_orders=" + delivered_public_orders +
                '}';
    }
}

