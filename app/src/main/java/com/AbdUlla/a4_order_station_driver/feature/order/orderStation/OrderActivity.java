package com.AbdUlla.a4_order_station_driver.feature.order.orderStation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.ActivityOrderBinding;
import com.AbdUlla.a4_order_station_driver.feature.main.MainActivity2;
import com.AbdUlla.a4_order_station_driver.feature.main.orders.OrdersFragment;
import com.AbdUlla.a4_order_station_driver.feature.order.orderStation.chat.ChatFragment;
import com.AbdUlla.a4_order_station_driver.feature.order.orderStation.newOrderStation.NewOrderStationFragment;
import com.AbdUlla.a4_order_station_driver.feature.order.orderStation.orderStationView.OrderStationViewFragment;
import com.AbdUlla.a4_order_station_driver.models.OrderStation;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.util.NavigateUtil;

import java.util.Objects;

public class OrderActivity extends BaseActivity {

    public final static int page = 500;

    private ActivityOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        super.setRootView(binding.getRoot());
        super.onCreate(savedInstanceState);

        setData();
        click();
    }

    private void click() {
        binding.ivBack.setOnClickListener(view -> {
           onBackPressed();
        });
    }

    private void setData() {
        navigate(Objects.requireNonNull(getIntent().getExtras()).getInt(AppContent.PAGE));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void navigate(int page) {
        try {
            Log.e(getClass().getName() + "4OrderStation", AppController.getInstance()
                    .getAppSettingsPreferences().getTrackingOrderStation().toString());

            binding.tvPageTitle.setText(getString(R.string.order) + AppController.getInstance().getAppSettingsPreferences()
                    .getTrackingOrderStation().getInvoice_number());
            switch (page) {
                case NewOrderStationFragment.page://6 new order station
                    NewOrderStationFragment newOrderFragment = NewOrderStationFragment
                            .newInstance(this);

                    new NavigateUtil().replaceFragment(getSupportFragmentManager()
                            , newOrderFragment, R.id.fragment_container);
                    break;

                case OrderStationViewFragment.page://7 order station view
                    OrderStationViewFragment orderViewFragment = OrderStationViewFragment.newInstance(this);

                    new NavigateUtil().replaceFragment(getSupportFragmentManager()
                            , orderViewFragment, R.id.fragment_container);
                    break;

                case ChatFragment.page: //order station chat
                    ChatFragment chatFragment = ChatFragment.newInstance(this);

                    new NavigateUtil().replaceFragment(getSupportFragmentManager()
                            , chatFragment, R.id.fragment_container);
                    break;
                case OrdersFragment.page:
                    new NavigateUtil().activityIntentWithPage(this, MainActivity2.class, false, page);
                    break;
            }
        } catch (Exception e) {
            Log.e(getClass().getName() + "4OrderStationError", e.getLocalizedMessage());
            new NavigateUtil().activityIntentWithPage(this, MainActivity2.class, false, page);
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof ChatFragment) {
            navigate(OrderStationViewFragment.page);
        } else {
            super.onBackPressed();
        }
    }
}