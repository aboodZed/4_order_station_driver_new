package com.AbdUlla.a4_order_station_driver.feature.order.adapter;

import static com.AbdUlla.a4_order_station_driver.utils.AppContent.IMAGE_STORAGE_URL;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.AbdUlla.a4_order_station_driver.databinding.ItemChatLeftBinding;
import com.AbdUlla.a4_order_station_driver.databinding.ItemChatRightBinding;
import com.AbdUlla.a4_order_station_driver.models.ChatMessage;
import com.AbdUlla.a4_order_station_driver.utils.util.APIImageUtil;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ChatMessage> chatMessages;
    private DateFormat dateFormat;
    private Activity activity;

    public ChatAdapter(Activity activity, ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
        this.activity = activity;
        dateFormat = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT);
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessages.get(position).getSender_id() == AppController.getInstance()
                .getAppSettingsPreferences().getUser().getId()) {
            return 1;
        } else {
            return 2;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new DriverHolder(ItemChatRightBinding.inflate(LayoutInflater
                    .from(parent.getContext()), parent, false));
        } else {
            return new ReceiverHolder(ItemChatLeftBinding.inflate(LayoutInflater
                    .from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DriverHolder) {
            ((DriverHolder) holder).setData(chatMessages.get(position));
        } else if (holder instanceof ReceiverHolder) {
            ((ReceiverHolder) holder).setData(chatMessages.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public void addItem(ChatMessage chatMessage) {
        chatMessages.add(chatMessage);
        notifyItemInserted(getItemCount() - 1);
    }

    public class DriverHolder extends RecyclerView.ViewHolder {

        private ItemChatRightBinding binding;

        public DriverHolder(ItemChatRightBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void setData(ChatMessage data) {
            if (data.getImageUrl() != null) {
                if (!data.getImageUrl().isEmpty()) {
                    APIImageUtil.loadImage(activity, binding.progressBar, data.getImageUrl(), binding.ivImage);
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.ivImage.setVisibility(View.VISIBLE);
                } else {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.ivImage.setVisibility(View.GONE);
                    binding.tvMessageText.setVisibility(View.VISIBLE);
                    binding.tvMessageText.setText(data.getText());
                }
            } else {
                binding.progressBar.setVisibility(View.GONE);
                binding.ivImage.setVisibility(View.GONE);
                binding.tvMessageText.setVisibility(View.VISIBLE);
                binding.tvMessageText.setText(data.getText());
            }
            try {
                binding.tvMessageTime.setText(ToolUtil.getTime((long) data.getTime()));
            } catch (Exception e) {
                Log.e("error", "" + e.getMessage());
            }
        }
    }

    public class ReceiverHolder extends RecyclerView.ViewHolder {

        private ItemChatLeftBinding binding;

        public ReceiverHolder(ItemChatLeftBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void setData(ChatMessage data) {
            if (data.getImageUrl() != null) {
                if (!data.getImageUrl().isEmpty()) {
                    APIImageUtil.loadImage(activity, binding.progressBar, data.getImageUrl(), binding.ivImage);
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.ivImage.setVisibility(View.VISIBLE);
                } else {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.ivImage.setVisibility(View.GONE);
                    binding.tvMessageText.setVisibility(View.VISIBLE);
                    binding.tvMessageText.setText(data.getText());
                }
            } else {
                binding.progressBar.setVisibility(View.GONE);
                binding.ivImage.setVisibility(View.GONE);
                binding.tvMessageText.setVisibility(View.VISIBLE);
                binding.tvMessageText.setText(data.getText());
            }

//            try {
            binding.tvMessageTime.setText(ToolUtil.getTime((long) data.getTime()));
//            } catch (Exception e) {
//                Log.e("error", "" + e.getMessage());
//            }
            APIImageUtil.loadImage(activity, binding.pbWaitAvatar
                    , data.getSender_avatar_url(), binding.ivUserImage);
        }
    }
}
