package com.AbdUlla.a4_order_station_driver.feature.reset.three;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.AbdUlla.a4_order_station_driver.databinding.FragmentResetStep3Binding;
import com.AbdUlla.a4_order_station_driver.models.Message;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.WaitDialogFragment;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;

public class ResetStep3 extends Fragment implements DialogView<Message> {

    public static final int page = 403;

    private FragmentResetStep3Binding binding;

    private ResetStep3Presenter presenter;

    public ResetStep3(BaseActivity baseActivity, String mobile) {
        presenter = new ResetStep3Presenter(baseActivity, this, mobile);
    }

    public static ResetStep3 newInstance(BaseActivity baseActivity, String mobile) {
        return new ResetStep3(baseActivity, mobile);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentResetStep3Binding.inflate(getLayoutInflater());
        click();
        return binding.getRoot();
    }

    private void click() {
        binding.btnConfirm.setOnClickListener(view -> presenter.ValidInput(
                binding.etEnterPassword, binding.etEnterConfirmPassword));
    }

    @Override
    public void setData(Message message) {

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
