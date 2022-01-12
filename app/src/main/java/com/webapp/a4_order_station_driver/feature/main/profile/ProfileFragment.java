package com.webapp.a4_order_station_driver.feature.main.profile;

import static com.webapp.a4_order_station_driver.utils.AppContent.IMAGE_STORAGE_URL;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.webapp.a4_order_station_driver.databinding.FragmentProfileBinding;
import com.webapp.a4_order_station_driver.feature.data.DataActivity;
import com.webapp.a4_order_station_driver.feature.data.changePassword.ChangePasswordFragment;
import com.webapp.a4_order_station_driver.feature.data.contact.ContactFragment;
import com.webapp.a4_order_station_driver.models.User;
import com.webapp.a4_order_station_driver.utils.Photo.PhotoTakerManager;
import com.webapp.a4_order_station_driver.utils.dialogs.ItemSelectImageDialogFragment;
import com.webapp.a4_order_station_driver.utils.listeners.RequestListener;
import com.webapp.a4_order_station_driver.utils.util.APIImageUtil;
import com.webapp.a4_order_station_driver.utils.AppContent;
import com.webapp.a4_order_station_driver.utils.AppController;
import com.webapp.a4_order_station_driver.utils.util.NavigateUtil;
import com.webapp.a4_order_station_driver.utils.dialogs.WaitDialogFragment;
import com.webapp.a4_order_station_driver.utils.language.BaseActivity;
import com.webapp.a4_order_station_driver.utils.listeners.DialogView;
import com.webapp.a4_order_station_driver.utils.util.ToolUtil;

import java.util.HashMap;

public class ProfileFragment extends Fragment implements DialogView<String>, RequestListener<Bitmap> {

    public static final int page = 204;

    private FragmentProfileBinding binding;

    private ItemSelectImageDialogFragment itemSelectImageDialogFragment;
    private PhotoTakerManager photoTakerManager;

    private static BaseActivity baseActivity;
    private ProfilePresenter presenter;
    private HashMap<String, String> map = new HashMap<>();
    private int process_image;

    private final int VEHICLE = 0;
    private final int VEHICLE_LICENSE = 1;
    private final int VEHICLE_INSURANCE = 2;
    private final int IDENTITY = 3;
    private final int YOUR_LICENSE = 4;
    private final int AVATAR = 5;

    private ActivityResultLauncher<Intent> launcher;

    public static ProfileFragment newInstance(BaseActivity baseActivity) {
        ProfileFragment.baseActivity = baseActivity;
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        photoTakerManager = new PhotoTakerManager(this);
        presenter = new ProfilePresenter(baseActivity, this, photoTakerManager);
        data();
        click();
        onActivityResulting();
        return binding.getRoot();
    }

    private void click() {
        binding.tvChangePassword.setOnClickListener(view -> new NavigateUtil().activityIntentWithPage(
                baseActivity, DataActivity.class, true, ChangePasswordFragment.page));
        //photos
        binding.ivDriverAvatar.setOnClickListener(view -> {
            process_image = AVATAR;
            request(AppContent.REQUEST_IMAGE_AVATAR_UPLOAD, AppContent.REQUEST_IMAGE_AVATAR_CAMERA);
        });
        binding.ivIdentity.setOnClickListener(view -> {
            process_image = IDENTITY;
            request(AppContent.REQUEST_IMAGE_IDENTITY_UPLOAD, AppContent.REQUEST_IMAGE_IDENTITY_CAMERA);
        });
        binding.ivVehicle.setOnClickListener(view -> {
            process_image = VEHICLE;
            request(AppContent.REQUEST_IMAGE_VEHICLE_UPLOAD, AppContent.REQUEST_IMAGE_VEHICLE_CAMERA);
        });
        binding.ivVehicleInsurance.setOnClickListener(view -> {
            process_image = VEHICLE_INSURANCE;
            request(AppContent.REQUEST_IMAGE_VEHICLE_INSURANCE_UPLOAD, AppContent.REQUEST_IMAGE_VEHICLE_INSURANCE_CAMERA);
        });
        binding.ivVehicleLicense.setOnClickListener(view -> {
            process_image = VEHICLE_LICENSE;
            request(AppContent.REQUEST_IMAGE_VEHICLE_LICENSE_UPLOAD, AppContent.REQUEST_IMAGE_VEHICLE_LICENSE_CAMERA);
        });
        binding.ivYourLicense.setOnClickListener(view -> {
            process_image = YOUR_LICENSE;
            request(AppContent.REQUEST_IMAGE_YOUR_LICENSE_UPLOAD, AppContent.REQUEST_IMAGE_YOUR_LICENSE_CAMERA);
        });

        binding.btnSave.setOnClickListener(view -> presenter.validInput(binding, map));
        /*binding.tvEdit.setOnClickListener(view -> new NavigateUtil().activityIntentWithPage(
                activity, DataActivity.class, true, EditProfileFragment.page));*/
    }

