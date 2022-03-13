package com.AbdUlla.a4_order_station_driver.utils.language;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.utils.AppController;

public abstract class BaseActivity extends AppCompatActivity {

    public View rootView;

    protected void setRootView(View rootView) {
        this.rootView = rootView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(rootView);
        super.onCreate(savedInstanceState);
        ImageView view = findViewById(R.id.iv_back);
        if (view != null) {
            if (AppController.getInstance().getAppSettingsPreferences().getAppLanguage().equals(AppLanguageUtil.ARABIC))
                view.setRotation(180);
        }
    }

    public abstract void navigate(int page);
}
