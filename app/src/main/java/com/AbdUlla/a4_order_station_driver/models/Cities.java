package com.AbdUlla.a4_order_station_driver.models;

import java.util.ArrayList;

public class Cities {
    private ArrayList<City> cities;

    public Cities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    @Override
    public String toString() {
        return "Cities{" +
                "cities=" + cities +
                '}';
    }
}
