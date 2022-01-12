package com.webapp.a4_order_station_driver.feature.register;

import android.content.pm.PackageManager;

import com.webapp.a4_order_station_driver.R;
import com.webapp.a4_order_station_driver.utils.util.ToolUtil;
import com.webapp.a4_order_station_driver.utils.language.BaseActivity;

class RegisterPresenter {

    private BaseActivity baseActivity;

    public RegisterPresenter(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ToolUtil.showLongToast(baseActivity.getString(R.string.permission_garnted), baseActivity);
        } else {
            ToolUtil.showLongToast(baseActivity.getString(R.string.permission_denial), baseActivity);
        }
    }
}
