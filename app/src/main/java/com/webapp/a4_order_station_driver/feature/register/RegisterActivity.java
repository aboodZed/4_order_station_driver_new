package com.webapp.a4_order_station_driver.feature.register;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.webapp.a4_order_station_driver.R;
import com.webapp.a4_order_station_driver.databinding.ActivityRegisterBinding;
import com.webapp.a4_order_station_driver.feature.login.LoginActivity;
import com.webapp.a4_order_station_driver.feature.main.MainActivity2;
import com.webapp.a4_order_station_driver.feature.register.one.RegisterStepOneFragment;
import com.webapp.a4_order_station_driver.feature.register.two.RegisterStepTwoFragment;
import com.webapp.a4_order_station_driver.utils.AppContent;
import com.webapp.a4_order_station_driver.utils.util.NavigateUtil;
import com.webapp.a4_order_station_driver.utils.language.BaseActivity;

public class RegisterActivity extends BaseActivity {

    public static final int page = 300;

    private ActivityRegisterBinding binding;

    private RegisterStepOneFragment registerStep1;
    private RegisterStepTwoFragment registerStep2;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        super.setRootView(binding.getRoot());
        super.onCreate(savedInstanceState);
        //view
        presenter = new RegisterPresenter(this);
        initFragment();
    }

    //functions
    private void initFragment() {
        registerStep1 = RegisterStepOneFragment.newInstance(this);
        registerStep2 = RegisterStepTwoFragment.newInstance(this);
        if (getIntent().getExtras() != null){
            navigate(getIntent().getExtras().getInt(AppContent.PAGE));
        }else {
            navigate(RegisterStepOneFragment.page);
        }
    }

    public void back() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container)
                instanceof RegisterStepTwoFragment) {
            navigate(RegisterStepOneFragment.page);
        } else {
            navigate(LoginActivity.page);
        }
    }

    @Override
    public void navigate(int page) {
        switch (page) {
            case RegisterStepOneFragment.page:
                new NavigateUtil().replaceFragment(getSupportFragmentManager(), registerStep1, R.id.fragment_container);
                break;
            case RegisterStepTwoFragment.page:
                new NavigateUtil().replaceFragment(getSupportFragmentManager(), registerStep2, R.id.fragment_container);
                break;
            case MainActivity2.page:
                new NavigateUtil().activityIntent(this, MainActivity2.class, false);
                break;
            case LoginActivity.page:
                new NavigateUtil().activityIntent(this, LoginActivity.class, false);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
