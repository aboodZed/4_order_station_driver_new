package com.AbdUlla.a4_order_station_driver.feature.splash;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.AbdUlla.a4_order_station_driver.feature.login.LoginActivity;
import com.AbdUlla.a4_order_station_driver.feature.main.MainActivity2;
import com.AbdUlla.a4_order_station_driver.models.AppSettings;
import com.AbdUlla.a4_order_station_driver.models.Order;
import com.AbdUlla.a4_order_station_driver.models.OrderStation;
import com.AbdUlla.a4_order_station_driver.models.PublicOrder;
import com.AbdUlla.a4_order_station_driver.models.Result;
import com.AbdUlla.a4_order_station_driver.models.User;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.language.AppLanguageUtil;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;
import com.AbdUlla.a4_order_station_driver.utils.location.tracking.OrderGPSTracking;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;

class SplashPresenter {

    private BaseActivity baseActivity;

    public SplashPresenter(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
        //functions
        setLanguage();
        //checkOrderProcess();
        //checkUserLogin();
        getSettings();
    }

    private void getSettings() {
        new APIUtil<AppSettings>(baseActivity).getData(AppController.getInstance()
                .getApi().getSettings(), new RequestListener<AppSettings>() {
            @Override
            public void onSuccess(AppSettings appSettings, String msg) {
                AppController.getInstance().getAppSettingsPreferences().setAppSettings(appSettings);
                FirebaseFirestore.getInstance().collection(AppContent.FIREBASE_PUBLIC_TRACKING_INSTANCE)
                        .document(AppContent.FIREBASE_DATA).addSnapshotListener((value, error) -> {
                        });

                if (!AppController.getInstance().getAppSettingsPreferences().getToken().trim().equals("Bearer")) {
                    if (AppController.getInstance().getAppSettingsPreferences().getUser() != null) {
                        if (AppController.getInstance().getAppSettingsPreferences().getUser().isComplete()) {
                            baseActivity.navigate(MainActivity2.page);
                        } else {
                            baseActivity.navigate(LoginActivity.page);
                        }
                    } else {
                        baseActivity.navigate(LoginActivity.page);
                    }
                    Log.e(getClass().getName() + ": userToken", AppController.getInstance()
                            .getAppSettingsPreferences().getToken());
                } else {
                    baseActivity.navigate(LoginActivity.page);
                }
            }

            @Override
            public void onError(String msg) {
                ToolUtil.showLongToast(msg, baseActivity);
                baseActivity.navigate(LoginActivity.page);
            }

            @Override
            public void onFail(String msg) {
                ToolUtil.showLongToast(msg, baseActivity);
                baseActivity.navigate(LoginActivity.page);
            }
        });
    }

