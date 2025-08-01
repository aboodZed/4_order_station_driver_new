package com.AbdUlla.a4_order_station_driver.services.firebase;

import android.util.Log;

import com.AbdUlla.a4_order_station_driver.feature.main.home.HomeFragment;
import com.AbdUlla.a4_order_station_driver.utils.location.tracking.OrderGPSTracking;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.AbdUlla.a4_order_station_driver.feature.main.MainActivity2;
import com.AbdUlla.a4_order_station_driver.feature.order.orderStation.chat.ChatFragment;
import com.AbdUlla.a4_order_station_driver.feature.order.publicOrder.publicOrderView.PublicOrderViewFragment;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.util.NavigateUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.NotificationUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("remoteMessage", "remote" + remoteMessage.getData());
        remote(remoteMessage);
    }

    private void remote(RemoteMessage remoteMessage) {
        try {
            Map<String, String> map = remoteMessage.getData();
            JSONObject mapJSON = new JSONObject(map);
            String message = mapJSON.getString(AppContent.FIREBASE_MESSAGE);
            JSONObject body = new JSONObject(message).getJSONObject(AppContent.FIREBASE_DATA);
            //data
            String msg = body.getString(AppContent.FIREBASE_MSG);
            String type = body.getString(AppContent.FIREBASE_TYPE);
            if (type.equals(AppContent.ADMIN)){
                new NotificationUtil().sendNotification(this, msg, message);
            }
//            if (status.equals(AppContent.NEW_MESSAGE) && PublicOrderViewFragment.isOpenPublicChat) {
//            } else if (status.equals(AppContent.NEW_MESSAGE) && ChatFragment.isOpenChat) {
//            } else
            String status = body.getString(AppContent.FIREBASE_STATUS);
            if (status != null) {
                if (status.equals(AppContent.PAYMENT_CONFIRM)) {
                    //send notification
                    new NotificationUtil().sendNotification(this, msg, message);
                    AppController.getInstance().getAppSettingsPreferences().setIsPayTheBill(status);

                } else if ((status.equals(AppContent.CONFIRM_DELIVIERY))) {
                    AppController.getInstance().getAppSettingsPreferences().setTrackingPublicOrder(null);
                    OrderGPSTracking.newInstance(this).removeUpdates();
                } else if (status.equals(AppContent.NEW_ORDER) && !MainActivity2.isLoadingNewOrder) {
                    new NotificationUtil().sendNotification(this, msg, message);
                    new NavigateUtil().openNotification(this, message);
                } else {
                    new NotificationUtil().sendNotification(this, msg, message);
                }
            } else {
                new NotificationUtil().sendNotification(this, msg, message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(getClass().getName() + " error", "" + e.getMessage());
        }
    }
}