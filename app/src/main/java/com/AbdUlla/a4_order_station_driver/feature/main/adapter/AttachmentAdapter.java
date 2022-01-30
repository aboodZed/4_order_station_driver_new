package com.AbdUlla.a4_order_station_driver.feature.main.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.AbdUlla.a4_order_station_driver.databinding.ItemAttachmentBinding;
import com.AbdUlla.a4_order_station_driver.models.Attachment;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.ImageFragment;
import com.AbdUlla.a4_order_station_driver.utils.util.APIImageUtil;

import java.util.ArrayList;

public class AttachmentAdapter extends RecyclerView.Adapter<AttachmentAdapter.AttachmentHolder> {

    private Activity activity;
    private ArrayList<Attachment> attachments;
    private FragmentManager fragmentManager;

    public AttachmentAdapter(Activity activity, ArrayList<Attachment> attachments
            , FragmentManager fragmentManager) {
        this.activity = activity;
        this.attachments = attachments;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public AttachmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AttachmentHolder(ItemAttachmentBinding.inflate(LayoutInflater
                .from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AttachmentHolder holder, int position) {
        holder.setData(attachments.get(position));
    }

    @Override
    public int getItemCount() {
        return attachments.size();
    }

    class AttachmentHolder extends RecyclerView.ViewHolder {

        private ItemAttachmentBinding binding;

        public AttachmentHolder(ItemAttachmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            click();
        }

        private void click() {
            binding.ivImage.setOnClickListener(view -> openImage());
        }

        private void setData(Attachment attachment) {
            APIImageUtil.loadImage(activity, binding.pbWaitImage, AppContent.IMAGE_STORAGE_URL
                    + attachment.getName(), binding.ivImage);
        }

        public void openImage() {
            ImageFragment.newInstance(APIImageUtil.getBitmapFromImageView(binding.ivImage))
                    .show(fragmentManager, "");
        }
    }

}
