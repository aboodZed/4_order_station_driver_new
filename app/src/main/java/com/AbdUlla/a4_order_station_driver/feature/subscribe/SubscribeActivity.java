package com.AbdUlla.a4_order_station_driver.feature.subscribe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.ActivitySubscribeBinding;
import com.AbdUlla.a4_order_station_driver.feature.login.LoginActivity;
import com.AbdUlla.a4_order_station_driver.models.AppSettings;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.util.NavigateUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;

public class SubscribeActivity extends BaseActivity {

    public static final int page = 800;

    private ActivitySubscribeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySubscribeBinding.inflate(getLayoutInflater());
        super.setRootView(binding.getRoot());
        super.onCreate(savedInstanceState);
        setData();
        click();
    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        AppSettings settings = AppController.getInstance().getAppSettingsPreferences().getSettings();
        binding.tvNumberOrder.setText(settings.getData().getNumber_of_orders() + " " + getString(R.string.orders));
        binding.tvPriceOrder.setText(settings.getData().getPrice_of_orders() + " " + settings.getData().getCurrency_code());
    }

    private void click() {
        binding.ivTelegram.setOnClickListener(v -> ToolUtil.openTelegramContact(this,AppController
                .getInstance().getAppSettingsPreferences().getSettings().getData().getTelegram_link()));
        binding.ivWhatsapp.setOnClickListener(v -> ToolUtil.openWhatsAppContact(this, AppController
                .getInstance().getAppSettingsPreferences().getSettings().getData().getMobile()));
    }

    @Override
    public void onBackPressed() {
        navigate(0);
    }

    @Override
    public void navigate(int page) {
        new NavigateUtil().activityIntent(this,LoginActivity.class,false);
    }
}