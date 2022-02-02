package com.AbdUlla.a4_order_station_driver.feature.login;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;

import com.AbdUlla.a4_order_station_driver.databinding.ActivityLoginBinding;
import com.AbdUlla.a4_order_station_driver.feature.main.MainActivity2;
import com.AbdUlla.a4_order_station_driver.feature.register.RegisterActivity;
import com.AbdUlla.a4_order_station_driver.feature.register.two.RegisterStepTwoFragment;
import com.AbdUlla.a4_order_station_driver.feature.reset.ResetPasswordActivity;
import com.AbdUlla.a4_order_station_driver.models.AppSettings;
import com.AbdUlla.a4_order_station_driver.models.Result;
import com.AbdUlla.a4_order_station_driver.models.User;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.WaitDialogFragment;
import com.AbdUlla.a4_order_station_driver.utils.language.AppLanguageUtil;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;
import com.AbdUlla.a4_order_station_driver.utils.util.NavigateUtil;

public class LoginActivity extends BaseActivity implements DialogView<Result<User>> {

    public static final int page = 100;

    private ActivityLoginBinding binding;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        super.setRootView(binding.getRoot());
        super.onCreate(savedInstanceState);
        //presenter
        presenter = new LoginPresenter(this, this);
        //view
        data();
        //on click
        click();
    }

    private void data() {
        AppSettings settings = AppController.getInstance().getAppSettingsPreferences().getSettings();
        binding.tvCode.setText(settings.getData().getPhone_code());
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(settings.getData().getPhone_length());
        binding.etEnterPhone.setFilters(filters);
    }

    private void click() {
        binding.btnLogin.setOnClickListener(view -> presenter.checkInput(binding.etEnterPhone, binding.etEnterPassword));
        binding.tvForget.setOnClickListener(view -> navigate(ResetPasswordActivity.page));
        binding.btnSignUp.setOnClickListener(view -> navigate(RegisterActivity.page));

        if (AppController.getInstance().getAppSettingsPreferences().getAppLanguage().equals(AppLanguageUtil.ARABIC)) {
            binding.tvArabic.setVisibility(View.GONE);
            binding.tvEnglish.setVisibility(View.VISIBLE);
        } else {
            binding.tvArabic.setVisibility(View.VISIBLE);
            binding.tvEnglish.setVisibility(View.GONE);
        }

        binding.tvArabic.setOnClickListener(view -> {
            AppLanguageUtil.getInstance().setAppLanguage(LoginActivity.this, AppLanguageUtil.ARABIC);
            binding.tvArabic.setVisibility(View.GONE);
            binding.tvEnglish.setVisibility(View.VISIBLE);
            recreate();
        });

        binding.tvEnglish.setOnClickListener(view -> {
            AppLanguageUtil.getInstance().setAppLanguage(LoginActivity.this, AppLanguageUtil.English);
            binding.tvArabic.setVisibility(View.VISIBLE);
            binding.tvEnglish.setVisibility(View.GONE);
            recreate();
        });
    }

    @Override
    public void setData(Result<User> result) {
    }

    @Override
    public void showDialog(String s) {
        WaitDialogFragment.newInstance().show(getSupportFragmentManager(), "");
    }

    @Override
    public void hideDialog() {
        WaitDialogFragment.newInstance().dismiss();
    }


    @Override
    public void navigate(int page) {
        switch (page) {
            case MainActivity2.page:
                new NavigateUtil().activityIntent(this, MainActivity2.class, false);
                break;
            case RegisterActivity.page:
                new NavigateUtil().activityIntent(this, RegisterActivity.class, false);
                break;
            case RegisterStepTwoFragment.page:
                new NavigateUtil().activityIntentWithPage(this, RegisterActivity.class
                        , true, RegisterStepTwoFragment.page);
                break;
            case ResetPasswordActivity.page:
                new NavigateUtil().activityIntent(this, ResetPasswordActivity.class, false);
                break;
        }
    }
}
