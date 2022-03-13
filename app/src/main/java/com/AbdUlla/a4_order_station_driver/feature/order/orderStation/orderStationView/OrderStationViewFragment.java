package com.AbdUlla.a4_order_station_driver.feature.order.orderStation.orderStationView;

import static com.AbdUlla.a4_order_station_driver.utils.AppContent.PHONE_CALL_CODE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.annotations.NotNull;
import com.AbdUlla.a4_order_station_driver.databinding.FragmentOrderViewBinding;
import com.AbdUlla.a4_order_station_driver.feature.main.adapter.OrderItemsAdapter;
import com.AbdUlla.a4_order_station_driver.feature.order.orderStation.chat.ChatFragment;
import com.AbdUlla.a4_order_station_driver.models.OrderStation;
import com.AbdUlla.a4_order_station_driver.models.OrderStationItem;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.util.APIImageUtil;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.util.NavigateUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.PermissionUtil;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.WaitDialogFragment;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;

import java.util.ArrayList;
import java.util.Map;

public class OrderStationViewFragment extends Fragment implements DialogView<OrderStation> {

    public final static int page = 503;

    private FragmentOrderViewBinding binding;

    private BaseActivity baseActivity;
    private OrderItemsAdapter orderItemsAdapter;
    private OrderStation orderStation;
    private OrderStationViewPresenter presenter;

