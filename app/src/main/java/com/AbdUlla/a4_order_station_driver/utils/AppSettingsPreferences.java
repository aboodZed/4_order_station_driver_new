package com.AbdUlla.a4_order_station_driver.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.AbdUlla.a4_order_station_driver.models.Cities;
import com.AbdUlla.a4_order_station_driver.models.City;
import com.AbdUlla.a4_order_station_driver.models.Result;
import com.google.gson.Gson;
import com.AbdUlla.a4_order_station_driver.models.AppSettings;
import com.AbdUlla.a4_order_station_driver.models.OrderStation;
import com.AbdUlla.a4_order_station_driver.models.PublicOrder;
import com.AbdUlla.a4_order_station_driver.models.User;
import com.AbdUlla.a4_order_station_driver.utils.language.AppLanguageUtil;

import java.util.ArrayList;

public class AppSettingsPreferences {

    public final static String KEY_APP_LANGUAGE = "app_language";
    public final static String KEY_FIRST_RUN = "first_run";
    public final static String KEY_USER = "user";
    public final static String KEY_SETTINGS_DATA = "settings_data";
    public final static String KEY_USER_PASSWORD = "password";
    private static final String USER_TOKEN = "UserToken";
    private static final String TRACKING_ORDER_STATION = "trackingOrderStation";
    private static final String TRACKING_PUBLIC_ORDER = "trackingPublicOrder";
    private static final String CITIES = "cities";
    private static final String PAY_BILL = "pay_bill";


    private static final String PREF_NAME = "AppSettingsPreferences";

    private int PRIVATE_MODE = 0;
    private SharedPreferences pref;

    private SharedPreferences.Editor editor;


    public AppSettingsPreferences(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstRun() {
        editor.putBoolean(KEY_FIRST_RUN, false);
        editor.apply();
    }

    public boolean isFirstRun() {
        return pref.getBoolean(KEY_FIRST_RUN, true);
    }

    public void setAppLanguage(String appLanguage) {
        editor.putString(KEY_APP_LANGUAGE, appLanguage);
        editor.apply();
    }

    public String getAppLanguage() {
        return pref.getString(KEY_APP_LANGUAGE, AppLanguageUtil.English);
    }

    public void setUser(User user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(KEY_USER, json);
        editor.apply();
    }

    public User getUser() {
        Gson gson = new Gson();
        String json = pref.getString(KEY_USER, "");
        return gson.fromJson(json, User.class);
    }


    public void setToken(String token) {
        editor.putString(USER_TOKEN, "Bearer " + token);
        editor.apply();
    }

    public String getToken() {
        return pref.getString(USER_TOKEN, "");
    }

    public void setAppSettings(AppSettings settings) {
        Gson gson = new Gson();
        String json = gson.toJson(settings);
        editor.putString(KEY_SETTINGS_DATA, json);
        editor.apply();
    }

    public AppSettings getSettings() {
        Gson gson = new Gson();
        String json = pref.getString(KEY_SETTINGS_DATA, "");
        return gson.fromJson(json, AppSettings.class);
    }

    /*public void setPassword(String password) {
        editor.putString(KEY_USER_PASSWORD, password);
        editor.apply();
    }

    public String getPassword() {
        return pref.getString(KEY_USER_PASSWORD, "");
    }*/

    public void setTrackingOrderStation(OrderStation order) {
        order.setType(AppContent.TYPE_ORDER_4STATION);
        Gson gson = new Gson();
        String json = gson.toJson(order);
        editor.putString(TRACKING_ORDER_STATION, json);
        editor.apply();
    }

    public OrderStation getTrackingOrderStation() {
        Gson gson = new Gson();
        String json = pref.getString(TRACKING_ORDER_STATION, null);
        return gson.fromJson(json, OrderStation.class);
    }

    public void setTrackingPublicOrder(PublicOrder order) {
        order.setType(AppContent.TYPE_ORDER_PUBLIC);
        Gson gson = new Gson();
        String json = gson.toJson(order);
        editor.putString(TRACKING_PUBLIC_ORDER, json);
        editor.apply();
    }

    public PublicOrder getTrackingPublicOrder() {
        Gson gson = new Gson();
        String json = pref.getString(TRACKING_PUBLIC_ORDER, null);
        return gson.fromJson(json, PublicOrder.class);
    }

    public void removeAnyOrder() {
        editor.remove(TRACKING_ORDER_STATION);
        editor.remove(TRACKING_PUBLIC_ORDER);
        editor.apply();
    }

    /*public void setCountry(Country country) {
        Gson gson = new Gson();
        String json = gson.toJson(country);
        editor.putString(COUNTRY, json);
        editor.apply();
    }

    public Country getCountry() {
        Gson gson = new Gson();
        String json = pref.getString(COUNTRY, null);
        return gson.fromJson(json, Country.class);
    }*/

    public void setIsPayTheBill(String s) {
        editor.putString(PAY_BILL, s);
        editor.apply();
    }

    public boolean isPayTheBill() {
        return pref.getString(PAY_BILL, "").equals(AppContent.PAYMENT_CONFIRM);
    }

    public void clean() {
        editor.clear();
        editor.apply();
    }

    public void setCities(Cities cities) {
        Gson gson = new Gson();
        String json = gson.toJson(cities);
        editor.putString(CITIES, json);
        editor.apply();
    }

    public Cities getCities() {
        Gson gson = new Gson();
        String json = pref.getString(CITIES, null);
        return gson.fromJson(json, Cities.class);
    }
}
