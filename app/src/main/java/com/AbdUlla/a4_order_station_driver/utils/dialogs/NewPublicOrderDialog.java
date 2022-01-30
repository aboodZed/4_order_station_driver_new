package com.AbdUlla.a4_order_station_driver.utils.dialogs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.DialogNewOrderBinding;
import com.AbdUlla.a4_order_station_driver.feature.order.publicOrder.newPublicOrder.NewPublicOrderFragment;
import com.AbdUlla.a4_order_station_driver.models.PublicOrder;
import com.AbdUlla.a4_order_station_driver.models.Result;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.NavigateUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;

import java.util.Objects;

public class NewPublicOrderDialog extends DialogFragment {

    private DialogNewOrderBinding binding;

    private NewPublicOrderListener listener;
    private PublicOrder publicOrder;

    public static NewPublicOrderDialog newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(AppContent.ORDER_Id, id);
        NewPublicOrderDialog dialog = new NewPublicOrderDialog();
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        binding = DialogNewOrderBinding.inflate(getLayoutInflater());
        getData(getArguments().getInt(AppContent.ORDER_Id, -1));
        click();
        return binding.getRoot();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void click() {
        binding.llBackground.setBackground(Objects.requireNonNull(getDialog()).getContext()
                .getDrawable(R.drawable.back_new_public_order));
        binding.btnView.setOnClickListener(view -> {
            dismiss();
            new NavigateUtil().openPublicOrder(getContext(), publicOrder
                    , NewPublicOrderFragment.page, true);
            listener.allowLoadNewOrder();
        });
        binding.btnCancel.setOnClickListener(view ->
        {
            dismiss();
            listener.cancel();
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog);
    }

    @Override
    public void onResume() {
        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog()).getWindow().getAttributes();
        params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.8);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getDialog().getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
        getDialog().getWindow().setAttributes(params);
        setCancelable(false);
        super.onResume();
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                NewPublicOrderDialog.this.dismiss();
                return true;
            } else return false;
        });
    }

    public void setListener(NewPublicOrderListener listener) {
        this.listener = listener;
    }

    private void getData(int id) {
        if (id != -1) {
            WaitDialogFragment.newInstance().show(getChildFragmentManager(), "");
            new APIUtil<Result<PublicOrder>>(getActivity()).getData(AppController.getInstance()
                    .getApi().getPublicOrder(id), new RequestListener<Result<PublicOrder>>() {
                @Override
                public void onSuccess(Result<PublicOrder> result, String msg) {
                    WaitDialogFragment.newInstance().dismiss();
                    NewPublicOrderDialog.this.publicOrder = result.getData();
                    data();
                }

                @Override
                public void onError(String msg) {
                    WaitDialogFragment.newInstance().dismiss();
                    ToolUtil.showLongToast(msg, getActivity());
                    listener.allowLoadNewOrder();
                    dismiss();
                }

                @Override
                public void onFail(String msg) {
                    WaitDialogFragment.newInstance().dismiss();
                    ToolUtil.showLongToast(msg, getActivity());
                    listener.allowLoadNewOrder();
                    dismiss();
                }
            });
        } else {
            listener.allowLoadNewOrder();
            dismiss();
        }
    }

    private void data() {
        binding.tvFrom.setText(publicOrder.getStore_name());
        binding.tvTo.setText(publicOrder.getCustomer().getName());
        binding.tvPickupLocation.setText(publicOrder.getStore_address());
        //binding.tvDestLocation.setText(publicOrder.getDestination_address());
    }

    public interface NewPublicOrderListener {
        void allowLoadNewOrder();

        void cancel();
    }
}
