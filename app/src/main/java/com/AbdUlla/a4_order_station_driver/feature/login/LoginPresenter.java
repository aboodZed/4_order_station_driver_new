package com.AbdUlla.a4_order_station_driver.feature.login;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.AbdUlla.a4_order_station_driver.feature.subscribe.SubscribeActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.feature.main.MainActivity2;
import com.AbdUlla.a4_order_station_driver.feature.register.two.RegisterStepTwoFragment;
import com.AbdUlla.a4_order_station_driver.models.Result;
import com.AbdUlla.a4_order_station_driver.models.User;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;

import java.util.HashMap;

public class LoginPresenter {

    private BaseActivity baseActivity;
    private DialogView<Result<User>> dialogView;

    public LoginPresenter(BaseActivity baseActivity, DialogView<Result<User>> dialogView) {
        this.baseActivity = baseActivity;
        this.dialogView = dialogView;
    }

    public void checkInput(EditText etEnterPhone, EditText etEnterPassword) {
        int phone_length = AppController.getInstance().getAppSettingsPreferences()
                .getSettings().getData().getPhone_length();

        if (etEnterPhone.getText().toString().trim().equals("")) {
            etEnterPhone.setError(baseActivity.getString(R.string.empty_error));
            return;
        }
        if (etEnterPhone.getText().toString().trim().length() != phone_length) {
            etEnterPhone.setError(baseActivity.getString(R.string.phone_must) + phone_length + baseActivity.getString(R.string.digits));
            return;
        }
        if (etEnterPassword.getText().toString().trim().equals("")) {
            etEnterPassword.setError(baseActivity.getString(R.string.empty_error));
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", AppController.getInstance().getAppSettingsPreferences().getSettings()
                .getData().getPhone_code() + etEnterPhone.getText().toString().trim());
        map.put("password", etEnterPassword.getText().toString().trim());
        map.put("remember_me", "True");
        generateFCMToken(baseActivity, map);
    }


    public void generateFCMToken(Context context, HashMap<String, String> map) {
        dialogView.showDialog("");
        FirebaseApp.initializeApp(context);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("Main", "getInstanceId failed", task.getException());
                return;
            }
            // Get new Instance ID token
            String token = task.getResult();
            Log.e("fcm token", "" + token);
            map.put("fcm_token", token);
            login(map);
        });
    }

    private void login(HashMap<String, String> map) {
        new APIUtil<Result<User>>(baseActivity).getData(AppController.getInstance().getApi().login(map),
                new RequestListener<Result<User>>() {
                    @Override
                    public void onSuccess(Result<User> result, String msg) {
                        dialogView.hideDialog();
                        if (result.isSuccess()) {
                            Log.e("sharedPreferences", result.toString());
                            AppController.getInstance().getAppSettingsPreferences().setUser(result.getData());
                            AppController.getInstance().getAppSettingsPreferences().setToken(result.getData().getToken());
                            //navigation
                            if (result.getData().getBalance().getIsSubscribe()) {
                                baseActivity.navigate(MainActivity2.page);
                            } else {
                                baseActivity.navigate(SubscribeActivity.page);
                            }
                        } else {
                            if (result.getData() != null) {
                                if (!result.getData().isComplete()) {
                                    baseActivity.navigate(RegisterStepTwoFragment.page);
                                } else {
                                    ToolUtil.showLongToast(result.getMessage(), baseActivity);
                                }
                            } else {
                                ToolUtil.showLongToast(result.getMessage(), baseActivity);
                            }
                        }
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
