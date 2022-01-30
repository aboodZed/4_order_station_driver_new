package com.AbdUlla.a4_order_station_driver.feature.reset.three;

import android.text.TextUtils;
import android.widget.EditText;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.feature.login.LoginActivity;
import com.AbdUlla.a4_order_station_driver.models.Message;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;

import java.util.HashMap;

public class ResetStep3Presenter {

    private BaseActivity baseActivity;
    private DialogView<Message> dialogView;
    private String mobile;

    public ResetStep3Presenter(BaseActivity baseActivity, DialogView<Message> dialogView, String mobile) {
        this.baseActivity = baseActivity;
        this.dialogView = dialogView;
        this.mobile = mobile;
    }

    public void ValidInput(EditText etEnterPassword, EditText etEnterConfirmPassword) {
        String password = etEnterPassword.getText().toString().trim();
        String confirmPassword = etEnterConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(password)) {
            etEnterPassword.setError(baseActivity.getString(R.string.empty_error));
            return;
        }
        if (password.length() < 6) {
            etEnterPassword.setError(baseActivity.getString(R.string.length_password_error));
            return;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            etEnterConfirmPassword.setError(baseActivity.getString(R.string.empty_error));
            return;
        }
        if (confirmPassword.length() < 6) {
            etEnterConfirmPassword.setError(baseActivity.getString(R.string.length_password_error));
            return;
        }
        if (!password.equals(confirmPassword)) {
            etEnterConfirmPassword.setError(baseActivity.getString(R.string.match_error));
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("password", password);
        map.put("password_confirmation", confirmPassword);
        map.put("mobile", mobile);
        saveNewPassword(map);
    }

    private void saveNewPassword(HashMap<String, String> hashMap) {
        dialogView.showDialog("");

        new APIUtil<Message>(baseActivity).getData(AppController.getInstance().getApi()
                .resetPassword(hashMap), new RequestListener<Message>() {
            @Override
            public void onSuccess(Message message, String msg) {
                dialogView.hideDialog();
                //AppController.getInstance().getAppSettingsPreferences()
                // .setPassword(hashMap.get("password"));
                baseActivity.navigate(LoginActivity.page);
            }

            @Override
            public void onError(String msg) {
                dialogView.hideDialog();
                ToolUtil.showLongToast(msg, baseActivity);
            }

            @Override
            public void onFail(String msg) {
                dialogView.hideDialog();
                ToolUtil.showLongToast(msg, baseActivity);
            }
        });
    }
}
