package com.webapp.a4_order_station_driver.feature.order.publicOrder.newPublicOrder;

import com.webapp.a4_order_station_driver.R;
import com.webapp.a4_order_station_driver.feature.main.orders.OrdersFragment;
import com.webapp.a4_order_station_driver.models.Message;
import com.webapp.a4_order_station_driver.models.PublicOrder;
import com.webapp.a4_order_station_driver.utils.util.APIUtil;
import com.webapp.a4_order_station_driver.utils.AppContent;
import com.webapp.a4_order_station_driver.utils.AppController;
import com.webapp.a4_order_station_driver.utils.util.ToolUtil;
import com.webapp.a4_order_station_driver.utils.language.BaseActivity;
import com.webapp.a4_order_station_driver.utils.listeners.DialogView;
import com.webapp.a4_order_station_driver.utils.listeners.RequestListener;
import com.webapp.a4_order_station_driver.utils.location.tracking.OrderGPSTracking;

class NewPublicOrderPresenter {

    private BaseActivity baseActivity;
    private DialogView<Message> dialogView;

    public NewPublicOrderPresenter(BaseActivity baseActivity, DialogView<Message> dialogView) {
        this.baseActivity = baseActivity;
        this.dialogView = dialogView;
    }

    public void accept(PublicOrder publicOrder) {
        dialogView.showDialog("");
        new APIUtil<Message>(baseActivity).getData(AppController.getInstance()
                .getApi().pickupPublicOrder(publicOrder.getId()), new RequestListener<Message>() {
            @Override
            public void onSuccess(Message message, String msg) {
                dialogView.hideDialog();
                AppController.getInstance().getAppSettingsPreferences()
                        .setTrackingPublicOrder(publicOrder);
                OrderGPSTracking.newInstance(baseActivity).startGPSTracking();

                //OrdersFragment.viewPagerPage = OrderPublicFragment.viewPagerPage;
                //WalletFragment.viewPagerPage = PublicWalletFragment.viewPagerPage;

                //ToolUtil.showLongToast(baseActivity.getString(R.string.closeApp), baseActivity);
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
