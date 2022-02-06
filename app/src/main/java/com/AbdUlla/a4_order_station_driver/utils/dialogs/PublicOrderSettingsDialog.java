package com.AbdUlla.a4_order_station_driver.utils.dialogs;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.AbdUlla.a4_order_station_driver.utils.location.tracking.OrderGPSTracking;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.FragmentBillDailogBinding;
import com.AbdUlla.a4_order_station_driver.models.Message;
import com.AbdUlla.a4_order_station_driver.models.PublicOrder;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.NavigateUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;

import java.util.Objects;

public class PublicOrderSettingsDialog extends BottomSheetDialogFragment {

    private FragmentBillDailogBinding binding;

    private PublicOrder publicOrder = AppController.getInstance()
            .getAppSettingsPreferences().getTrackingPublicOrder();

    private Listener listener;

    public static PublicOrderSettingsDialog newInstance(PublicOrder order) {
        PublicOrderSettingsDialog fragment = new PublicOrderSettingsDialog();
        Bundle args = new Bundle();
        args.putSerializable(AppContent.ORDER_OBJECT, order);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState) {
        binding = FragmentBillDailogBinding.inflate(getLayoutInflater());
        data();
        click();
        return binding.getRoot();
    }

    private void data() {
        if (publicOrder.getStatus().equals(AppContent.TO_STORE_STATUS)) {
            binding.tvDelivery.setVisibility(View.GONE);
            if (!publicOrder.getClient_paid_invoice().equals(AppContent.ONE)) {
                binding.tvOnTheWayToTheCustomer.setVisibility(View.GONE);
            } else {
                binding.tvAddBill.setVisibility(View.GONE);
                if (AppController.getInstance().getAppSettingsPreferences().isPayTheBill()
                        || publicOrder.getClient_paid_invoice().equals(AppContent.ONE)) {
                    binding.tvCancelOrder.setVisibility(View.GONE);
                }
            }
        } else if (publicOrder.getStatus().equals(AppContent.TO_CLIENT_STATUS)) {
            binding.tvAddBill.setVisibility(View.GONE);
            binding.tvCancelOrder.setVisibility(View.GONE);
            binding.tvOnTheWayToTheCustomer.setVisibility(View.GONE);
        }
       /*if (getArguments() != null) {
             if (publicOrder.getStatus().equals(AppContent.TO_STORE_STATUS)) {
                binding.tvDelivery.setVisibility(View.INVISIBLE);
                binding.scOnWay.setEnabled(false);

                if (publicOrder.getClient_paid_invoice().equals("1")) {
                    binding.scOnWay.setEnabled(true);
                    binding.btnCancel.setVisibility(View.GONE);
                }

            } else if (publicOrder.getStatus().equals(AppContent.TO_CLIENT_STATUS)) {
                binding.scOnWay.setChecked(true);
                binding.scOnWay.setEnabled(false);

                if (publicOrder.getClient_paid_invoice().equals("1")) {
                    binding.btnCancel.setVisibility(View.GONE);
                    binding.tvDelivery.setVisibility(View.VISIBLE);
                }
            }
        }*/
    }

    private void click() {
        binding.clStoreLocation.setOnClickListener(v -> new NavigateUtil().setLocation(requireActivity()
                , new LatLng(publicOrder.getStore_lat(), publicOrder.getStore_lng())));

        binding.clCustomerLocation.setOnClickListener(v -> new NavigateUtil().setLocation(requireActivity()
                , new LatLng(publicOrder.getCustomer_address().getLat(), publicOrder.getCustomer_address().getLng())));

        binding.tvAddBill.setOnClickListener(view -> {
            dismiss();
            AddBillDialog addBillDialog = AddBillDialog.newInstance();
            addBillDialog.show(getParentFragmentManager(), "");
        });
        binding.tvDelivery.setOnClickListener(view -> delivery());
        binding.tvOnTheWayToTheCustomer.setOnClickListener(view -> onWay());
        binding.tvCancelOrder.setOnClickListener(view -> cancelOrder());
    }

