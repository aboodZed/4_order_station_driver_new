package com.AbdUlla.a4_order_station_driver.feature.data.rate;

import android.app.Activity;

import com.AbdUlla.a4_order_station_driver.models.Rating;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;

class RatingPresenter {

    private Activity activity;
    private DialogView<Rating> dialogView;

    public RatingPresenter(Activity activity, DialogView<Rating> dialogView) {
        this.activity = activity;
        this.dialogView = dialogView;
    }

    public void getRatings() {
        dialogView.showDialog("");
        new APIUtil<Rating>(activity).getData(AppController.getInstance()
                .getApi().getRating(), new RequestListener<Rating>() {
            @Override
            public void onSuccess(Rating rating, String msg) {
                dialogView.hideDialog();
                dialogView.setData(rating);
            }

            @Override
            public void onError(String msg) {
                dialogView.hideDialog();
                ToolUtil.showLongToast(msg, activity);
            }

            @Override
            public void onFail(String msg) {
                dialogView.hideDialog();
                ToolUtil.showLongToast(msg, activity);
            }
        });
    }
}
