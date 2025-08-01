package com.AbdUlla.a4_order_station_driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("invoice_number")
    @Expose
    private String invoice_number;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName(value = "status_translation", alternate = {"status_label"})
    @Expose
    private String status_translation;

    //public order
    @SerializedName("store_name")
    @Expose
    private String store_name;

    @SerializedName("store_lat")
    @Expose
    private String store_lat;

    @SerializedName("store_lng")
    @Expose
    private String store_lng;

    @SerializedName("store_address")
    @Expose
    private String store_address;

    @SerializedName("created_timestamp")
    @Expose
    private long created_timestamp;

    @SerializedName("customer")
    @Expose
    private Customer customer;

//    @SerializedName("customer_address")
//    @Expose
//    private Customer customer_address;

    @SerializedName("total")
    @Expose
    private String total;

    @SerializedName("driver")
    private User driver;

    //4orderstation
    @SerializedName("order_date")
    @Expose
    private String order_date;

    @SerializedName("type_of_receive")
    @Expose
    private String type_of_receive;

    @SerializedName(value = "items_count", alternate = {"extra_items_count"})
    @Expose
    private String items_count;

    @SerializedName("store")
    @Expose
    private Customer store;

    @SerializedName("payment_type")
    @Expose
    private String payment_type;

    //customer address
    @SerializedName("location_name")
    @Expose
    private String location_name;

    @SerializedName("receiver_name")
    @Expose
    private String receiver_name;

    @SerializedName("receiver_address")
    @Expose
    private String receiver_address;

    @SerializedName("receiver_phone")
    @Expose
    private String receiver_phone;

    @SerializedName("lat")
    @Expose
    private String lat;

    @SerializedName("lng")
    @Expose
    private String lng;

    @SerializedName("order_type")
    @Expose
    private String order_type;

    private String type;

    public int getId() {
        return id;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public String getStatus_translation() {
        return status_translation;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrder_type() {
        return order_type;
    }

    public String getStore_name() {
        return store_name;
    }

    public double getStore_lat() {
        return Double.parseDouble(store_lat);
    }

    public double getStore_lng() {
        return Double.parseDouble(store_lng);
    }

    public String getStore_address() {
        return store_address;
    }

    public long getCreated_timestamp() {
        return created_timestamp;
    }

    public Customer getCustomer() {
        return customer;
    }

//    public Customer getCustomer_address() {
//        return customer_address;
//    }

    public String getTotal() {
        return total;
    }

    public String getOrder_date() {
        return order_date;
    }

    public String getType_of_receive() {
        return type_of_receive;
    }

    public String getItems_count() {
        return items_count;
    }

    public Customer getStore() {
        return store;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public User getDriver() {
        return driver;
    }

    public String getLocation_name() {
        return location_name;
    }

    public String getReceiver_address() {
        return receiver_address;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", invoice_number='" + invoice_number + '\'' +
                ", status='" + status + '\'' +
                ", status_translation='" + status_translation + '\'' +
                ", store_name='" + store_name + '\'' +
                ", store_lat='" + store_lat + '\'' +
                ", store_lng='" + store_lng + '\'' +
                ", store_address='" + store_address + '\'' +
                ", created_timestamp=" + created_timestamp +
                ", customer=" + customer +
                //", customer_address=" + customer_address +
                ", total='" + total + '\'' +
                ", order_date='" + order_date + '\'' +
                ", type_of_receive='" + type_of_receive + '\'' +
                ", items_count='" + items_count + '\'' +
                ", store=" + store +
                ", payment_type='" + payment_type + '\'' +
                ", type='" + type + '\'' +
                ", order_type='" + order_type + '\'' +
                ", driver='" + driver + '\'' +
                '}';
    }
}