    @Override
    public void onResume() {
        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog()).getWindow()
                .getAttributes();
        params.width = getResources().getDisplayMetrics().widthPixels;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getDialog().getWindow().setAttributes(params);
        setCancelable(false);
        super.onResume();
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                this.dismiss();
                return true;
            } else return false;
        });
    }


    public void onWay() {
        WaitDialogFragment.newInstance().show(getChildFragmentManager(), "");
        new APIUtil<Message>(getActivity()).getData(AppController.getInstance()
                        .getApi().changeToONTheWay(publicOrder.getId())
                , new RequestListener<Message>() {
                    @Override
                    public void onSuccess(Message message, String msg) {
                        if (message.isSuccess()) {
                            listener.updatePublicOrder(AppContent.TO_CLIENT_STATUS);
                        }
                        ToolUtil.showLongToast(message.getMessage(), getActivity());
                    }

                    @Override
                    public void onError(String msg) {
                        showError(msg);
                    }

                    @Override
                    public void onFail(String msg) {
                        showError(msg);
                    }
                });

    }

    public void delivery() {
           /*if (AppController.getInstance().getAppSettingsPreferences()
                   .getPayType().equals(AppContent.ON_DELIVERY_STATUS)) {
               CompletePayDialog.newInstance(publicOrder, listener)
                       .show(getChildFragmentManager(), "");
           } else {*/
        WaitDialogFragment.newInstance().show(getChildFragmentManager(), "");
        new APIUtil<Message>(getActivity()).getData(AppController.getInstance().getApi()
                .deliveredPublicOrder(publicOrder.getId()), new RequestListener<Message>() {
            @Override
            public void onSuccess(Message message, String msg) {
                //PublicOrderViewFragment.billPrice = 0;
                //OrdersFragment.viewPagerPage = OrderStationFragment.viewPagerPage;
                //WalletFragment.viewPagerPage = OrderStationWalletFragment.viewPagerPage;
                //ToolUtil.showLongToast(message.getMessage(), getActivity());
                listener.updatePublicOrder(AppContent.DELIVERED_STATUS);

                ToolUtil.showLongToast(message.getMessage(), requireActivity());
                AppController.getInstance().getAppSettingsPreferences().setIsPayTheBill("");
                AppController.getInstance().getAppSettingsPreferences().setTrackingOrderStation(null);
                AppController.getInstance().getAppSettingsPreferences().setTrackingPublicOrder(null);
            }

            @Override
            public void onError(String msg) {
                showError(msg);
            }

            @Override
            public void onFail(String msg) {
                showError(msg);
            }
        });

        //}
    }

    public void cancelOrder() {
        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.cancel))
                .setMessage(getString(R.string.cancel_massage))
                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    WaitDialogFragment.newInstance().show(getChildFragmentManager(), "");
                    new APIUtil<Message>(getActivity()).getData(AppController
                                    .getInstance().getApi().cancelOrder(publicOrder.getId())
                            , new RequestListener<Message>() {
                                @Override
                                public void onSuccess(Message message, String msg) {
                                    //PublicOrderViewFragment.billPrice = 0;
                                    //OrdersFragment.viewPagerPage = OrderStationFragment.viewPagerPage;
                                    //WalletFragment.viewPagerPage = OrderStationWalletFragment.viewPagerPage;
                                    //ToolUtil.showLongToast(message.getMessage(), getActivity());
                                    listener.updatePublicOrder(AppContent.CANCELLED_STATUS);
                                    OrderGPSTracking.newInstance(requireActivity()).removeUpdates();
                                    ToolUtil.showLongToast(message.getMessage(), requireActivity());
                                    AppController.getInstance().getAppSettingsPreferences().setIsPayTheBill("");
                                    AppController.getInstance().getAppSettingsPreferences().setTrackingOrderStation(null);
                                    AppController.getInstance().getAppSettingsPreferences().setTrackingPublicOrder(null);
                                }

                                @Override
                                public void onError(String msg) {
                                    showError(msg);
                                }

                                @Override
                                public void onFail(String msg) {
                                    showError(msg);
                                }
                            });

                })
                .setNegativeButton(R.string.no, null)
                .setIcon(R.drawable.ic_privacy)
                .show();
    }

    private void showError(String msg) {
        ToolUtil.showLongToast(msg, getActivity());
        WaitDialogFragment.newInstance().dismiss();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void updatePublicOrder(String status);
    }
}                                                                                                                                     