    private void request(int request_upload, int request_camera) {
        itemSelectImageDialogFragment = ItemSelectImageDialogFragment.newInstance();
        itemSelectImageDialogFragment.setListener(new ItemSelectImageDialogFragment.Listener() {
            @Override
            public void onGalleryClicked() {
                presenter.setRequestCode(request_upload);
                photoTakerManager.galleryRequestLauncher(getActivity(), launcher);
            }

            @Override
            public void onCameraClicked() {
                presenter.setRequestCode(request_camera);
                photoTakerManager.cameraRequestLauncher(getActivity(), launcher);
            }
        });
        itemSelectImageDialogFragment.show(getChildFragmentManager(), "");
    }

    public void contact() {
        new NavigateUtil().activityIntentWithPage(requireActivity(), DataActivity.class, true, ContactFragment.page);
    }


    //functions
    private void data() {
        presenter.getCities(binding);
        User user = AppController.getInstance().getAppSettingsPreferences().getUser();
        Log.e("usercountryid", user.toString() + "");
        Log.e("id", user.getId() + "");
        APIImageUtil.loadImage(getContext(), binding.pbWait, IMAGE_STORAGE_URL
                + user.getAvatar_url(), binding.ivDriverAvatar);
        map.put("avatar", user.getAvatar_url());
        APIImageUtil.loadImage(getContext(), binding.bpWaitDriverIdentity, IMAGE_STORAGE_URL
                + user.getId_pic_url(), binding.ivIdentity);
        map.put("id_pic", user.getId_pic_url());
        APIImageUtil.loadImage(getContext(), binding.bpWaitDriverLicense, IMAGE_STORAGE_URL
                + user.getDriver_license_url(), binding.ivYourLicense);
        map.put("drive_license", user.getDriver_license_url());
        APIImageUtil.loadImage(getContext(), binding.bpWaitVehicle, IMAGE_STORAGE_URL
                + user.getVehicle_pic_url(), binding.ivVehicle);
        map.put("vehicle_pic", user.getVehicle_pic_url());
        APIImageUtil.loadImage(getContext(), binding.bpWaitVehicleLicense, IMAGE_STORAGE_URL
                + user.getVehicle_license_url(), binding.ivVehicleLicense);
        map.put("vehicle_license", user.getVehicle_license_url());
        APIImageUtil.loadImage(getContext(), binding.bpWaitVehicleInsurance, IMAGE_STORAGE_URL
                + user.getInsurance_license_url(), binding.ivVehicleInsurance);
        map.put("insurance_license", user.getInsurance_license_url());

        binding.tvDriverName.setText(user.getName());
        binding.tvUserName.setText(user.getName());
        binding.tvPhone.setText(user.getMobile());
        binding.tvEmail.setText(user.getEmail());
        binding.tvAddress.setText(user.getAddress());
        //binding.tvArea.setText(user.getCity().getName());
        //binding.rbUser.setRating(user.getRate());
    }

    private void onActivityResulting() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> presenter.onActivityResult(result.getResultCode(), result.getData()));
    }

    @Override
    public void setData(String fileName) {
        switch (process_image) {
            case AVATAR:
                map.put("avatar", fileName);
                break;
            case VEHICLE:
                map.put("vehicle_pic", fileName);
                break;
            case VEHICLE_LICENSE:
                map.put("vehicle_license", fileName);
                break;
            case VEHICLE_INSURANCE:
                map.put("insurance_license", fileName);
                break;
            case IDENTITY:
                map.put("id_pic", fileName);
                break;
            case YOUR_LICENSE:
                map.put("drive_license", fileName);
                break;
        }
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
        APIImageUtil.uploadImage(baseActivity, this, bitmap);
        switch (process_image) {
            case AVATAR:
                binding.ivDriverAvatar.setImageBitmap(bitmap);
                break;
            case VEHICLE:
                binding.ivVehicle.setImageBitmap(bitmap);
                break;
            case VEHICLE_LICENSE:
                binding.ivVehicleLicense.setImageBitmap(bitmap);
                break;
            case VEHICLE_INSURANCE:
                binding.ivVehicleInsurance.setImageBitmap(bitmap);
                break;
            case IDENTITY:
                binding.ivIdentity.setImageBitmap(bitmap);
                break;
            case YOUR_LICENSE:
                binding.ivYourLicense.setImageBitmap(bitmap);
                break;
        }
    }

    @Override
    public void onError(String msg) {
        ToolUtil.showLongToast(msg, baseActivity);
    }

    @Override
    public void onFail(String msg) {
        ToolUtil.showLongToast(msg, baseActivity);
    }
}
