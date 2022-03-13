package com.AbdUlla.a4_order_station_driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PublicOrder extends Order implements Serializable {

//    @SerializedName("place_id")
//    @Expose
//    private String place_id;

//    @SerializedName("store_name")
//    @Expose
//    private String store_name;

    @SerializedName("purchase_invoice_value")
    @Expose
    private String purchase_invoice_value;

    @SerializedName("delivery_cost")
    @Expose
    private String delivery_cost;

    @SerializedName("tax")
    @Expose
    private String tax;

    @SerializedName("client_paid_invoice")
    @Expose
    private String client_paid_invoice;

    @SerializedName("app_delivery_commission")
    @Expose
    private String app_delivery_commission;

    @SerializedName("driver_revenue")
    @Expose
    private String driver_revenue;



//    @SerializedName("total")
//    @Expose
//    private String total;

//    @SerializedName("store_lat")
//    @Expose
//    private String store_lat;
//
//    @SerializedName("store_lng")
//    @Expose
//    private String store_lng;
//
//    @SerializedName("store_address")
//    @Expose
//    private String store_address;

//    @SerializedName("destination_lat")
//    @Expose
//    private String destination_lat;
//
//    @SerializedName("destination_lng")
//    @Expose
//    private String destination_lng;
//
//    @SerializedName("destination_address")
//    @Expose
//    private String destination_address;
//
//    @SerializedName("client_id")
//    @Expose
//    private String client_id;
//
//    @SerializedName("driver_id")
//    @Expose
//    private String driver_id;

    @SerializedName("note")
    @Expose
    private String note;

//    @SerializedName("status_label")
//    @Expose
//    private String status_label;

//    @SerializedName("cancel_reason")
//    @Expose
//    private String cancel_reason;
//
//    @SerializedName("cancel_reasons_id")
//    @Expose
//    private String cancel_reasons_id;

//    @SerializedName("created_timestamp")
//    @Expose
//    private long created_timestamp;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

//    @SerializedName("order_pic")
//    @Expose
//    private String order_pic;
//
//    @SerializedName("order_pic_url")
//    @Expose
//    private String order_pic_url;
//
//    @SerializedName("status_translation")
//    @Expose
//    private String status_translation;

//    @SerializedName("customer")
//    @Expose
//    private User user;
//
//    @SerializedName("customer_address")
//    @Expose
//    private Customer customer;

//    @SerializedName("app_revenue")
//    @Expose
//    private String app_revenue;
//
//    @SerializedName("driver_revenue")
//    @Expose
//    private String driver_revenue;
//
//    @SerializedName("client_paid_invoice")
//    @Expose
//    private String client_paid_invoice;

    @SerializedName("attachments")
    @Expose
    private ArrayList<Attachment> attachments;

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

//    public String getClient_paid_invoice() {
//        return client_paid_invoice;
//    }

//    public String getPlace_id() {
//        return place_id;
//    }

//    public String getStore_name() {
//        return store_name;
//    }

//    public String getInvoice_number() {
//        return invoice_number;
//    }

    public String getPurchase_invoice_value() {
        return purchase_invoice_value;
    }

    public String getDelivery_cost() {
        return delivery_cost;
    }

    public String getTax() {
        return tax;
    }

    public String getApp_delivery_commission() {
        return app_delivery_commission;
    }

    public String getDriver_revenue() {
        return driver_revenue;
    }

    //    public String getTotal() {
//        return total;
//    }
//
//    public double getStore_lat() {
//        return Double.parseDouble(store_lat);
//    }
//
//    public double getStore_lng() {
//        return Double.parseDouble(store_lng);
//    }
//
//    public String getStore_address() {
//        return store_address;
//    }

//    public String getDestination_lat() {
//        return destination_lat;
//    }
//
//    public String getDestination_lng() {
//        return destination_lng;
//    }
//
//    public String getDestination_address() {
//        return destination_address;
//    }
//
//    public String getClient_id() {
//        return client_id;
//    }
//
//    public String getDriver_id() {
//        return driver_id;
//    }

    public String getNote() {
        return note;
    }

    public String getClient_paid_invoice() {
        return client_paid_invoice;
    }

    //    public String getStatus_label() {
//        return status_label;
//    }

    //    public String getCancel_reason() {
//        return cancel_reason;
//    }
//
//    public String getCancel_reasons_id() {
//        return cancel_reasons_id;
//    }
//
//    public String getOrder_pic() {
//        return order_pic;
//    }
//
//    public String getOrder_pic_url() {
//        return order_pic_url;
//    }
//
//
//    public String getStatus_translation() {
//        return status_translation;
//    }

//    public long getCreated_timestamp() {
//        return created_timestamp;
//    }

    public String getUpdated_at() {
        return updated_at;
    }

//    public String getApp_revenue() {
//        return app_revenue;
//    }
//
//    public String getDriver_revenue() {
//        return driver_revenue;
//    }

//    public User getUser() {
//        return user;
//    }

//    public Customer getCustomer() {
//        return customer;
//    }

    public void setPurchase_invoice_value(String purchase_invoice_value) {
        this.purchase_invoice_value = purchase_invoice_value;
    }

    @Override
    public String toString() {
        return "PublicOrder{" +
                "id='" + getId() + '\'' +
                //", place_id='" + place_id + '\'' +
                //", store_name='" + store_name + '\'' +
                //", invoice_number='" + invoice_number + '\'' +
                ", purchase_invoice_value='" + purchase_invoice_value + '\'' +
                ", delivery_cost='" + delivery_cost + '\'' +
                ", tax='" + tax + '\'' +
//                ", total='" + total + '\'' +
//                ", store_lat='" + store_lat + '\'' +
//                ", store_lng='" + store_lng + '\'' +
//                ", store_address='" + store_address + '\'' +
                ", note='" + note + '\'' +
                //", status='" + status_label + '\'' +
                //", created_at='" + created_timestamp + '\'' +
                ", updated_at='" + updated_at + '\'' +
//                ", destination_lat='" + destination_lat + '\'' +
//                ", destination_lng='" + destination_lng + '\'' +
//                ", destination_address='" + destination_address + '\'' +
//                ", client_id='" + client_id + '\'' +
//                ", driver_id='" + driver_id + '\'' +
//                ", cancel_reason='" + cancel_reason + '\'' +
//                ", cancel_reasons_id='" + cancel_reasons_id + '\'' +
//                ", order_pic='" + order_pic + '\'' +
//                ", order_pic_url='" + order_pic_url + '\'' +
//                ", status='" + getStatus() + '\'' +
//                ", status_translation='" + status_translation + '\'' +
//                ", app_revenue='" + app_revenue + '\'' +
//                ", driver_revenue='" + driver_revenue + '\'' +
//                ", client_paid_invoice='" + client_paid_invoice + '\'' +
                //", client=" + user +
                //", attachmentArrays=" + attachments +
                '}';
    }
}
