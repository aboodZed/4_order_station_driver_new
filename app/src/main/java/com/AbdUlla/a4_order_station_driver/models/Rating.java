package com.AbdUlla.a4_order_station_driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Rating extends Result<ArrayList<Rating.RatingData>> {

    @SerializedName("rate")
    @Expose
    private String rate;

    public String getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "TestRating{" +
                "rate='" + rate + '\'' +
                '}';
    }

    public class RatingData {

        @SerializedName("rate")
        @Expose
        private String rate;

        @SerializedName("review")
        @Expose
        private String review;

        @SerializedName("customer")
        @Expose
        private Customer customer;

        public String getRate() {
            return rate;
        }

        public String getReview() {
            return review;
        }

        public Customer getCustomer() {
            return customer;
        }

        @Override
        public String toString() {
            return "RatingData{" +
                    "rate='" + rate + '\'' +
                    ", review='" + review + '\'' +
                    ", customer=" + customer +
                    '}';
        }
    }
}
