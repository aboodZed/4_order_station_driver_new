package com.AbdUlla.a4_order_station_driver.utils.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.AbdUlla.a4_order_station_driver.databinding.DialogSignOutBinding;

public class SignOutDialog extends Dialog {

    private DialogSignOutBinding binding;

    private Context context;
    private FragmentManager fragmentManager;
    private Listener listener;

    public interface Listener {
        void onSignOut(SignOutDialog signOutDialog);
    }

    public SignOutDialog(@NonNull Context context, FragmentManager fragmentManager, Listener listener) {
        super(context);
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.listener = listener;
        binding = DialogSignOutBinding.inflate(LayoutInflater.from(context));
        setContentView(binding.getRoot());
        getWindow().setBackgroundDrawable(null);
        setClick();
    }

    private void setClick() {
        binding.signOut.setOnClickListener(v -> listener.onSignOut(this));
        binding.no.setOnClickListener(v -> dismiss());
    }
}
