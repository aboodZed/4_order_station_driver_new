package com.AbdUlla.a4_order_station_driver.services.firebase;

import android.util.Log;

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
        /*
        2022-02-02 12:26:33.474 6598-17351/com.AbdUlla.a4_order_station_driver E/remoteMessage: remote{moredata=dd, message={"data":{"msg":"new order ##00000151","destination_address":"لاخاهلهللعلعبعبعبعببع","pickup_address":"test","title":"4orderstation","type":"4orderstation","order_id":152,"status":"neworder"},"sound":"mySound","icon":"myIcon","title":"4orderstation","body":"new order ##00000151","click_action":"com.webapp.a4_order_station_driver.feture.home.MainActivity"}}

         */
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
            String status = body.getString(AppContent.FIREBASE_STATUS);

//            if (status.equals(AppContent.NEW_MESSAGE) && PublicOrderViewFragment.isOpenPublicChat) {
//            } else if (status.equals(AppContent.NEW_MESSAGE) && ChatFragment.isOpenChat) {
//            } else
            if (status.equals(AppContent.PAYMENT_CONFIRM)) {
                //send notification
                new NotificationUtil().sendNotification(this, msg, message);
                AppController.getInstance().getAppSettingsPreferences().setIsPayTheBill(status);

            } else if ((status.equals(AppContent.CONFIRM_DELIVIERY))){
                AppController.getInstance().getAppSettingsPreferences().setTrackingPublicOrder(null);
                OrderGPSTracking.newInstance(this).removeUpdates();
            } else if (status.equals(AppContent.NEW_ORDER) && !MainActivity2.isLoadingNewOrder) {

                new NotificationUtil().sendNotification(this, msg, message);
                new NavigateUtil().openNotification(this, message);
            } else {
                new NotificationUtil().sendNotification(this, msg, message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(getClass().getName() + " error", "" + e.getMessage());
        }
    }
}