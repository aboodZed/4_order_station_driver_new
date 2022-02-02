package com.AbdUlla.a4_order_station_driver.feature.order.publicOrder.publicOrderView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.AbdUlla.a4_order_station_driver.feature.main.orders.OrdersFragment;
import com.google.firebase.database.annotations.NotNull;
import com.AbdUlla.a4_order_station_driver.databinding.FragmentPublicChatBinding;
import com.AbdUlla.a4_order_station_driver.feature.order.adapter.PublicChatAdapter;
import com.AbdUlla.a4_order_station_driver.models.PublicOrder;
import com.AbdUlla.a4_order_station_driver.models.Result;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.Photo.PhotoTakerManager;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.PublicOrderSettingsDialog;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.ItemSelectImageDialogFragment;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.WaitDialogFragment;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;
import com.AbdUlla.a4_order_station_driver.utils.location.tracking.OrderGPSTracking;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;

public class PublicOrderViewFragment extends Fragment implements
        RequestListener<Bitmap>, DialogView<Result<PublicOrder>> {

    public final static int page = 504;

    private PublicOrder publicOrder;

    private FragmentPublicChatBinding binding;

    private PublicChatAdapter publicChatAdapter;
    private final ItemSelectImageDialogFragment itemSelectImageDialogFragment
            = ItemSelectImageDialogFragment.newInstance();
    private PhotoTakerManager photoTakerManager;
    private PublicOrderViewPresenter presenter;

    private PublicOrderSettingsDialog billDialog;

    private BaseActivity baseActivity;
    public static double billPrice = 0;
    public static boolean isOpenPublicChat = false;

    private ActivityResultLauncher<Intent> launcher;

    public PublicOrderViewFragment(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public static PublicOrderViewFragment newInstance(BaseActivity baseActivity) {

        return new PublicOrderViewFragment(baseActivity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoTakerManager = new PhotoTakerManager(this);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPublicChatBinding.inflate(getLayoutInflater());
        photoTakerManager = new PhotoTakerManager(this);
        //if (getArguments() != null) {
        presenter = new PublicOrderViewPresenter(baseActivity, binding, this, photoTakerManager);

        //presenter.getData(publicOrder);
        initRecycleView();
        data();
        presenter.getMessages(publicChatAdapter, String.valueOf(publicOrder.getId()));
        click();
        onActivityResulting();
        //  }
        return binding.getRoot();
    }

    private void onActivityResulting() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> presenter.onActivityResult(result.getResultCode(), result.getData()));
    }

    private void click() {
        binding.ivUploadMessage.setOnClickListener(view -> presenter.sendMessage(""));
        binding.ivUploadPhoto.setOnClickListener(view -> uploadPhoto());
        binding.tvMore.setOnClickListener(v -> openBillDialog());

        /*binding.ivTracking.setOnClickListener(view -> showLocation());
        binding.ivMore.setOnClickListener(view -> {
            presenter.getData(publicOrder);
            openBillDialog = true;
        });*/
    }

    @Override
    public void onStart() {
        super.onStart();
        //OrdersFragment.viewPagerPage = OrderPublicFragment.viewPagerPage;
        //WalletFragment.viewPagerPage = PublicWalletFragment.viewPagerPage;
        isOpenPublicChat = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isOpenPublicChat = false;
    }

    public void uploadPhoto() {
        itemSelectImageDialogFragment.setListener(new ItemSelectImageDialogFragment.Listener() {
            @Override
            public void onGalleryClicked() {
                presenter.setRequestCode(AppContent.REQUEST_STUDIO);
                photoTakerManager.galleryRequestLauncher(requireActivity(), launcher);
            }

            @Override
            public void onCameraClicked() {
                presenter.setRequestCode(AppContent.REQUEST_STUDIO);
                photoTakerManager.cameraRequestLauncher(requireActivity(), launcher);
            }
        });
        itemSelectImageDialogFragment.show(getChildFragmentManager(), "");
    }

    private void openBillDialog() {
        billDialog = PublicOrderSettingsDialog.newInstance(publicOrder);
        billDialog.show(getChildFragmentManager(), "");
        billDialog.setListener(status -> {
            publicOrder.setStatus(status);
            AppController.getInstance().getAppSettingsPreferences()
                    .setTrackingPublicOrder(publicOrder);
            billDialog.dismiss();
            data();
            baseActivity.navigate(OrdersFragment.page);
            //presenter.getData(publicOrder);
        });
    }

    //functions
    private void data() {
//        binding.tvOrderId.setText((getString(R.string.order) + "#" + testOrder.getInvoice_number()));
        publicOrder = AppController.getInstance().getAppSettingsPreferences().getTrackingPublicOrder();

        binding.tvOrderDetails.setText(publicOrder.getNote());
        setPrice();

        if (publicOrder.getStatus().equals(AppContent.DELIVERED_STATUS)
                || publicOrder.getStatus().equals(AppContent.CANCELLED_STATUS)) {
            binding.llBottom.setVisibility(View.GONE);
            binding.tvMore.setVisibility(View.GONE);
            //OrderGPSTracking.newInstance(baseActivity).removeUpdates();
        } else if (publicOrder.getStatus().equals(AppContent.CUSTOMER_WAITING)) {
            binding.tvMore.setVisibility(View.GONE);
        }
        /*String currency = AppController.getInstance().getAppSettingsPreferences().getUser()
                .getCountry().getCurrency_code();
        binding.tvOrderDetails.setText(publicOrder.get());
        binding..setText((DecimalFormatterManager.getFormatterInstance()
                .format(Double.parseDouble(publicOrder.getDelivery_cost())) + " " + currency));
        binding.tvTaxPrice.setText((DecimalFormatterManager.getFormatterInstance()
                .format(Double.parseDouble(testOrder.getTax())) + " " + currency));
        if (testOrder.getStatus().equals(AppContent.DELIVERED_STATUS)
                || testOrder.getStatus().equals(AppContent.CANCELLED_STATUS)) {
            binding.ivMore.setVisibility(View.GONE);
            binding.ivTracking.setVisibility(View.GONE);
            binding.llBottom.setVisibility(View.GONE);
        }*/
    }

    private void setPrice() {
        if (publicOrder.getPurchase_invoice_value() != null) {
            billPrice = Double.parseDouble(publicOrder.getPurchase_invoice_value());
        } else {
            billPrice = 0;
        }
        Log.e("billPrice:", billPrice + "");

    }


    private void initRecycleView() {
        publicChatAdapter = new PublicChatAdapter(getActivity(), getChildFragmentManager());
        binding.rvChat.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvChat.setItemAnimator(new DefaultItemAnimator());
        binding.rvChat.setAdapter(publicChatAdapter);

        /*if (PermissionUtil.isPermissionGranted(MediaStore.ACTION_IMAGE_CAPTURE, getContext()))
            PermissionUtil.requestPermission(getActivity(), Manifest.permission.CAMERA
                    , AppContent.REQUEST_PERMISSIONS_R_W_STORAGE_CAMERA);*/
    }

    @Override
    public void setData(Result<PublicOrder> result) {
        /*testOrder = publicOrderObject.getPublicOrder();
        Log.e("orderdata", testOrder.toString());
        data();

        if (testOrder.getStatus().equals(AppContent.DELIVERED_STATUS)
                || testOrder.getStatus().equals(AppContent.CANCELLED_STATUS)) {
            OrderGPSTracking.newInstance(baseActivity).removeUpdates();
        }
        if (openBillDialog) {
            openBillDialog();
            openBillDialog = false;
        }*/
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
        presenter.uploadImage(photoTakerManager.getCurrentPhotoUri());
    }

    @Override
    public void onError(String msg) {
        ToolUtil.showLongToast(msg, requireActivity());
    }

    @Override
    public void onFail(String msg) {
        ToolUtil.showLongToast(msg, requireActivity());
    }

}
