package com.AbdUlla.a4_order_station_driver.feature.order.orderStation.orderStationView;

import com.AbdUlla.a4_order_station_driver.feature.main.orders.OrdersFragment;
import com.AbdUlla.a4_order_station_driver.models.Message;
import com.AbdUlla.a4_order_station_driver.models.Order;
import com.AbdUlla.a4_order_station_driver.models.OrderStation;
import com.AbdUlla.a4_order_station_driver.models.Result;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;
import com.AbdUlla.a4_order_station_driver.utils.location.tracking.OrderGPSTracking;

public class OrderStationViewPresenter {

    private BaseActivity baseActivity;
    private DialogView<OrderStation> dialogView;

    public OrderStationViewPresenter(BaseActivity baseActivity, DialogView<OrderStation> dialogView) {
        this.baseActivity = baseActivity;
        this.dialogView = dialogView;
    }

    public void getOrderData(Order order) {
        dialogView.showDialog("");
        new APIUtil<Result<OrderStation>>(baseActivity).getData(AppController.getInstance()
                .getApi().getOrderStation(order.getId()), new RequestListener<Result<OrderStation>>() {
            @Override
            public void onSuccess(Result<OrderStation> result, String msg) {
                dialogView.setData(result.getData());
                dialogView.hideDialog();
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

    public void finishOrder(OrderStation orderStation) {
        dialogView.showDialog("");
        new APIUtil<Message>(baseActivity).getData(AppController.getInstance()
                .getApi().deliveryOrder(orderStation.getId()), new RequestListener<Message>() {
            @Override
            public void onSuccess(Message message, String msg) {
                OrderGPSTracking.newInstance(baseActivity).removeUpdates();
                dialogView.hideDialog();
                AppController.getInstance().getAppSettingsPreferences().setTrackingOrderStation(null);
                AppController.getInstance().getAppSettingsPreferences().setTrackingPublicOrder(null);
                baseActivity.navigate(OrdersFragment.page);
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
