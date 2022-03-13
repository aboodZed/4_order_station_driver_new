package com.AbdUlla.a4_order_station_driver.feature.main.orders;

import com.AbdUlla.a4_order_station_driver.models.Orders;
import com.AbdUlla.a4_order_station_driver.models.Result;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;

public class OrdersPresenter {

   private BaseActivity baseActivity;
   private DialogView<Orders> dialogView;

    public OrdersPresenter(BaseActivity baseActivity, DialogView<Orders> dialogView) {
        this.baseActivity = baseActivity;
        this.dialogView = dialogView;
        getData();
    }

    public void getData() {
        dialogView.showDialog("");
        new APIUtil<Result<Orders>>(baseActivity).getData(AppController.getInstance()
                .getApi().getOrders(), new RequestListener<Result<Orders>>() {
            @Override
            public void onSuccess(Result<Orders> orders, String msg) {
                dialogView.hideDialog();
                dialogView.setData(orders.getData());
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
