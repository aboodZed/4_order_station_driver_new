package com.AbdUlla.a4_order_station_driver.feature.main.profile;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.FragmentEditProfileBinding;
import com.AbdUlla.a4_order_station_driver.databinding.FragmentProfileBinding;
import com.AbdUlla.a4_order_station_driver.feature.register.adapter.SpinnerAdapter;
import com.AbdUlla.a4_order_station_driver.models.City;
import com.AbdUlla.a4_order_station_driver.models.Result;
import com.AbdUlla.a4_order_station_driver.models.User;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.Photo.PhotoTakerManager;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfilePresenter {

    private BaseActivity baseActivity;
    private DialogView<String> dialogView;
    private PhotoTakerManager photoTakerManager;
    private int requestCode;
    private ProfileFragment.Listener listener;

    public ProfilePresenter(BaseActivity baseActivity, DialogView<String> dialogView,
                            PhotoTakerManager photoTakerManager, ProfileFragment.Listener listener) {
        this.baseActivity = baseActivity;
        this.dialogView = dialogView;
        this.photoTakerManager = photoTakerManager;
        this.listener = listener;
    }

    public void validInput(FragmentProfileBinding binding, HashMap<String, String> map) {

        String name = binding.tvUserName.getText().toString().trim();
        String email = binding.tvEmail.getText().toString().trim();
        String address = binding.tvAddress.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            binding.tvUserName.setError(baseActivity.getString(R.string.empty_error));
            return;
        }
        if (TextUtils.isEmpty(email)) {
            binding.tvEmail.setError(baseActivity.getString(R.string.empty_error));
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tvEmail.setError(baseActivity.getString(R.string.un_match_pattern));
            return;
        }
        if (TextUtils.isEmpty(address)) {
            binding.tvAddress.setError(baseActivity.getString(R.string.empty_error));
            return;
        }
        //fill data
        map.put("name", name);
        map.put("email", email);
        map.put("address", address);
        City city = (City) binding.spNeighborhood.getSelectedItem();
        map.put("city_id", String.valueOf(city.getId()));

        generateFCMToken(baseActivity, map);
    }

    public void generateFCMToken(Context context, HashMap<String, String> map) {
        dialogView.showDialog("");
        FirebaseApp.initializeApp(context);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("failed_fcm_token", "getInstanceId failed", task.getException());
                return;
            }
            // Get new Instance ID token
            String token = task.getResult();
            Log.e("fcm_token", "" + token);
            map.put("fcm_token", token);
            updateProfile(map);
        });
    }

    private void updateProfile(HashMap<String, String> params) {
        new APIUtil<Result<User>>(baseActivity).getData(AppController.getInstance().getApi()
                .updateProfile(params), new RequestListener<Result<User>>() {
            @Override
            public void onSuccess(Result<User> result, String msg) {
                AppController.getInstance().getAppSettingsPreferences().setUser(result.getData());
                dialogView.hideDialog();
                listener.onProfileUpdate();
                baseActivity.navigate(ProfileFragment.page);
            }

            @Override
            public void onError(String msg) {
                ToolUtil.showLongToast(msg, baseActivity);
                dialogView.hideDialog();
            }

            @Override
            public void onFail(String msg) {
                ToolUtil.showLongToast(msg, baseActivity);
                dialogView.hideDialog();
            }
        });
    }

    public void getCities(FragmentProfileBinding binding) {
//        dialogView.showDialog("");
//        new APIUtil<Result<ArrayList<City>>>(baseActivity).getData(AppController.getInstance()
//                        .getApi().getCities(AppController.getInstance().getAppSettingsPreferences().
//                                getSettings().getData().getCountry_id())
//                , new RequestListener<Result<ArrayList<City>>>() {
//                    @Override
//                    public void onSuccess(Result<ArrayList<City>> result, String msg) {
//                       dialogView.hideDialog();
        ArrayList<City> cities = AppController
                .getInstance().getAppSettingsPreferences().getCities().getCities();
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(baseActivity, cities);
        binding.spNeighborhood.setAdapter(spinnerAdapter);
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getId() == AppController.getInstance()
                    .getAppSettingsPreferences().getUser().getCity().getId()) {
                binding.spNeighborhood.setSelection(i);
            }
        }
//                    }
//
//                    @Override
//                    public void onError(String msg) {
//                        dialogView.hideDialog();
//                        ToolUtil.showLongToast(msg, baseActivity);
//                    }
//
//                    @Override
//                    public void onFail(String msg) {
//                        dialogView.hideDialog();
//                        ToolUtil.showLongToast(msg, baseActivity);
//                    }
//                });
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public void onActivityResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppContent.REQUEST_IMAGE_AVATAR_UPLOAD:
                case AppContent.REQUEST_IMAGE_VEHICLE_UPLOAD:
                case AppContent.REQUEST_IMAGE_VEHICLE_LICENSE_UPLOAD:
                case AppContent.REQUEST_IMAGE_VEHICLE_INSURANCE_UPLOAD:
                case AppContent.REQUEST_IMAGE_IDENTITY_UPLOAD:
                case AppContent.REQUEST_IMAGE_YOUR_LICENSE_UPLOAD:
                    photoTakerManager.processGalleryPhoto(baseActivity, data);
                    break;
                case AppContent.REQUEST_IMAGE_AVATAR_CAMERA:
                case AppContent.REQUEST_IMAGE_VEHICLE_CAMERA:
                case AppContent.REQUEST_IMAGE_VEHICLE_LICENSE_CAMERA:
                case AppContent.REQUEST_IMAGE_VEHICLE_INSURANCE_CAMERA:
                case AppContent.REQUEST_IMAGE_IDENTITY_CAMERA:
                case AppContent.REQUEST_IMAGE_YOUR_LICENSE_CAMERA:
                    photoTakerManager.processCameraPhoto(baseActivity);
                    break;
            }
        }
    }
}
