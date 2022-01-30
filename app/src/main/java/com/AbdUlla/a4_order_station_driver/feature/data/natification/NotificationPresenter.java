package com.AbdUlla.a4_order_station_driver.feature.data.natification;

import android.app.Activity;

import com.AbdUlla.a4_order_station_driver.models.Notification;
import com.AbdUlla.a4_order_station_driver.models.Result;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;

import java.util.ArrayList;

class NotificationPresenter {

    private Activity activity;
    private DialogView<ArrayList<Notification>> dialogView;

    public NotificationPresenter(Activity activity, DialogView<ArrayList<Notification>> dialogView) {
        this.activity = activity;
        this.dialogView = dialogView;
        getData();
    }

    private void getData() {
        dialogView.showDialog("");
        new APIUtil<Result<ArrayList<Notification>>>(activity).getData(AppController.getInstance()
                .getApi().getNotifications(), new RequestListener<Result<ArrayList<Notification>>>() {
            @Override
            public void onSuccess(Result<ArrayList<Notification>> notifications, String msg) {
                dialogView.setData(notifications.getData());
                dialogView.hideDialog();
            }

            @Override
            public void onError(String msg) {
                ToolUtil.showLongToast(msg, activity);
                dialogView.hideDialog();
            }

            @Override
            public void onFail(String msg) {
                ToolUtil.showLongToast(msg, activity);
                dialogView.hideDialog();
            }
        });
    }
}
