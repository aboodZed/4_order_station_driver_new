package com.AbdUlla.a4_order_station_driver.feature.order.orderStation.newOrderStation;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.feature.main.orders.OrdersFragment;
import com.AbdUlla.a4_order_station_driver.models.Message;
import com.AbdUlla.a4_order_station_driver.models.OrderStation;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;
import com.AbdUlla.a4_order_station_driver.utils.location.tracking.OrderGPSTracking;

public class NewOrderStationPresenter {

    private BaseActivity baseActivity;
    private DialogView<Message> dialogView;

    public NewOrderStationPresenter(BaseActivity baseActivity, DialogView<Message> dialogView) {
        this.baseActivity = baseActivity;
        this.dialogView = dialogView;
    }

    public void accept(OrderStation orderStation) {
        dialogView.showDialog("");
        new APIUtil<Message>(baseActivity).getData(AppController.getInstance()
                .getApi().pickupOrder(orderStation.getId()), new RequestListener<Message>() {
            @Override
            public void onSuccess(Message message, String msg) {
                dialogView.hideDialog();
                if (message.isSuccess()) {
                    AppController.getInstance().getAppSettingsPreferences().setTrackingOrderStation(orderStation);
                    AppController.getInstance().getAppSettingsPreferences().setTrackingPublicOrder(null);
                    OrderGPSTracking.newInstance(baseActivity).startGPSTracking();

                    ToolUtil.showLongToast(baseActivity.getString(R.string.closeApp), baseActivity);
                    //OrdersFragment.viewPagerPage = OrderStationFragment.viewPagerPage;
                    //WalletFragment.viewPagerPage = OrderStationWalletFragment.viewPagerPage;
                    baseActivity.navigate(OrdersFragment.page);
                } else {
                    StringBuilder errors = new StringBuilder();
                    for (String s : message.getErrors()) {
                        errors.append(s).append("\n");
                    }
                    ToolUtil.showLongToast(errors.toString(), baseActivity);
                }
            }

            @Override
            public void onError(String msg) {
                ToolUtil.showLongToast(msg, baseActivity);
                dialogView.hideDialog();
            }

            @Override
            public void onFail(String msg) {
                ToolUtil.showLongToast(msg, baseActivity);
                dialogView.hideDialog();
            }
        });
    }
}
