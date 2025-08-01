package com.AbdUlla.a4_order_station_driver.utils.location.tracking;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.models.MyLocation;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;

import java.util.HashMap;

public class GPSTracking {

    private Context context;
    private LocationManager lm;
    private LocationListener locationListener;
    private static GPSTracking myGpsTracking;

    public GPSTracking(Context context) {
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.context = context;
    }

    public static GPSTracking getInstance(Context context) {
        if (myGpsTracking == null) {
            myGpsTracking = new GPSTracking(context);
        }
        return myGpsTracking;
    }

    public void startMyGPSTracking() {
        MyLocation myLocation = new MyLocation();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference(AppContent.FIREBASE_PUBLIC_TRACKING_INSTANCE)
                .child(AppController.getInstance().getAppSettingsPreferences().getUser().getId() + "");

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, R.string.unable_location, Toast.LENGTH_SHORT).show();
            return;
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                HashMap<String, String> map = new HashMap<>();
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    myLocation.setLat(location.getLatitude());
                    map.put("lat", "" + location.getLatitude());
                    myLocation.setLng(location.getLongitude());
                    map.put("lng", "" + location.getLongitude());

                    myLocation.setName(AppController.getInstance().getAppSettingsPreferences().getUser().getName());
                    myLocation.setMobile(AppController.getInstance().getAppSettingsPreferences().getUser().getMobile());
                    myLocation.setDriver_id(AppController.getInstance().getAppSettingsPreferences().getUser().getId());
                    myLocation.setCountry_id(AppController.getInstance().getAppSettingsPreferences().getUser().getCountry().getId());
                    myLocation.setCity_id(AppController.getInstance().getAppSettingsPreferences().getUser().getCity().getId());
                    if (AppController.getInstance().getAppSettingsPreferences().getUser().isOnline()) {
                        if (AppController.getInstance().getAppSettingsPreferences().getTrackingOrderStation() == null
                                && AppController.getInstance().getAppSettingsPreferences().getTrackingPublicOrder() == null) {
                            myLocation.setStatus("online");
                        } else {
                            myLocation.setStatus("busy");
                        }
                    } else {
                        myLocation.setStatus("offline");
                    }
                    db.setValue(myLocation);
                    map.put("driver_id", AppController.getInstance().getAppSettingsPreferences().getUser().getId() + "");

//                    new APIUtil<Message>(context).getData(AppController.getInstance().getApi()
//                            .updateLocation(map), new RequestListener<Message>() {
//                        @Override
//                        public void onSuccess(Message message, String msg) {
//                            Log.e(getClass().getName() + " : MyTracking", myLocation.toString());
//                        }
//
//                        @Override
//                        public void onError(String msg) {
//                            Log.e(getClass().getName() + " : trackingError", msg);
//                        }
//
//                        @Override
//                        public void onFail(String msg) {
//                            Log.e(getClass().getName() + " : trackingFail", msg);
//                        }
//                    });
                } else {
                    lm.removeUpdates(locationListener);
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
    }

    public void removeMyUpdates() {
        if (this.locationListener != null)
            this.lm.removeUpdates(this.locationListener);
        myGpsTracking = null;
    }
}