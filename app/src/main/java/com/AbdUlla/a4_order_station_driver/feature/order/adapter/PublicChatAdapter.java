package com.AbdUlla.a4_order_station_driver.feature.order.adapter;

import static com.AbdUlla.a4_order_station_driver.utils.AppContent.IMAGE_STORAGE_URL;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.AbdUlla.a4_order_station_driver.databinding.ItemLeftPublicChatBinding;
import com.AbdUlla.a4_order_station_driver.databinding.ItemRightPublicChatBinding;
import com.AbdUlla.a4_order_station_driver.models.ChatMessage;
import com.AbdUlla.a4_order_station_driver.utils.util.APIImageUtil;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.ImageFragment;

import java.util.ArrayList;

public class PublicChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ChatMessage> publicChatMessages;
    private Activity activity;
    private FragmentManager fragmentManager;

    public PublicChatAdapter(Activity activity, FragmentManager fragmentManager) {
        this.publicChatMessages = new ArrayList<>();
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public int getItemViewType(int position) {
        if (publicChatMessages.get(position).getSender_id() == AppController.getInstance()
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
            return new DriverHolder(ItemRightPublicChatBinding.inflate(LayoutInflater
                    .from(parent.getContext()), parent, false));
        } else {
            return new ReceiverHolder(ItemLeftPublicChatBinding.inflate(LayoutInflater
                    .from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DriverHolder) {
            ((DriverHolder) holder).setData(publicChatMessages.get(position));
        } else if (holder instanceof ReceiverHolder) {
            ((ReceiverHolder) holder).setData(publicChatMessages.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return publicChatMessages.size();
    }

    public void addItem(ChatMessage publicChatMessage) {
        publicChatMessages.add(publicChatMessage);
        notifyItemInserted(getItemCount() - 1);
    }

    public class DriverHolder extends RecyclerView.ViewHolder {

        private ItemRightPublicChatBinding binding;

        public DriverHolder(ItemRightPublicChatBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            click();
        }

        private void click() {
            binding.image.setOnClickListener(view -> open());
        }

        private void setData(ChatMessage data) {
            binding.message.setText(data.getText());
            //APIImageUtil.loadImage(activity, binding.progressBar, IMAGE_STORAGE_URL + data.getSender_avatar_url(), binding.senderImage);
            if (data.getImageUrl() != null) {
                if (!data.getImageUrl().isEmpty()) {
                    APIImageUtil.loadImage(activity, binding.progressBar, data.getImageUrl(), binding.image);
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.image.setVisibility(View.VISIBLE);
                }else {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.image.setVisibility(View.GONE);
                    binding.message.setVisibility(View.VISIBLE);
                    binding.message.setText(binding.message.getText());
                }
            } else {
                binding.progressBar.setVisibility(View.GONE);
                binding.image.setVisibility(View.GONE);
                binding.message.setVisibility(View.VISIBLE);
                binding.message.setText(binding.message.getText());
            }
            binding.time.setText(ToolUtil.getTime((long) data.getTime()));
        }

        public void open() {
            ImageFragment.newInstance(APIImageUtil.getBitmapFromImageView(binding.image)).show(fragmentManager, "");
        }
    }

    public class ReceiverHolder extends RecyclerView.ViewHolder {

        private ItemLeftPublicChatBinding binding;

        private ChatMessage publicChatMessage;

        public ReceiverHolder(ItemLeftPublicChatBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            click();
        }

        private void click() {
            binding.image.setOnClickListener(view -> open());
        }

        private void setData(ChatMessage data) {
            publicChatMessage = data;
            binding.message.setText(data.getText());
            APIImageUtil.loadImage(activity, binding.progressBar, IMAGE_STORAGE_URL + data.getSender_avatar_url(), binding.ivUserImage);
            if (data.getImageUrl() != null) {
                if (!data.getImageUrl().isEmpty()) {
                    APIImageUtil.loadImage(activity, binding.progressBar, data.getImageUrl(), binding.image);
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.image.setVisibility(View.VISIBLE);
                }else {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.image.setVisibility(View.GONE);
                    binding.message.setVisibility(View.VISIBLE);
                    binding.message.setText(binding.message.getText());
                }
            } else {
                binding.progressBar.setVisibility(View.GONE);
                binding.image.setVisibility(View.GONE);
                binding.message.setVisibility(View.VISIBLE);
                binding.message.setText(binding.message.getText());
            }
            binding.time.setText(ToolUtil.getTime((long) data.getTime()));
        }

        public void open() {
            ImageFragment.newInstance(APIImageUtil.getBitmapFromImageView(binding.image)).show(fragmentManager, "");
        }
    }
}
