package com.AbdUlla.a4_order_station_driver.feature.order.orderStation.chat;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.FragmentChatBinding;
import com.AbdUlla.a4_order_station_driver.feature.order.adapter.ChatAdapter;
import com.AbdUlla.a4_order_station_driver.models.ChatMessage;
import com.AbdUlla.a4_order_station_driver.models.OrderStation;
import com.AbdUlla.a4_order_station_driver.models.Result;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.Photo.PhotoTakerManager;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;
import com.AbdUlla.a4_order_station_driver.utils.util.NotificationUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;

import java.util.UUID;

public class ChatPresenter {

    private int requestCode;

    private DialogView<Result<OrderStation>> dialogView;
    private PhotoTakerManager photoTakerManager;
    private BaseActivity baseActivity;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    private DatabaseReference db;
    private FragmentChatBinding binding;
    private OrderStation orderStation;

    public ChatPresenter(DialogView<Result<OrderStation>> dialogView, PhotoTakerManager photoTakerManager
            , BaseActivity baseActivity, FragmentChatBinding binding, OrderStation orderStation) {
        this.dialogView = dialogView;
        this.photoTakerManager = photoTakerManager;
        this.baseActivity = baseActivity;
        this.binding = binding;
        this.orderStation = orderStation;
        db = FirebaseDatabase.getInstance().getReference(AppContent.FIREBASE_CHAT_INSTANCE);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
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

    public void setRequestCode(int requestStudio) {
        this.requestCode = requestStudio;
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
                        dialogView.hideDialog();
                    })
                    .addOnProgressListener(taskSnapshot -> {

                    });
        }
    }

    public void getMessages(ChatAdapter chatAdapter, String id) {
        db.child(id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                chatAdapter.addItem(dataSnapshot.getValue(ChatMessage.class));
                binding.rvChat.scrollToPosition(chatAdapter.getItemCount() - 1);
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
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setText(binding.etMessage.getText().toString());
                chatMessage.setSender_id(AppController.getInstance().getAppSettingsPreferences().getUser().getId());
                chatMessage.setSender_name(AppController.getInstance().getAppSettingsPreferences().getUser().getName());
                chatMessage.setImageUrl(image);
                chatMessage.setSender_avatar_url(AppController.getInstance().getAppSettingsPreferences().getUser().getAvatar_url());
                chatMessage.setTime(System.currentTimeMillis() / 1000);
                String key = db.push().getKey();
                db.child(orderStation.getId() + "").child(key).setValue(chatMessage);
                ToolUtil.hideSoftKeyboard(baseActivity, binding.etMessage);
                binding.etMessage.setText("");
                NotificationUtil.sendMessageNotification(baseActivity, orderStation.getInvoice_number()
                        , orderStation.getId() + "", orderStation.getCustomer_id()
                        , AppContent.TYPE_ORDER_4STATION);
            }
        } else {
            ToolUtil.showLongToast(baseActivity.getString(R.string.no_connection), baseActivity);
        }
    }
}
