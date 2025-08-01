package com.AbdUlla.a4_order_station_driver.feature.main.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.FragmentOrdersBinding;
import com.AbdUlla.a4_order_station_driver.feature.main.adapter.OrderStationAdapter;
import com.AbdUlla.a4_order_station_driver.feature.main.adapter.ProcessOrderAdapter;
import com.AbdUlla.a4_order_station_driver.feature.main.adapter.PublicOrderAdapter;
import com.AbdUlla.a4_order_station_driver.models.Orders;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.WaitDialogFragment;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;

public class OrdersFragment extends Fragment implements DialogView<Orders> {

    public static final int page = 203;

    private FragmentOrdersBinding binding;

    private OrderStationAdapter ordersAdapter;
    private PublicOrderAdapter publicOrderAdapter;
    private ProcessOrderAdapter processOrderAdapter;
    private static BaseActivity baseActivity;
    public static boolean isOrderStation = true;

    private OrdersPresenter presenter;

    public static OrdersFragment newInstance(BaseActivity baseActivity) {
        OrdersFragment.baseActivity = baseActivity;
        return new OrdersFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(getLayoutInflater());
        presenter = new OrdersPresenter(baseActivity, this);
        click();
        //setupViewPager();
        return binding.getRoot();
    }

    private void click() {
        binding.llOrderStation.setOnClickListener(v -> {
            isOrderStation = true;
            switchClick();
        });
        binding.llPublicOrder.setOnClickListener(v -> {
            isOrderStation = false;
            switchClick();
        });
    }

    private void switchClick() {
        if (isOrderStation) {
            binding.tvOrderStationText.setTextColor(baseActivity.getColor(R.color.red));
            binding.tvPublicOrderText.setTextColor(baseActivity.getColor(R.color.light_blue));
            binding.vOrderStationLine.setVisibility(View.VISIBLE);
            binding.vPublicOrderLine.setVisibility(View.INVISIBLE);
            binding.rvOrdersOthers.setVisibility(View.VISIBLE);
            binding.rvPublicOrders.setVisibility(View.GONE);
        } else {
            binding.tvOrderStationText.setTextColor(baseActivity.getColor(R.color.light_blue));
            binding.tvPublicOrderText.setTextColor(baseActivity.getColor(R.color.red));
            binding.vOrderStationLine.setVisibility(View.INVISIBLE);
            binding.vPublicOrderLine.setVisibility(View.VISIBLE);
            binding.rvOrdersOthers.setVisibility(View.GONE);
            binding.rvPublicOrders.setVisibility(View.VISIBLE);
        }
    }

    /*
        private void setupViewPager() {

            SectionPageAdapter sectionPageAdapter = new SectionPageAdapter(getChildFragmentManager());
            sectionPageAdapter.addFragment(OrderStationFragment.newInstance(baseActivity), getString(R.string.order_station));
            sectionPageAdapter.addFragment(OrderPublicFragment.newInstance(baseActivity), getString(R.string.public_order));
            binding.vpTabs.setAdapter(sectionPageAdapter);
            binding.tlTabs.setupWithViewPager(binding.vpTabs);
            binding.vpTabs.setCurrentItem(viewPagerPage);

        }
    */
    private void initRecycleView() {
        ordersAdapter = new OrderStationAdapter(baseActivity);
        publicOrderAdapter = new PublicOrderAdapter(baseActivity);
        processOrderAdapter = new ProcessOrderAdapter(baseActivity);

        binding.rvOrdersOthers.setLayoutManager(new LinearLayoutManager(baseActivity));
        binding.rvPublicOrders.setLayoutManager(new LinearLayoutManager(baseActivity));
        binding.rvOrders.setLayoutManager(new LinearLayoutManager(baseActivity));

        binding.rvOrdersOthers.setItemAnimator(new DefaultItemAnimator());
        binding.rvPublicOrders.setItemAnimator(new DefaultItemAnimator());
        binding.rvOrders.setItemAnimator(new DefaultItemAnimator());

        binding.rvOrdersOthers.setAdapter(ordersAdapter);
        binding.rvPublicOrders.setAdapter(publicOrderAdapter);
        binding.rvOrders.setAdapter(processOrderAdapter);

        processOrderAdapter.setProcessListener(() -> WaitDialogFragment.newInstance()
                .show(getParentFragmentManager(), ""));
        switchClick();
    }

    @Override
    public void setData(Orders orders) {
        initRecycleView();
        processOrderAdapter.addAll(orders.getIn_progress_order());
        ordersAdapter.addAll(orders.getDelivered_orders());
        publicOrderAdapter.addAll(orders.getDelivered_public_orders());
        if (!orders.getIn_progress_order().isEmpty()) {
            binding.tvInProgress.setVisibility(View.VISIBLE);
            binding.tvOther.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showDialog(String s) {
        binding.avi.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDialog() {
        binding.avi.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ordersAdapter != null)
            ordersAdapter.clear();
        if (publicOrderAdapter != null)
            publicOrderAdapter.clear();
        if (processOrderAdapter != null)
            processOrderAdapter.clear();
        presenter.getData();
    }
}
