package com.AbdUlla.a4_order_station_driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderStationItem implements Serializable {

//    @SerializedName("TestOrderItem")
//    private OrderStationITemData TestOrderItem;
//
//    public OrderStationITemData getTestOrderItem() {
//        return TestOrderItem;
//    }
//
//    @Override
//    public String toString() {
//        return "OrderStationItem{" +
//                "TestOrderItem=" + TestOrderItem +
//                '}';
//    }
//
//    public class OrderStationITemData implements Serializable {
        @SerializedName("id")
        @Expose
        private int id;

        @SerializedName("name")
        @Expose
        private String name;

//    @SerializedName("price")
//    @Expose
//    private double price;

        @SerializedName("qty")
        @Expose
        private String qty;

        @SerializedName("price")
        @Expose
        private String price;

        @SerializedName("extra_items")
        @Expose
        private ArrayList<OrderStationItemExtra> extra_items;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

//    public double getPrice() {
//        return price;
//    }

        public String getQty() {
            return qty;
        }

        public String getPrice() {
            return price;
        }

        public ArrayList<OrderStationItemExtra> getExtra_items() {
            return extra_items;
        }

        @Override
        public String toString() {
            return "TestOrderItem{" +
                    "id=" + id +
                    ", item_name='" + name + '\'' +
                    //", price=" + price +
                    ", qty=" + qty +
                    ", total=" + price +
                    ", extra_items=" + extra_items +
                    '}';
        }
  //  }
}
