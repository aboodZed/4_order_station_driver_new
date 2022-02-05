package com.AbdUlla.a4_order_station_driver.feature.order.orderStation.chat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.FragmentChatBinding;
import com.AbdUlla.a4_order_station_driver.models.OrderStation;
import com.AbdUlla.a4_order_station_driver.models.Result;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.Photo.PhotoTakerManager;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.ItemSelectImageDialogFragment;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.WaitDialogFragment;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;
import com.AbdUlla.a4_order_station_driver.feature.order.adapter.ChatAdapter;

import java.util.ArrayList;

public class ChatFragment extends Fragment implements
        RequestListener<Bitmap>, DialogView<Result<OrderStation>> {

    public final static int page = 505;

    public static boolean isOpenChat;

    private FragmentChatBinding binding;

    BaseActivity baseActivity;

    //private DatabaseReference db;
    //private ArrayList<ChatMessage> messageArrayList;
    private OrderStation orderStation;
    private ChatAdapter chatAdapter;

    //Image
    private final ItemSelectImageDialogFragment itemSelectImageDialogFragment
            = ItemSelectImageDialogFragment.newInstance();
    private ActivityResultLauncher<Intent> launcher;
    private PhotoTakerManager photoTakerManager;
    private ChatPresenter presenter;

    public ChatFragment(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public static ChatFragment newInstance(BaseActivity baseActivity) {
        return new ChatFragment(baseActivity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoTakerManager = new PhotoTakerManager(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View v = inflater.inflate(R.layout.fragment_chat, container, false);
        binding = FragmentChatBinding.inflate(getLayoutInflater());
        //getOrderData();
        orderStation = AppController.getInstance().getAppSettingsPreferences().getTrackingOrderStation();
        presenter = new ChatPresenter(this, photoTakerManager, baseActivity, binding, orderStation);
        initRecycleView();
        click();
        presenter.getMessages(chatAdapter, String.valueOf(orderStation.getId()));
        onActivityResulting();
        return binding.getRoot();
    }

    private void onActivityResulting() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> presenter.onActivityResult(result.getResultCode(), result.getData()));
    }

    @Override
    public void onStart() {
        super.onStart();
        isOpenChat = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isOpenChat = false;
    }

    //functions

    private void click() {
        binding.ivUploadMessage.setOnClickListener(view -> presenter.sendMessage(""));
        binding.ivUploadPhoto.setOnClickListener(view -> uploadPhoto());
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
                presenter.setRequestCode(AppContent.REQUEST_CAMERA);
                photoTakerManager.cameraRequestLauncher(requireActivity(), launcher);
            }
        });
        itemSelectImageDialogFragment.show(getChildFragmentManager(), "");
    }

    /*
    private void getOrderData() {
        if (getArguments() != null) {
            WaitDialogFragment.newInstance().show(getChildFragmentManager(), "");

            Order order = (Order) getArguments().get(AppContent.ORDER_OBJECT);
            new APIUtil<Result<TestOrder>>(requireActivity()).getData(AppController
                            .getInstance().getApi().getOrderById(order.getId())
                    , new RequestListener<Result<TestOrder>>() {
                        @Override
                        public void onSuccess(Result<TestOrder> result, String msg) {
                            WaitDialogFragment.newInstance().dismiss();
                            orderStation = result.getData();
                            db = FirebaseDatabase.getInstance().getReference(AppContent.FIREBASE_CHAT_INSTANCE);
                            getMessages();
                            initRecycleView();
                        }

                        @Override
                        public void onError(String msg) {
                            WaitDialogFragment.newInstance().dismiss();
                            ToolUtil.showLongToast(msg, requireActivity());
                        }

                        @Override
                        public void onFail(String msg) {
                            WaitDialogFragment.newInstance().dismiss();
                            ToolUtil.showLongToast(msg, requireActivity());
                        }
                    });
        }
    }
*/
    private void initRecycleView() {
        chatAdapter = new ChatAdapter(getActivity(), new ArrayList<>());
        binding.rvChat.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvChat.setItemAnimator(new DefaultItemAnimator());
        binding.rvChat.setAdapter(chatAdapter);
    }

    //get message


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

    @Override
    public void setData(Result<OrderStation> result) {

    }

    @Override
    public void showDialog(String s) {
        WaitDialogFragment.newInstance().show(getChildFragmentManager(), "");
    }

    @Override
    public void hideDialog() {
        WaitDialogFragment.newInstance().dismiss();
    }
}
