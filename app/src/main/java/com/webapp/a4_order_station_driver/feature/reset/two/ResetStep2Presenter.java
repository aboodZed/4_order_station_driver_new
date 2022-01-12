package com.webapp.a4_order_station_driver.feature.reset.two;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.webapp.a4_order_station_driver.R;
import com.webapp.a4_order_station_driver.feature.reset.three.ResetStep3;
import com.webapp.a4_order_station_driver.utils.util.ToolUtil;
import com.webapp.a4_order_station_driver.utils.language.BaseActivity;
import com.webapp.a4_order_station_driver.utils.listeners.DialogView;

import java.util.HashMap;
import java.util.Objects;

public class ResetStep2Presenter {

    private BaseActivity baseActivity;
    private DialogView<String> dialogView;
    private HashMap<String, String> map;

    public ResetStep2Presenter(BaseActivity baseActivity
            , DialogView<String> dialogView, HashMap<String, String> map) {
        this.baseActivity = baseActivity;
        this.dialogView = dialogView;
        this.map = map;
    }

    public void verify(String code) {
        if (TextUtils.isEmpty(code)) {
            ToolUtil.showLongToast(baseActivity.getString(R.string.empty_error), baseActivity);
            return;
        }

        if (code.length() != 6) {
            ToolUtil.showLongToast(baseActivity.getString(R.string.empty_error), baseActivity);
            return;
        }

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(Objects.requireNonNull(map.get("verify")), code);
        signIn(credential);
    }

    private void signIn(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(baseActivity, task -> {
                    if (task.isSuccessful()) {
                        baseActivity.navigate(ResetStep3.page);
                    }else {
                        ToolUtil.showLongToast(Objects.requireNonNull(task.getException())
                                .getLocalizedMessage(),baseActivity);
                    }
                });
    }

    public void reSend() {

    }
}
