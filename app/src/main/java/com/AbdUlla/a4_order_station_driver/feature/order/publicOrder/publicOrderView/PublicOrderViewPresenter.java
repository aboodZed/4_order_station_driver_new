package com.AbdUlla.a4_order_station_driver.feature.order.publicOrder.publicOrderView;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.AbdUlla.a4_order_station_driver.models.ChatMessage;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.FragmentPublicChatBinding;
import com.AbdUlla.a4_order_station_driver.feature.order.adapter.PublicChatAdapter;
import com.AbdUlla.a4_order_station_driver.models.Order;
import com.AbdUlla.a4_order_station_driver.models.PublicOrder;
import com.AbdUlla.a4_order_station_driver.models.Result;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.util.NotificationUtil;
import com.AbdUlla.a4_order_station_driver.utils.Photo.PhotoTakerManager;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class PublicOrderViewPresenter {

    private BaseActivity baseActivity;
    private DialogView<Result<PublicOrder>> dialogView;
    private PhotoTakerManager photoTakerManager;
    private StorageReference storageReference;
    private DatabaseReference db;
    private PublicOrder publicOrder;
    private FragmentPublicChatBinding binding;

    private int requestCode;

    public PublicOrderViewPresenter(BaseActivity baseActivity, FragmentPublicChatBinding binding
            , DialogView<Result<PublicOrder>> dialogView, PhotoTakerManager photoTakerManager) {
        this.baseActivity = baseActivity;
        this.binding = binding;
        this.dialogView = dialogView;
        this.photoTakerManager = photoTakerManager;
        publicOrder = AppController.getInstance().getAppSettingsPreferences().getTrackingPublicOrder();
        db = FirebaseDatabase.getInstance().getReference(AppContent.FIREBASE_PUBLIC_STORE_CHAT_INSTANCE);
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public void getData(Order order) {
        dialogView.showDialog("");
        new APIUtil<Result<PublicOrder>>(baseActivity).getData(AppController.getInstance()
                .getApi().getPublicOrder(order.getId()), new RequestListener<Result<PublicOrder>>() {
            @Override
            public void onSuccess(Result<PublicOrder> result, String msg) {
                publicOrder = result.getData();
                dialogView.setData(result);
                dialogView.hideDialog();
            }

            @Override
            public void onError(String msg) {
                ToolUtil.showLongToast(msg, baseActivity);
                dialogView.hideDialog();
            }

            @Override
            public void onFail(String msg) {
                ToolUtil.showLongToast(msg, baseActivity);
                dialogView.hideDialog();
            }
        });
    }

    //get message
    public void getMessages(PublicChatAdapter publicChatAdapter, String id) {
        db.child(id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                publicChatAdapter.addItem(dataSnapshot.getValue(ChatMessage.class));
                binding.rvChat.scrollToPosition(publicChatAdapter.getItemCount() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //send message
    public void sendMessage(String image) {
        if (ToolUtil.checkTheInternet()) {
            if (!binding.etMessage.getText().toString().equals("") || !image.isEmpty()) {
                ChatMessage publicStoreMessage = new ChatMessage();
                publicStoreMessage.setText(binding.etMessage.getText().toString());
                publicStoreMessage.setSender_name(AppController.getInstance().getAppSettingsPreferences().getUser().getName());
                publicStoreMessage.setImageUrl(image);
                publicStoreMessage.setSender_id(AppController.getInstance().getAppSettingsPreferences().getUser().getId());
                publicStoreMessage.setSender_avatar_url(AppController.getInstance().getAppSettingsPreferences().getUser().getAvatar_url());
                publicStoreMessage.setTime(System.currentTimeMillis() / 1000);
                publicStoreMessage.setIsDriver(true);
                String key = db.push().getKey();
                db.child(publicOrder.getId() + "").child(key).setValue(publicStoreMessage);
                NotificationUtil.sendMessageNotification(baseActivity, publicOrder.getInvoice_number()
                        , publicOrder.getId() + "", publicOrder.getCustomer().getId() + ""
                        , AppContent.TYPE_ORDER_PUBLIC);
            }
            ToolUtil.hideSoftKeyboard(baseActivity, binding.etMessage);
            binding.etMessage.setText("");
        } else {
            ToolUtil.showLongToast(baseActivity.getString(R.string.no_connection), baseActivity);
        }
    }

    public void uploadImage(Uri filePath) {
        if (filePath != null) {
            dialogView.showDialog("");
            String avatarPath = "images/" + UUID.randomUUID().toString();
            StorageReference ref = storageReference.child(avatarPath);
            ref.putFile(filePath)
                    .addOnSuccessListener(taskSnapshot -> {
                        dialogView.hideDialog();
                        ref.getDownloadUrl().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                sendMessage(String.valueOf(task.getResult()));
                            } else {
                                ToolUtil.showLongToast(baseActivity.getString(R.string.error), baseActivity);
                            }
                        });
                    })
                    .addOnFailureListener(e -> {
                        ToolUtil.showLongToast(e.getLocalizedMessage(), baseActivity);
                        dialogView.hideDialog();
                    })
                    .addOnProgressListener(taskSnapshot -> {

                    });
        }
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public void onActivityResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppContent.REQUEST_STUDIO:
                    photoTakerManager.processGalleryPhoto(baseActivity, data);
                    break;
                case AppContent.REQUEST_CAMERA:
                    photoTakerManager.processCameraPhoto(baseActivity);
                    break;
            }
        }
    }
}