    private void setLanguage() {
        if (AppController.getInstance().getAppSettingsPreferences().isFirstRun()) {
            AppLanguageUtil.getInstance().setAppFirstRunLng();
        } else {
            AppLanguageUtil.getInstance().setAppLanguage(baseActivity
                    , AppController.getInstance().getAppSettingsPreferences().getAppLanguage());
        }
    }

//    private void checkOrderProcess() {
//        Order order = AppController.getInstance().getAppSettingsPreferences().getTrackingOrderStation();
//
//        if (AppController.getInstance().getAppSettingsPreferences().getTrackingOrderStation() != null) {
//            if (order.getType().equals(AppContent.TYPE_ORDER_4STATION)) {
//                new APIUtil<Result<OrderStation>>(baseActivity).getData(AppController.getInstance().getApi()
//                        .getOrderStation(order.getId()), new RequestListener<Result<OrderStation>>() {
//                    @Override
//                    public void onSuccess(Result<OrderStation> result, String msg) {
//                        if (result.getData().getStatus().equals(AppContent.DELIVERED_STATUS)) {
//                            //OrderGPSTracking.newInstance(baseActivity).removeUpdates();
//                        } else {
//                            //OrderGPSTracking.newInstance(baseActivity).startGPSTracking();
//                        }
//                    }
//
//                    @Override
//                    public void onError(String msg) {
//
//                    }
//
//                    @Override
//                    public void onFail(String msg) {
//
//                    }
//                });
//            } else {
//                new APIUtil<Result<PublicOrder>>(baseActivity).getData(AppController.getInstance().getApi()
//                        .getPublicOrder(order.getId()), new RequestListener<Result<PublicOrder>>() {
//                    @Override
//                    public void onSuccess(Result<PublicOrder> result, String msg) {
//                        if (result.getData().getStatus().equals(AppContent.DELIVERED_STATUS)) {
//                            //OrderGPSTracking.newInstance(baseActivity).removeUpdates();
//                        } else {
//                            //OrderGPSTracking.newInstance(baseActivity).startGPSTracking();
//                        }
//                    }
//
//                    @Override
//                    public void onError(String msg) {
//
//                    }
//
//                    @Override
//                    public void onFail(String msg) {
//
//                    }
//                });
//            }
//        }
//    }
//
//    private void checkUserLogin() {
//        if (!AppController.getInstance().getAppSettingsPreferences().getToken().equals("")) {
//            new APIUtil<Result<User>>(baseActivity).getData(AppController.getInstance().getApi().getUserData()
//                    , new RequestListener<Result<User>>() {
//                        @Override
//                        public void onSuccess(Result<User> result, String msg) {
//                            //update user
//                            User user = AppController.getInstance().getAppSettingsPreferences().getUser();
//                            AppController.getInstance().getAppSettingsPreferences().setUser(user);
//                            //print user token
//                            Log.e(getClass().getName() + " : userData", result.getData().toString());
//
//                            Log.e("usertoken", AppController.getInstance()
//                                    .getAppSettingsPreferences().getToken());
//                            //navigate
//                            FirebaseFirestore.getInstance().collection(AppContent.FIREBASE_PUBLIC_TRACKING_INSTANCE)
//                                    .document(AppContent.FIREBASE_DATA).addSnapshotListener((value, error) -> {
//                                        String s = value.getString(AppContent.FIREBASE_STATUS);
//                                    });
//                            baseActivity.navigate(MainActivity2.page);
//                        }
//
//                        @Override
//                        public void onError(String msg) {
//                            ToolUtil.showLongToast(msg, baseActivity);
//                            baseActivity.navigate(LoginActivity.page);
//                        }
//
//                        @Override
//                        public void onFail(String msg) {
//                            ToolUtil.showLongToast(msg, baseActivity);
//                            baseActivity.navigate(LoginActivity.page);
//                        }
//                    });
//
//            /*
//            public void onSuccess(User user, String msg) {
//                            //update user
//                            Login login = AppController.getInstance().getAppSettingsPreferences().getLogin();
//                            login.setUser(user);
//                            AppController.getInstance().getAppSettingsPreferences().setLogin(login);
//                            //print user token
//                            Log.e(getClass().getName() + " : userData", user.toString());
//
//                            Log.e("usertoken", AppController.getInstance()
//                                    .getAppSettingsPreferences().getLogin().getAccess_token());
//                            //navigate
//                            FirebaseFirestore.getInstance().collection(AppContent.FIREBASE_PUBLIC_TRACKING_INSTANCE)
//                                    .document(AppContent.FIREBASE_DATA).addSnapshotListener((value, error) -> {
//                                        String s = value.getString(AppContent.FIREBASE_STATUS);
//                                    });
//                            baseActivity.navigate(MainActivity.page);
//                        }
//
//                        @Override
//                        public void onError(String msg) {
//                            ToolUtil.showLongToast(msg, baseActivity);
//                            baseActivity.navigate(LoginActivity.page);
//                        }
//
//                        @Override
//                        public void onFail(String msg) {
//                            ToolUtil.showLongToast(msg, baseActivity);
//                            baseActivity.navigate(LoginActivity.page);
//                        }
//             */
//
//        } else {
//            baseActivity.navigate(LoginActivity.page);
//        }
//    }

}
