package com.AbdUlla.a4_order_station_driver.feature.main;

import com.AbdUlla.a4_order_station_driver.feature.login.LoginActivity;
import com.AbdUlla.a4_order_station_driver.feature.order.orderStation.chat.ChatFragment;
import com.AbdUlla.a4_order_station_driver.feature.order.publicOrder.publicOrderView.PublicOrderViewFragment;
import com.AbdUlla.a4_order_station_driver.models.Message;
import com.AbdUlla.a4_order_station_driver.models.OrderStation;
import com.AbdUlla.a4_order_station_driver.models.PublicOrder;
import com.AbdUlla.a4_order_station_driver.models.Result;
import com.AbdUlla.a4_order_station_driver.models.User;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.SignOutDialog;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.WaitDialogFragment;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.util.NavigateUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;
import com.AbdUlla.a4_order_station_driver.utils.location.tracking.GPSTracking;

class MainPresenter {

    private BaseActivity baseActivity;
    private DialogView<Boolean> dialogView;

    public MainPresenter(BaseActivity baseActivity, DialogView<Boolean> dialogView) {
        this.baseActivity = baseActivity;
        this.dialogView = dialogView;
    }

    public void getPublicOrder(int id, String type) {
        dialogView.showDialog("");
        new APIUtil<Result<PublicOrder>>(baseActivity).getData(AppController.getInstance()
                .getApi().getPublicOrder(id), new RequestListener<Result<PublicOrder>>() {
            @Override
            public void onSuccess(Result<PublicOrder> result, String msg) {
                WaitDialogFragment.newInstance().dismiss();
                result.getData().setType(type);
                new NavigateUtil().openPublicOrder(baseActivity, result.getData()
                        , PublicOrderViewFragment.page, true);
            }

            @Override
            public void onError(String msg) {
                dialogView.hideDialog();
                ToolUtil.showLongToast(msg, baseActivity);
            }

            @Override
            public void onFail(String msg) {
                dialogView.hideDialog();
                ToolUtil.showLongToast(msg, baseActivity);
            }
        });
    }

    public void updateState(boolean status) {
        new APIUtil<Message>(baseActivity).getData(AppController.getInstance()
                .getApi().isOnline(status), new RequestListener<Message>() {
            @Override
            public void onSuccess(Message message, String msg) {
                //update saved user
                User user = AppController.getInstance().getAppSettingsPreferences().getUser();
                user.setOnline(status);
                AppController.getInstance().getAppSettingsPreferences().setUser(user);
                //set toggle
                ToolUtil.showLongToast(message.getMessage(), baseActivity);
                dialogView.setData(true);
            }

            @Override
            public void onError(String msg) {
                ToolUtil.showLongToast(msg, baseActivity);
                dialogView.setData(false);
            }

            @Override
            public void onFail(String msg) {
                ToolUtil.showLongToast(msg, baseActivity);
                dialogView.setData(false);
            }
        });
    }

    public void logout(SignOutDialog signOutDialog) {
        dialogView.showDialog("");
        new APIUtil<Message>(baseActivity).getData(AppController.getInstance()
                .getApi().isOnline(false), new RequestListener<Message>() {
            @Override
            public void onSuccess(Message message, String msg) {
                AppController.getInstance().getAppSettingsPreferences().setToken("");
                GPSTracking.getInstance(baseActivity).removeMyUpdates();
                new NavigateUtil().activityIntent(baseActivity, LoginActivity.class, false);
                dialogView.hideDialog();
                signOutDialog.dismiss();
            }

            @Override
            public void onError(String msg) {
                ToolUtil.showLongToast(msg, baseActivity);
                dialogView.hideDialog();
                signOutDialog.dismiss();
            }

            @Override
            public void onFail(String msg) {
                ToolUtil.showLongToast(msg, baseActivity);
                dialogView.hideDialog();
                signOutDialog.dismiss();
            }
        });
    }

    public void getOrderStation(int id, String type) {
        dialogView.showDialog("");
        new APIUtil<Result<OrderStation>>(baseActivity).getData(AppController.getInstance()
                .getApi().getOrderStation(id), new RequestListener<Result<OrderStation>>() {
            @Override
            public void onSuccess(Result<OrderStation> result, String msg) {
                WaitDialogFragment.newInstance().dismiss();
                result.getData().setType(type);
                new NavigateUtil().openOrderStation(baseActivity, result.getData()
                        , ChatFragment.page, true);
            }

            @Override
            public void onError(String msg) {
                dialogView.hideDialog();
                ToolUtil.showLongToast(msg, baseActivity);
            }

            @Override
            public void onFail(String msg) {
                dialogView.hideDialog();
                ToolUtil.showLongToast(msg, baseActivity);
            }
        });

    }
}
