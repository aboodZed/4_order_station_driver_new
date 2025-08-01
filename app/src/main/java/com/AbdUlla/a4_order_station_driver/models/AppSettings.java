package com.AbdUlla.a4_order_station_driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AppSettings extends Result<AppSettings.SettingsData> {

    @SerializedName("privacy_title")
    @Expose
    private String privacy_title;

    @SerializedName("privacy_content")
    @Expose
    private String privacy_content;

    private String fcm_token;

    public String getPrivacy_title() {
        return privacy_title;
    }

    public String getPrivacy_content() {
        return privacy_content;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    @Override
    public String toString() {
        return "AppSettings{" +
                "privacy_title='" + privacy_title + '\'' +
                ", privacy_content='" + privacy_content + '\'' +
                '}';
    }

    public class SettingsData implements Serializable {

        @SerializedName("facebook_link")
        @Expose
        private String facebook_link;

        @SerializedName("instagram_link")
        @Expose
        private String instagram_link;

        @SerializedName("linkedin_link")
        @Expose
        private String linkedin_link;

        @SerializedName("twitter_link")
        @Expose
        private String twitter_link;

        @SerializedName("telegram_link")
        @Expose
        private String telegram_link;

        @SerializedName("mobile")
        @Expose
        private String mobile;

        @SerializedName("email")
        @Expose
        private String email;

        @SerializedName("number_of_orders")
        @Expose
        private String number_of_orders;

        @SerializedName("price_of_orders")
        @Expose
        private String price_of_orders;

        @SerializedName("currency_code")
        @Expose
        private String currency_code;

        @SerializedName("phone_code")
        @Expose
        private String phone_code;

        @SerializedName("phone_length")
        @Expose
        private int phone_length;

        @SerializedName("country_id")
        @Expose
        private int country_id;

        public String getFacebook_link() {
            return facebook_link;
        }

        public String getInstagram_link() {
            return instagram_link;
        }

        public String getLinkedin_link() {
            return linkedin_link;
        }

        public String getTwitter_link() {
            return twitter_link;
        }

        public String getTelegram_link() {
            return telegram_link;
        }

        public String getMobile() {
            return mobile;
        }

        public String getEmail() {
            return email;
        }

        public String getNumber_of_orders() {
            return number_of_orders;
        }

        public String getPrice_of_orders() {
            return price_of_orders;
        }

        public String getCurrency_code() {
            return currency_code;
        }

        public String getPhone_code() {
            return phone_code;
        }

        public int getPhone_length() {
            return phone_length;
        }

        public int getCountry_id() {
            return country_id;
        }

        @Override
        public String toString() {
            return "SettingsData{" +
                    "facebook_link='" + facebook_link + '\'' +
                    ", instagram_link='" + instagram_link + '\'' +
                    ", linkedin_link='" + linkedin_link + '\'' +
                    ", twitter_link='" + twitter_link + '\'' +
                    ", telegram_link='" + telegram_link + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", email='" + email + '\'' +
                    ", number_of_orders='" + number_of_orders + '\'' +
                    ", price_of_orders='" + price_of_orders + '\'' +
                    ", currency_code='" + currency_code + '\'' +
                    ", phone_code='" + phone_code + '\'' +
                    ", phone_length=" + phone_length +
                    ", country_id=" + country_id +
                    '}';
        }
    }
}
