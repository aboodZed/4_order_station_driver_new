package com.webapp.a4_order_station_driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Customer implements Serializable {


    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("lat")
    @Expose
    private String lat;

    @SerializedName("lng")
    @Expose
    private String lng;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("full_mobile")
    @Expose
    private String full_mobile;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("logo_url")
    @Expose
    private String logo_url;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    public int getId() {
        return id;
    }

    public double getLat() {
        return Double.parseDouble(lat);
    }

    public double getLng() {
        return Double.parseDouble(lng);
    }

    public String getName() {
        return name;
    }

    public String getFull_mobile() {
        return full_mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getLogo_url() {
        return logo_url;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", full_mobile='" + full_mobile + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", logo_url='" + logo_url + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
