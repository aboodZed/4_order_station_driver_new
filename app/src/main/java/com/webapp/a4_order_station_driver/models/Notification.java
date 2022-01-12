package com.webapp.a4_order_station_driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification extends Message {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("notifiable_type")
    @Expose
    private String notifiable_type;

    @SerializedName("notifiable_id")
    @Expose
    private String notifiable_id;

    @SerializedName("data")
    @Expose
    private NotificationData data;

    @SerializedName("read_at")
    @Expose
    private String read_at;

    @SerializedName("created_at")
    @Expose
    private long created_at;

    public Notification() {
        super();
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getNotifiable_type() {
        return notifiable_type;
    }

    public String getNotifiable_id() {
        return notifiable_id;
    }

    public NotificationData getData() {
        return data;
    }

    public String getRead_at() {
        return read_at;
    }

    public long getCreated_at() {
        return created_at;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", notifiable_type='" + notifiable_type + '\'' +
                ", notifiable_id='" + notifiable_id + '\'' +
                ", data=" + data +
                ", read_at='" + read_at + '\'' +
                ", created_at=" + created_at +
                '}';
    }

    public class NotificationData {

        @SerializedName("msg")
        @Expose
        private String msg;

        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("type")
        @Expose
        private String type;

        @SerializedName("invoice_number")
        @Expose
        private String invoice_number;

        @SerializedName("status")
        @Expose
        private String status;

        @SerializedName("store_name")
        @Expose
        private String store_name;

        @SerializedName("country_id")
        @Expose
        private String country_id;

        @SerializedName("public_order_id")
        @Expose
        private int public_order_id;

        @SerializedName("order_id")
        @Expose
        private int order_id;


        public String getMsg() {
            return msg;
        }

        public String getTitle() {
            return title;
        }

        public String getType() {
            return type;
        }

        public int getCountry_id() {
            return Integer.parseInt(country_id);
        }

        public int getPublic_order_id() {
            return public_order_id;
        }

        public int getOrder_id() {
            return order_id;
        }



        @Override
        public String toString() {
            return "NotificationData{" +
                    "msg='" + msg + '\'' +
                    ", title='" + title + '\'' +
                    ", type='" + type + '\'' +
                    ", country_id='" + country_id + '\'' +
                    ", public_order_id=" + public_order_id +
                    ", order_id=" + order_id +
                    '}';
        }
    }
}

