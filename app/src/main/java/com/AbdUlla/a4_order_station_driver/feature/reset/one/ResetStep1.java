package com.AbdUlla.a4_order_station_driver.feature.reset.one;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.AbdUlla.a4_order_station_driver.databinding.FragmentResetStep1Binding;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.WaitDialogFragment;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;

import java.util.HashMap;

public class ResetStep1 extends Fragment implements DialogView<String> {

    public static final int page = 401;

    private FragmentResetStep1Binding binding;

    private ResetStep1Presenter presenter;
    private BaseActivity baseActivity;

    public ResetStep1(BaseActivity baseActivity) {
        presenter = new ResetStep1Presenter(baseActivity, this);
    }

    public static ResetStep1 newInstance(BaseActivity baseActivity) {
        return new ResetStep1(baseActivity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentResetStep1Binding.inflate(getLayoutInflater());
        data();
        click();
        return binding.getRoot();
    }

    private void data() {
        binding.tvCode.setText(AppController.getInstance()
                .getAppSettingsPreferences().getSettings().getData().getPhone_code());
    }

    private void click() {
        binding.btnSend.setOnClickListener(view -> presenter.validInput(binding.etEnterPhone));
    }

    public HashMap<String, String> getData() {
        return presenter.getCredential();
    }

    public String getMobile(){
        return presenter.getMobile();
    }

    @Override
    public void setData(String s) {

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
