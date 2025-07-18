package com.AbdUlla.a4_order_station_driver.utils.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.FragmentWaitBinding;

import java.util.Objects;

public class WaitDialogFragment extends DialogFragment {

    private FragmentWaitBinding binding;

    private static WaitDialogFragment fragment;

    public static WaitDialogFragment newInstance() {
        if (fragment == null) {
            fragment = new WaitDialogFragment();
            return fragment;
        } else return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWaitBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog())
                .getWindow().getAttributes();
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        getDialog().getWindow().setAttributes(params);
        setCancelable(false);
        super.onResume();
    }
}
