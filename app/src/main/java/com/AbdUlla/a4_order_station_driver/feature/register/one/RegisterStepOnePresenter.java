package com.AbdUlla.a4_order_station_driver.feature.register.one;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Spinner;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.feature.register.two.RegisterStepTwoFragment;
import com.AbdUlla.a4_order_station_driver.models.City;
import com.AbdUlla.a4_order_station_driver.models.Result;
import com.AbdUlla.a4_order_station_driver.models.User;
import com.AbdUlla.a4_order_station_driver.utils.util.APIImageUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.Photo.PhotoTakerManager;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterStepOnePresenter implements DialogView<String> {


    private BaseActivity baseActivity;
    private DialogView<ArrayList<City>> dialogView;
    private PhotoTakerManager photoTakerManager;
    private int requestCode;
    private String fileName;

    public RegisterStepOnePresenter(BaseActivity baseActivity
            , DialogView<ArrayList<City>> dialogView, PhotoTakerManager photoTakerManager) {
        this.baseActivity = baseActivity;
        this.dialogView = dialogView;
        this.photoTakerManager = photoTakerManager;
    }

    public void validInput(EditText etEnterName, EditText etEnterEmail, EditText etEnterAddress
            , EditText etEnterPhone, EditText etEnterPassword, EditText etEnterConfirmPassword, Spinner spinner) {

        String name = etEnterName.getText().toString().trim();
        String email = etEnterEmail.getText().toString().trim();
        String mobile = etEnterPhone.getText().toString().trim();
        String password = etEnterPassword.getText().toString().trim();
        String confirmPassword = etEnterConfirmPassword.getText().toString().trim();
        String address = etEnterAddress.getText().toString().trim();
        int phone_length = AppController.getInstance().getAppSettingsPreferences()
                .getSettings().getData().getPhone_length();
        int city_id = ((City) spinner.getSelectedItem()).getId();
        if (TextUtils.isEmpty(fileName)) {
            ToolUtil.showLongToast(baseActivity.getString(R.string.fill_photos), baseActivity);
            return;
        }
        if (TextUtils.isEmpty(name)) {
            etEnterName.setError(baseActivity.getString(R.string.empty_error));
            return;
        }
        if (TextUtils.isEmpty(email)) {
            etEnterEmail.setError(baseActivity.getString(R.string.empty_error));
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEnterEmail.setError(baseActivity.getString(R.string.un_match_pattern));
            return;
        }
        if (TextUtils.isEmpty(mobile)) {
            etEnterPhone.setError(baseActivity.getString(R.string.empty_error));
            return;
        }
        if (mobile.length() != phone_length) {
            etEnterPhone.setError(baseActivity.getString(R.string.phone_must) + phone_length + baseActivity.getString(R.string.digits));
            return;
        }
        if (TextUtils.isEmpty(address)) {
            etEnterAddress.setError(baseActivity.getString(R.string.empty_error));
            return;
        }
        if (city_id == 0) {
            ToolUtil.showLongToast(baseActivity.getString(R.string.select_city), baseActivity);
            return;
        }
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
        finishStepOne(User.mapUploadStepOne(name, AppController.getInstance()
                        .getAppSettingsPreferences().getSettings().getData().getPhone_code() + mobile
                , email, password, address, AppController.getInstance().getAppSettingsPreferences()
                        .getSettings().getData().getCountry_id(), city_id, fileName));
    }

    private void finishStepOne(HashMap<String, String> map) {
        dialogView.showDialog("");

        new APIUtil<Result<User>>(baseActivity).getData(AppController.getInstance().getApi().SignUp(map)
                , new RequestListener<Result<User>>() {
                    @Override
                    public void onSuccess(Result<User> result, String msg) {
                        dialogView.hideDialog();
                        if (result.isSuccess()) {
                            Log.e(getClass().getName() + "user", result.getData().toString());
                            AppController.getInstance().getAppSettingsPreferences().setUser(result.getData());
                            AppController.getInstance().getAppSettingsPreferences().setToken(result.getData().getToken());
                            baseActivity.navigate(RegisterStepTwoFragment.page);
                        } else {
                            StringBuilder errors = new StringBuilder();
                            for (String s : result.getErrors()) {
                                errors.append(s).append("\n");
                            }
                            ToolUtil.showLongToast(errors.toString(), baseActivity);
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


    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public void onActivityResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppContent.REQUEST_STUDIO:
                    photoTakerManager.processGalleryPhoto(baseActivity, data);
                    break;
                case AppContent.REQUEST_CAMERA:
                    photoTakerManager.processCameraPhoto(baseActivity);
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            photoTakerManager.deleteLastTakenPhoto();
        }
    }

    public void uploadImage(Bitmap bitmap) {
        APIImageUtil.uploadImage(baseActivity, this, bitmap);
    }

    @Override
    public void setData(String s) {
        fileName = s;
    }

    @Override
    public void showDialog(String s) {
        dialogView.showDialog(s);
    }

    @Override
    public void hideDialog() {
        dialogView.hideDialog();
    }

    public void getCities() {
        dialogView.setData(AppController.getInstance().getAppSettingsPreferences().getCities().getCities());
    }
}
