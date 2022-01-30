package com.AbdUlla.a4_order_station_driver.feature.data.contact;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.FragmentContactBinding;
import com.AbdUlla.a4_order_station_driver.models.Message;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.util.NavigateUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.WaitDialogFragment;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;

import java.util.HashMap;

public class ContactFragment extends Fragment {

    public static final int page = 602;

    private FragmentContactBinding binding;

    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentContactBinding.inflate(getLayoutInflater());
        click();
        return binding.getRoot();
    }

    private void click() {
        binding.btnSend.setOnClickListener(view -> sendMessage());
        binding.ivFacebook.setOnClickListener(view -> facebook());
        binding.ivInstagram.setOnClickListener(view -> instagram());
        binding.ivTwitter.setOnClickListener(view -> twitter());
        binding.ivLinkin.setOnClickListener(view -> linkedin());
    }

    private void sendMessage() {
        String subject = binding.etTitle.getText().toString().trim();
        String message = binding.etMessage.getText().toString().trim();

        if (TextUtils.isEmpty(subject)) {
            binding.etTitle.setError(getString(R.string.empty_error));
            return;
        }
        if (TextUtils.isEmpty(message)) {
            binding.etMessage.setError(getString(R.string.empty_error));
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("subject", subject);
        map.put("message", message);

        WaitDialogFragment.newInstance().show(getChildFragmentManager(),"");

        new APIUtil<Message>(requireActivity()).getData(AppController.getInstance().getApi()
                .sendMessage(map), new RequestListener<Message>() {
            @Override
            public void onSuccess(Message message, String msg) {
                binding.etTitle.setText("");
                binding.etMessage.setText("");
                WaitDialogFragment.newInstance().dismiss();
                ToolUtil.showLongToast(message.getMessage(), requireActivity());
            }

            @Override
            public void onError(String msg) {
                WaitDialogFragment.newInstance().dismiss();
                ToolUtil.showLongToast(msg, requireActivity());
            }

            @Override
            public void onFail(String msg) {
                WaitDialogFragment.newInstance().dismiss();
                ToolUtil.showLongToast(msg, requireActivity());
            }
        });
    }


    void facebook() {
        new NavigateUtil().openLink(getActivity(), AppController.getInstance()
                .getAppSettingsPreferences().getSettings().getData().getFacebook_link());
    }

    void twitter() {
        new NavigateUtil().openLink(getActivity(), AppController.getInstance()
                .getAppSettingsPreferences().getSettings().getData().getTwitter_link());
    }

    void instagram() {
        new NavigateUtil().openLink(getActivity(), AppController.getInstance()
                .getAppSettingsPreferences().getSettings().getData().getInstagram_link());
    }

    void linkedin() {
        new NavigateUtil().openLink(getActivity(), AppController.getInstance()
                .getAppSettingsPreferences().getSettings().getData().getLinkedin_link());
    }
}
