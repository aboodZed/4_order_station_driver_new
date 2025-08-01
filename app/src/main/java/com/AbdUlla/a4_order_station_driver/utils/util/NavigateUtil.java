package com.AbdUlla.a4_order_station_driver.utils.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.model.LatLng;
import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.feature.main.MainActivity2;
import com.AbdUlla.a4_order_station_driver.feature.main.home.HomeFragment;
import com.AbdUlla.a4_order_station_driver.feature.order.orderStation.OrderActivity;
import com.AbdUlla.a4_order_station_driver.feature.order.publicOrder.PublicOrderActivity;
import com.AbdUlla.a4_order_station_driver.models.OrderStation;
import com.AbdUlla.a4_order_station_driver.models.PublicOrder;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;

public class NavigateUtil {

    public void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int layout) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layout, fragment);
        fragmentTransaction.commit();
    }

    public void activityIntent(Context from, Class to, boolean back) {
        Intent intent = new Intent(from, to);
        if (!back) intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        from.startActivity(intent);
    }

    public void activityIntentWithPage(Context from, Class to, boolean back, int pageNum) {
        Intent intent = new Intent(from, to);
        intent.putExtra(AppContent.PAGE, pageNum);
        if (!back) intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        from.startActivity(intent);
    }

    public void openNotification(Context from, String message) {
        Intent intent = new Intent(from, MainActivity2.class);
        intent.putExtra(AppContent.PAGE, HomeFragment.page);
        intent.putExtra(AppContent.FIREBASE_MESSAGE, message);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        from.startActivity(intent);
    }

    public void openOrderStation(Context from, OrderStation orderStation, int page, boolean back) {
        //save order station
        AppController.getInstance().getAppSettingsPreferences()
                .setTrackingOrderStation(orderStation);
        //move to order activity
        Intent intent = new Intent(from, OrderActivity.class);
        intent.putExtra(AppContent.PAGE, page);
        if (!back) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        from.startActivity(intent);
    }

    public void openPublicOrder(Context from, PublicOrder publicOrder, int page, boolean back) {
        //save public order
        AppController.getInstance().getAppSettingsPreferences()
                .setTrackingPublicOrder(publicOrder);
        //move to public order activity
        Intent intent = new Intent(from, PublicOrderActivity.class);
        intent.putExtra(AppContent.PAGE, page);
        if (!back) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        from.startActivity(intent);
    }

    public void setLocation(Activity activity, LatLng location) {
        String format = "geo:0,0?q=" + location.latitude
                + "," + location.longitude + "( GPSLocation title)";
        Uri uri = Uri.parse(format);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public void openLink(Activity activity, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public void sendEmail(Activity activity, String email) {
        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
        activity.startActivity(Intent.createChooser(i, activity.getString(R.string.send_email)));
    }

    public void makeCall(Activity activity, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        activity.startActivity(intent);
    }
}