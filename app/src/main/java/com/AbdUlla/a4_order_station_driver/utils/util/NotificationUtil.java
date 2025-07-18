package com.AbdUlla.a4_order_station_driver.utils.util;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.feature.main.MainActivity2;
import com.AbdUlla.a4_order_station_driver.feature.main.home.HomeFragment;
import com.AbdUlla.a4_order_station_driver.models.Message;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;

import java.util.HashMap;

public class NotificationUtil {

    public static void sendMessageNotification(Activity activity, String invoice_no
            , String order_id, String user_id, String type) {
        HashMap<String, String> map = new HashMap<>();
        map.put("customer_id", user_id);
        map.put("order_id", order_id);
        map.put("invoice_no", invoice_no);
        map.put("type", type);
        Log.e(NotificationUtil.class.getName(), "map" + map.toString());

        new APIUtil<Message>(activity).getData(AppController.getInstance().getApi()
                .sendMessageNotification(map), new RequestListener<Message>() {
            @Override
            public void onSuccess(Message message, String msg) {
                Log.e(getClass().getName(), "response" + message);
            }

            @Override
            public void onError(String msg) {
                Log.e(getClass().getName(), "failure" + msg);
            }

            @Override
            public void onFail(String msg) {
                Log.e(getClass().getName(), "failure" + msg);
            }
        });
    }


    public void sendNotification(Context context ,String title, String message) {
        Notification.Builder builder = new Notification.Builder(context);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setContentTitle(title)
                //.setStyle(new Notification.BigTextStyle().bigText(title))
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher);
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                String channelID = "orderChannel"; // In case you don't add a channel android oreo will not create the notification and it will give you a log error
                NotificationChannel channel = new NotificationChannel(channelID, title,
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setLightColor(Color.GREEN);
                notificationManager.createNotificationChannel(channel);
                builder.setChannelId(channelID);
            } catch (Exception e) {
            }
        }
        Notification notification = builder.build();
        //notification.defaults = Notification.FLAG_ONLY_ALERT_ONCE;
        notification.defaults = Notification.FLAG_INSISTENT | Notification.FLAG_AUTO_CANCEL;
        //notification.defaults = Notification.FLAG_AUTO_CANCEL;
        notification.sound = alarmSound;

        int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);

        Intent intent;
        intent = new Intent(context, MainActivity2.class);
        intent.putExtra(AppContent.PAGE, HomeFragment.page);
        intent.putExtra(AppContent.FIREBASE_MESSAGE, message);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        notification.contentIntent = PendingIntent.getActivity(context
                , iUniqueId, intent, PendingIntent.FLAG_ONE_SHOT);
        notificationManager.notify(iUniqueId, notification);
    }
}