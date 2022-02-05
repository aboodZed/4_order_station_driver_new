package com.AbdUlla.a4_order_station_driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderStation extends Order implements Serializable {


//    @SerializedName("order_date")
//    @Expose
//    private String order_date;
//
//    @SerializedName("type_of_receive")
//    @Expose
//    private String type_of_receive;

//    @SerializedName("type")
//    @Expose
//    private String type_of_order_station;

    @SerializedName("sub_total_1")
    @Expose
    private String sub_total_1;

    @SerializedName("discount")
    @Expose
    private String discount;

    @SerializedName("sub_total_2")
    @Expose
    private String sub_total_2;

    @SerializedName("tax")
    @Expose
    private String tax;

    @SerializedName("delivery")
    @Expose
    private String delivery;

    @SerializedName("customer_id")
    @Expose
    private String customer_id;

//    @SerializedName("total")
//    @Expose
//    private String total;

    @SerializedName(value = "items", alternate = {"orderItems"})
    @Expose
    private ArrayList<OrderStationItem> orderItems;
//
//    @SerializedName("store")
//    @Expose
//    private Customer store;
//
//    @SerializedName("customer_address")
//    @Expose
//    private Customer customer;

//    @SerializedName("driver")
//    @Expose
//    private User driver;
//
//    public String getInvoice_number() {
//        return invoice_number;
//    }
//
//    public String getStatus_translation() {
//        return status_translation;
//    }
//
//    public String getOrder_date() {
//        return order_date;
//    }
//
//    public String getType_of_receive() {
//        return type_of_receive;
//    }

    public String getSub_total_1() {
        return sub_total_1;
    }

    public String getDiscount() {
        return discount;
    }

    public String getSub_total_2() {
        return sub_total_2;
    }

    public String getTax() {
        return tax;
    }

    public String getDelivery() {
        return delivery;
    }

//    public String getTotal() {
//        return total;
//    }

    public ArrayList<OrderStationItem> getOrderItems() {
        return orderItems;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    //
//    public Customer getStore() {
//        return store;
//    }
//
//    public Customer getCustomer() {
//        return customer;
//    }

//    public User getDriver() {
//        return driver;
//    }

    @Override
    public String toString() {
        return "TestOrder{" +
                "id='" + getId() + "\'" +
                ", status='" + getStatus() + "\'" +
//                ", invoice_number='" + invoice_number + '\'' +
//                ", status_translation='" + status_translation + '\'' +
//                ", order_date='" + order_date + '\'' +
//                ", type_of_receive='" + type_of_receive + '\'' +
                ", sub_total_1=" + sub_total_1 +
                ", discount=" + discount +
                ", sub_total_2=" + sub_total_2 +
                ", tax=" + tax +
                ", delivery=" + delivery +
                //", total=" + total +
                ", orderItems=" + orderItems +
//                ", store=" + store +
//                ", customer=" + customer +
//                ", driver=" + driver +
                '}';
    }
}
