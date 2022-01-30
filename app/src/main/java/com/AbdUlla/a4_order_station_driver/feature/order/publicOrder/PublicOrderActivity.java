package com.AbdUlla.a4_order_station_driver.feature.order.publicOrder;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.ActivityOrderBinding;
import com.AbdUlla.a4_order_station_driver.feature.main.MainActivity2;
import com.AbdUlla.a4_order_station_driver.feature.main.orders.OrdersFragment;
import com.AbdUlla.a4_order_station_driver.feature.order.publicOrder.newPublicOrder.NewPublicOrderFragment;
import com.AbdUlla.a4_order_station_driver.feature.order.publicOrder.publicOrderView.PublicOrderViewFragment;
import com.AbdUlla.a4_order_station_driver.models.PublicOrder;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.util.NavigateUtil;

public class PublicOrderActivity extends BaseActivity {

    public final static int page = 700;

    private ActivityOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        super.setRootView(binding.getRoot());
        super.onCreate(savedInstanceState);

        click();
        setData();
    }

    private void click() {
        binding.ivBack.setOnClickListener(view -> onBackPressed());
    }

    private void setData() {
        PublicOrder publicOrder = AppController.getInstance().getAppSettingsPreferences()
                .getTrackingPublicOrder();
        if (publicOrder.getStatus().equals(AppContent.PENDING_STATUS)) {
            navigate(NewPublicOrderFragment.page);
        } else {
            navigate(PublicOrderViewFragment.page);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void navigate(int page) {
        binding.tvPageTitle.setText(getString(R.string.order) + AppController.getInstance().getAppSettingsPreferences()
                .getTrackingPublicOrder().getInvoice_number());

        switch (page) {
            case NewPublicOrderFragment.page://10 new public order
                NewPublicOrderFragment newPublicOrderFragment = NewPublicOrderFragment
                        .newInstance(this);

                new NavigateUtil().replaceFragment(getSupportFragmentManager()
                        , newPublicOrderFragment, R.id.fragment_container);
                break;
            case PublicOrderViewFragment.page: //public order view and chat

                PublicOrderViewFragment publicOrderViewFragment = PublicOrderViewFragment.newInstance(this);

                new NavigateUtil().replaceFragment(getSupportFragmentManager()
                        , publicOrderViewFragment, R.id.fragment_container);
                break;
            case OrdersFragment.page:
                new NavigateUtil().activityIntentWithPage(this, MainActivity2.class, false, page);
                break;
        }
    }
}