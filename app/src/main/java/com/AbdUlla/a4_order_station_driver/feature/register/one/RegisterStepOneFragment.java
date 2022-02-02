package com.AbdUlla.a4_order_station_driver.feature.register.one;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.AbdUlla.a4_order_station_driver.databinding.FragmentRegisterStep1Binding;
import com.AbdUlla.a4_order_station_driver.feature.register.adapter.SpinnerAdapter;
import com.AbdUlla.a4_order_station_driver.models.AppSettings;
import com.AbdUlla.a4_order_station_driver.models.City;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.Photo.PhotoTakerManager;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.ItemSelectImageDialogFragment;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.WaitDialogFragment;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;

import java.util.ArrayList;

public class RegisterStepOneFragment extends Fragment implements RequestListener<Bitmap>
        , DialogView<ArrayList<City>> {

    public static final int page = 301;

    private FragmentRegisterStep1Binding binding;

    private ItemSelectImageDialogFragment itemSelectImageDialogFragment;
    private RegisterStepOnePresenter presenter;
    private PhotoTakerManager photoTakerManager;
    private SpinnerAdapter spinnerAdapter;

    private ActivityResultLauncher<Intent> launcher;

    public static RegisterStepOneFragment newInstance(BaseActivity baseActivity) {
        return new RegisterStepOneFragment(baseActivity);
    }

    public RegisterStepOneFragment(BaseActivity baseActivity) {
        photoTakerManager = new PhotoTakerManager(this);
        presenter = new RegisterStepOnePresenter(baseActivity, this, photoTakerManager);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterStep1Binding.inflate(getLayoutInflater());

        data();
        click();
        onActivityResulting();

        return binding.getRoot();
    }

    private void onActivityResulting() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , result -> presenter.onActivityResult(result.getResultCode(), result.getData()));
    }

    private void data() {
        AppSettings settings = AppController.getInstance().getAppSettingsPreferences().getSettings();
        binding.tvCode.setText(settings.getData().getPhone_code());
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(settings.getData().getPhone_length());
        binding.etEnterPhone.setFilters(filters);
        presenter.getCities();
    }

    private void click() {
        binding.ivEnterImage.setOnClickListener(view -> enterImage());
        binding.btnNext.setOnClickListener(view -> signUp());
    }

    private void signUp() {
        presenter.validInput(binding.etEnterName, binding.etEnterEmail, binding.etEnterAddress
                , binding.etEnterPhone, binding.etEnterPassword, binding.etEnterConfirmPassword
                , binding.spNeighborhood);
    }

    public void enterImage() {
        itemSelectImageDialogFragment = ItemSelectImageDialogFragment.newInstance();
        itemSelectImageDialogFragment.setListener(new ItemSelectImageDialogFragment.Listener() {
            @Override
            public void onGalleryClicked() {
                presenter.setRequestCode(AppContent.REQUEST_STUDIO);
                photoTakerManager.galleryRequestLauncher(getActivity(), launcher);
            }

            @Override
            public void onCameraClicked() {
                presenter.setRequestCode(AppContent.REQUEST_CAMERA);
                photoTakerManager.cameraRequestLauncher(getActivity(), launcher);
            }
        });
        itemSelectImageDialogFragment.show(getChildFragmentManager(), "");
    }

    @Override
    public void setData(ArrayList<City> result) {
        result.add(0, new City(0, ""));
        spinnerAdapter = new SpinnerAdapter(requireContext(), result);
        binding.spNeighborhood.setAdapter(spinnerAdapter);
    }

    @Override
    public void showDialog(String s) {
        WaitDialogFragment.newInstance().show(getChildFragmentManager(), "");
    }

    @Override
    public void hideDialog() {
        WaitDialogFragment.newInstance().dismiss();
    }

    @Override
    public void onSuccess(Bitmap bitmap, String msg) {
        binding.ivEnterImage.setImageBitmap(bitmap);
        presenter.uploadImage(bitmap);
    }

    @Override
    public void onError(String msg) {
    }

    @Override
    public void onFail(String msg) {
        ToolUtil.showLongToast(msg, requireActivity());
    }
}
