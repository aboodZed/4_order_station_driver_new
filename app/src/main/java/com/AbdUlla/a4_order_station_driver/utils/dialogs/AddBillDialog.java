package com.AbdUlla.a4_order_station_driver.utils.dialogs;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.fragment.app.DialogFragment;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.FragmentAddBillDialogBinding;
import com.AbdUlla.a4_order_station_driver.models.Message;
import com.AbdUlla.a4_order_station_driver.models.PublicOrder;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;

import java.util.Objects;

public class AddBillDialog extends DialogFragment implements DialogView<Message> {

    private FragmentAddBillDialogBinding binding;

    private PublicOrder publicOrder = AppController.getInstance()
            .getAppSettingsPreferences().getTrackingPublicOrder();

    public static AddBillDialog newInstance() {
        return new AddBillDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddBillDialogBinding.inflate(getLayoutInflater());
        data();
        click();
        return binding.getRoot();
    }

    private void click() {
        binding.btnSend.setOnClickListener(view -> send());
    }

    private void data() {
        /*if (getArguments() != null) {
            publicOrder = (PublicOrder) getArguments().get(AppContent.ORDER_OBJECT);
            String m = getArguments().getString(ENTER);
            binding.tvEnter.setText(m);
            binding.tvMessage.setText(m);
            if (PublicOrderViewFragment.billPrice != 0 && m.equals(getString(R.string.show_bill))) {
                binding.etEnterPrice.setText(String.valueOf(PublicOrderViewFragment.billPrice));
                binding.etEnterPrice.setFocusable(false);
                binding.btnSend.setVisibility(View.GONE);
            }
        }*/
    }

    @Override
    public void onResume() {
        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog())
                .getWindow().getAttributes();
        params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getDialog().getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
        getDialog().getWindow().setAttributes(params);
        setCancelable(false);
        super.onResume();
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {
                dismiss();
                return true;
            } else return false;
        });
    }

    public void send() {

        if (TextUtils.isEmpty(binding.etEnterPrice.getText())) {
            binding.etEnterPrice.setError(getString(R.string.empty_error));
            return;
        }
        double price = Double.parseDouble(binding.etEnterPrice.getText().toString().trim());

        if (price > 0) {
            uploadBill(price);
        } else {
            binding.etEnterPrice.setError(getString(R.string.zero));
        }
//        if (PublicOrderViewFragment.billPrice == 0) {
//            PublicOrderViewFragment.billPrice = price;
//            binding.etEnterPrice.setText(getString(R.string.re_enter_bill_price));
//            binding.etEnterPrice.setText("");
//        } else {
//            uploadBill(price);
//        }
    }

    private void uploadBill(double price) {
        showDialog("");
        new APIUtil<Message>(getActivity()).getData(AppController.getInstance().getApi()
                        .sendInvoiceValue(publicOrder.getId(), price)
                , new RequestListener<Message>() {
                    @Override
                    public void onSuccess(Message message, String msg) {
                        publicOrder.setPurchase_invoice_value(String.valueOf(price));
                        AppController.getInstance().getAppSettingsPreferences()
                                .setTrackingPublicOrder(publicOrder);
                        setData(message);
                    }

                    @Override
                    public void onError(String msg) {
                        ToolUtil.showLongToast(msg, getActivity());
                        hideDialog();
                    }

                    @Override
                    public void onFail(String msg) {
                        ToolUtil.showLongToast(msg, getActivity());
                        hideDialog();
                    }
                });

    }

    @Override
    public void setData(Message message) {
        hideDialog();
        dismiss();
    }

    @Override
    public void showDialog(String s) {
        WaitDialogFragment.newInstance().show(getChildFragmentManager(), "");
    }

    @Override
    public void hideDialog() {
        WaitDialogFragment.newInstance().dismiss();
    }
}
