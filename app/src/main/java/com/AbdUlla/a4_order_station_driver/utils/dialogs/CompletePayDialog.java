package com.AbdUlla.a4_order_station_driver.utils.dialogs;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.FragmentCompletePayDialogBinding;
import com.AbdUlla.a4_order_station_driver.models.Message;
import com.AbdUlla.a4_order_station_driver.models.OrderStation;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;

public class CompletePayDialog extends DialogFragment {

    public static final String ORDER_KEY = "order_key";

    private FragmentCompletePayDialogBinding binding;

    private OrderStation orderStation;
    private PublicOrderSettingsDialog.Listener listener;

    public static CompletePayDialog newInstance(OrderStation order, PublicOrderSettingsDialog.Listener listener) {
        CompletePayDialog fragment = new CompletePayDialog(listener);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ORDER_KEY, order);
        fragment.setArguments(bundle);
        return fragment;
    }

    public CompletePayDialog(PublicOrderSettingsDialog.Listener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCompletePayDialogBinding.inflate(getLayoutInflater());
        data();
        click();
        return binding.getRoot();
    }

    private void click() {
        binding.btnDelivered.setOnClickListener(view -> delivered());
    }

    private void data() {
        if (getArguments().getSerializable(ORDER_KEY) != null) {
            orderStation = (OrderStation) getArguments().getSerializable(ORDER_KEY);
            setData();
        }
    }


    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.8);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        setCancelable(false);
        super.onResume();
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                CompletePayDialog.this.dismiss();
                return true;
            } else return false;
        });
    }

    public void delivered() {
            WaitDialogFragment.newInstance().show(getChildFragmentManager(), "");

            new APIUtil<Message>(getActivity()).getData(AppController.getInstance()
                            .getApi().deliveredPublicOrder(orderStation.getId())
                    , new RequestListener<Message>() {
                        @Override
                        public void onSuccess(Message message, String msg) {
                            //PublicOrderViewFragment.billPrice = 0;
                            //OrdersFragment.viewPagerPage = OrderStationFragment.viewPagerPage;
                            //WalletFragment.viewPagerPage = OrderStationWalletFragment.viewPagerPage;
                            //listener.updatePublicOrder();
                            dismiss();
                        }

                        @Override
                        public void onError(String msg) {
                            ToolUtil.showLongToast(msg, getActivity());
                            WaitDialogFragment.newInstance().dismiss();
                        }

                        @Override
                        public void onFail(String msg) {
                            ToolUtil.showLongToast(msg, getActivity());
                            WaitDialogFragment.newInstance().dismiss();
                        }
                    });
    }

    private void setData() {
        /*binding.tvDetials.setText((getString(R.string.price_bill) + " = " + DecimalFormatterManager.getFormatterInstance()
                .format(PublicOrderViewFragment.billPrice) + " " +
                AppController.getInstance().getAppSettingsPreferences().getUser().getCountry().getCurrency_code()
                + "\n" + getString(R.string.delivery_price) + " = " + DecimalFormatterManager.getFormatterInstance()
                .format(Double.parseDouble(testOrder.getDelivery_cost())) + " " +
                AppController.getInstance().getAppSettingsPreferences().getUser().getCountry().getCurrency_code()
                + "\n" + getString(R.string.tax_price) + " = " + DecimalFormatterManager.getFormatterInstance()
                .format(Double.parseDouble(testOrder.getTax())) + " " +
                AppController.getInstance().getAppSettingsPreferences().getUser().getCountry().getCurrency_code()
                + "\n ---------------------\n"
                + getString(R.string.total) + " = " + DecimalFormatterManager.getFormatterInstance()
                .format((PublicOrderViewFragment.billPrice) + Double.parseDouble(testOrder.getDelivery_cost())
                        + Double.parseDouble(testOrder.getTax())) + " " +
                AppController.getInstance().getAppSettingsPreferences().getUser().getCountry().getCurrency_code()));*/
    }
}
