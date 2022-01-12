package com.webapp.a4_order_station_driver.feature.main.orders;

import com.webapp.a4_order_station_driver.models.Orders;
import com.webapp.a4_order_station_driver.models.Result;
import com.webapp.a4_order_station_driver.utils.util.APIUtil;
import com.webapp.a4_order_station_driver.utils.AppController;
import com.webapp.a4_order_station_driver.utils.util.ToolUtil;
import com.webapp.a4_order_station_driver.utils.language.BaseActivity;
import com.webapp.a4_order_station_driver.utils.listeners.DialogView;
import com.webapp.a4_order_station_driver.utils.listeners.RequestListener;

public class OrdersPresenter {

   private BaseActivity baseActivity;
   private DialogView<Orders> dialogView;

    public OrdersPresenter(BaseActivity baseActivity, DialogView<Orders> dialogView) {
        this.baseActivity = baseActivity;
        this.dialogView = dialogView;
        getData();
    }

    private void getData() {
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
