package com.AbdUlla.a4_order_station_driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("country")
    @Expose
    private Country country;

    @SerializedName("city")
    @Expose
    private City city;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("rate")
    @Expose
    private float rate;

    @SerializedName("is_online")
    @Expose
    private boolean online;

    @SerializedName("is_complete")
    @Expose
    private boolean complete;

    @SerializedName("avatar")
    @Expose
    private String avatar_url;

    @SerializedName("id_pic")
    @Expose
    private String id_pic_url;

    @SerializedName("drive_license")
    @Expose
    private String driver_license_url;

    @SerializedName("vehicle_pic")
    @Expose
    private String vehicle_pic_url;

    @SerializedName("vehicle_license")
    @Expose
    private String vehicle_license_url;

    @SerializedName("insurance_license")
    @Expose
    private String Insurance_license_url;

    @SerializedName("balance")
    @Expose
    private Balance balance;

    public int getId() {
        return id;
    }

    public Country getCountry() {
        return country;
    }

    public City getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getInsurance_license_url() {
        return Insurance_license_url;
    }

    public String getDriver_license_url() {
        return driver_license_url;
    }

    public String getVehicle_license_url() {
        return vehicle_license_url;
    }

    public String getId_pic_url() {
        return id_pic_url;
    }

    public String getVehicle_pic_url() {
        return vehicle_pic_url;
    }

    public float getRate() {
        return rate;
    }

    public String getToken() {
        return token;
    }

    public boolean isComplete() {
        return complete;
    }

    public Balance getBalance() {
        return balance;
    }

    /*
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", active='" + active + '\'' +
                ", email_verified_at='" + email_verified_at + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", delivery_address='" + delivery_address + '\'' +
                ", lang='" + lang + '\'' +
                ", mobile_verify_code='" + mobile_verify_code + '\'' +
                ", reset_password_verify_code='" + reset_password_verify_code + '\'' +
                ", insurance_license='" + insurance_license + '\'' +
                ", drive_license='" + drive_license + '\'' +
                ", vehicle_license='" + vehicle_license + '\'' +
                ", vehicle_no='" + vehicle_no + '\'' +
                ", vehicle_plate='" + vehicle_plate + '\'' +
                ", vehicle_type='" + vehicle_type + '\'' +
                ", id_pic='" + id_pic + '\'' +
                ", id_no='" + id_no + '\'' +
                ", complete_reg='" + complete_reg + '\'' +
                ", vehicle_pic='" + vehicle_pic + '\'' +
                ", is_online='" + is_online + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", deleted_at='" + deleted_at + '\'' +
                ", country_id=" + country_id +
                ", city_id=" + city_id +
                ", avatar_url='" + avatar_url + '\'' +
                ", Insurance_license_url='" + Insurance_license_url + '\'' +
                ", drive_license_url='" + drive_license_url + '\'' +
                ", vehicle_license_url='" + vehicle_license_url + '\'' +
                ", id_pic_url='" + id_pic_url + '\'' +
                ", vehicle_pic_url='" + vehicle_pic_url + '\'' +
                ", rate=" + rate +
                ", password='" + password + '\'' +
                ", password_confirmation='" + password_confirmation + '\'' +
                ", content_type='" + content_type + '\'' +
                '}';
    }*/

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", country=" + country +
                ", city=" + city +
                ", token='" + token + '\'' +
                ", address='" + address + '\'' +
                ", rate=" + rate +
                ", online=" + online +
                ", complete=" + complete +
                ", avatar_url='" + avatar_url + '\'' +
                ", id_pic_url='" + id_pic_url + '\'' +
                ", driver_license_url='" + driver_license_url + '\'' +
                ", vehicle_pic_url='" + vehicle_pic_url + '\'' +
                ", vehicle_license_url='" + vehicle_license_url + '\'' +
                ", Insurance_license_url='" + Insurance_license_url + '\'' +
                '}';
    }

    public static HashMap<String, String> mapUploadStepOne(String name, String mobile, String email
            , String password, String address, int country_id, int city_id, String avatar) {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("mobile", mobile);
        map.put("email", email);
        map.put("password", password);
        map.put("password_confirmation", password);
        map.put("address", address);
        map.put("country_id", String.valueOf(country_id));
        map.put("city_id", String.valueOf(city_id));
        map.put("avatar", avatar);
        return map;
    }

    public class Balance {
        @SerializedName("is_subscribe")
        @Expose
        private boolean is_subscribe;

        @SerializedName("orders_balance")
        @Expose
        private String orders_balance;

        @SerializedName("end_date")
        @Expose
        private boolean end_date;

        public boolean getIsSubscribe() {
            return is_subscribe;
        }

        public String getOrders_balance() {
            return orders_balance;
        }

        public boolean getEnd_date() {
            return end_date;
        }
    }
}