    public OrderStationViewFragment(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public static OrderStationViewFragment newInstance(BaseActivity baseActivity) {
        return new OrderStationViewFragment(baseActivity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrderViewBinding.inflate(getLayoutInflater());
        presenter = new OrderStationViewPresenter(baseActivity, this);
        orderStation = AppController.getInstance().getAppSettingsPreferences().getTrackingOrderStation();
        setData(orderStation);
        click();
        return binding.getRoot();
    }

    private void click() {
        binding.ivReceiverChat.setOnClickListener(view -> baseActivity.navigate(ChatFragment.page));
        binding.btnDone.setOnClickListener(view -> presenter.finishOrder(orderStation));
        binding.ivCoCall.setOnClickListener(view -> coCall());
        binding.ivReceiveCall.setOnClickListener(view -> receiverCall());

        binding.ivCoLocation.setOnClickListener(view -> new NavigateUtil()
                .setLocation(requireActivity(), new LatLng(orderStation.getStore().getLat()
                        , orderStation.getStore().getLng())));

        binding.ivReceiveLocation.setOnClickListener(view -> new NavigateUtil()
                .setLocation(requireActivity(), new LatLng(Double.parseDouble(orderStation.getLat())
                        , Double.parseDouble(orderStation.getLng()))));
    }

    public void coCall() {
        if (PermissionUtil.isPermissionGranted(Manifest.permission.CALL_PHONE, getActivity())) {
            new NavigateUtil().makeCall(requireActivity(), orderStation.getStore().getMobile());
        } else {
            permission.launch(new String[]{Manifest.permission.CALL_PHONE});
            //PermissionUtil.requestPermission(getActivity(), Manifest.permission.CALL_PHONE, PHONE_CALL_CODE);
        }
    }

    public void receiverCall() {
        if (PermissionUtil.isPermissionGranted(Manifest.permission.CALL_PHONE, getActivity())) {
            new NavigateUtil().makeCall(requireActivity(), orderStation.getReceiver_phone());
        } else {
            permission.launch(new String[]{Manifest.permission.CALL_PHONE});
            //PermissionUtil.requestPermission(getActivity(), Manifest.permission.CALL_PHONE, PHONE_CALL_CODE);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setData(OrderStation orderStation) {
        String currency = AppController.getInstance().getAppSettingsPreferences().getUser()
                .getCountry().getCurrency_code();
        //this.orderStation = orderStation;
        APIImageUtil.loadImage(getContext(), binding.pbWaitReceiverImage, AppContent.IMAGE_STORAGE_URL
                + this.orderStation.getCustomer().getAvatar(), binding.ivReceiverImage);

        APIImageUtil.loadImage(getContext(), binding.pbWaitCoImage, AppContent.IMAGE_STORAGE_URL
                + this.orderStation.getStore().getLogo_url(), binding.ivCoImage);


        binding.tvReceiverName.setText(this.orderStation.getReceiver_name());
        binding.tvCoName.setText(this.orderStation.getStore().getName());
        binding.tvOrderCoName.setText(this.orderStation.getStore().getName());
        binding.tvOrderCoAddress.setText(this.orderStation.getStore().getAddress());

        binding.tvDelivery.setText(orderStation.getDelivery());
        if (this.orderStation.getStatus().equals(AppContent.DELIVERED_STATUS)) {
            binding.btnDone.setClickable(false);
            binding.btnDone.setVisibility(View.GONE);
            binding.ivCoCall.setVisibility(View.GONE);
            binding.ivCoLocation.setVisibility(View.GONE);
            binding.ivReceiveCall.setVisibility(View.GONE);
            binding.ivReceiveLocation.setVisibility(View.GONE);
            binding.ivReceiverChat.setVisibility(View.GONE);
            AppController.getInstance().getAppSettingsPreferences().setTrackingOrderStation(null);
        }
        //binding.tvOrderId.setText((getString(R.string.order) + "#" + this.orderStation.getInvoice_number()));
        binding.tvTime.setText(this.orderStation.getOrder_date());
        if (!TextUtils.isEmpty(this.orderStation.getSub_total_1()))
            binding.tvDelivery.setText(this.orderStation.getSub_total_1() + " " + currency);
        if (!TextUtils.isEmpty(this.orderStation.getDiscount()))
            if (Integer.parseInt(this.orderStation.getDiscount()) <= 0) {
                binding.tvDeliveryFees.setText(this.orderStation.getDiscount() + " " + currency);
            } else {
                binding.tvDeliveryFees.setText("-" + this.orderStation.getDiscount() + " " + currency);
            }
        if (!TextUtils.isEmpty(this.orderStation.getSub_total_2()))
            binding.tvSubTotalAfter.setText(this.orderStation.getSub_total_2() + " " + currency);
        if (!TextUtils.isEmpty(this.orderStation.getTax()))
            binding.tvVat.setText(this.orderStation.getTax() + " " + currency);
        if (!TextUtils.isEmpty(this.orderStation.getDelivery()))
            binding.tvDeliveryFees.setText(this.orderStation.getDelivery() + " " + currency);
        if (!TextUtils.isEmpty(this.orderStation.getTotal()))
            binding.tvTotal.setText(this.orderStation.getTotal() + " " + currency);
        if (!TextUtils.isEmpty(this.orderStation.getType_of_receive()))
            binding.tvReceive.setText(this.orderStation.getType_of_receive());
//shop info
        APIImageUtil.loadImage(getContext(), binding.pbWaitCoImage, this.orderStation.getStore().getLogo_url(), binding.ivCoImage);
        //binding.tvOrderCoAddress.setText(this.orderStation.getStore().getAddress_en());

//user info
        /*APIImageUtil.loadImage(getContext(), binding.pbWaitReciverImage
                , this.orderStation.getUser().getAvatar_url(), binding.ivReceiverImage);
        binding.tvReceiverName.setText(this.orderStation.getUser().getName());*/
        //items
        initRecycleView(orderStation.getOrderItems());
    }

    @Override
    public void showDialog(String s) {
        WaitDialogFragment.newInstance().show(getChildFragmentManager(), "");
    }

    @Override
    public void hideDialog() {
        WaitDialogFragment.newInstance().dismiss();
    }

    public void initRecycleView(ArrayList<OrderStationItem> orderItems) {
        orderItemsAdapter = new OrderItemsAdapter(orderItems);
        binding.rvOrderItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvOrderItem.setItemAnimator(new DefaultItemAnimator());
        binding.rvOrderItem.setAdapter(orderItemsAdapter);
    }

    ActivityResultLauncher<String[]> permission = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            new ActivityResultCallback<Map<String, Boolean>>() {
                @Override
                public void onActivityResult(Map<String, Boolean> result) {
                    if (Boolean.TRUE.equals(result.get(Manifest.permission.CALL_PHONE))) {
                        new NavigateUtil().makeCall(requireActivity(), orderStation.getReceiver_phone());
                    }
                }
            });
}
