package com.AbdUlla.a4_order_station_driver.feature.main.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.AbdUlla.a4_order_station_driver.databinding.ItemOrderItemsBinding;
import com.AbdUlla.a4_order_station_driver.models.OrderStationItem;
import com.AbdUlla.a4_order_station_driver.models.OrderStationItemExtra;
import com.AbdUlla.a4_order_station_driver.utils.AppController;

import java.util.ArrayList;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemHolder> {

    private ArrayList<OrderStationItem> orderItems;

    public OrderItemsAdapter(ArrayList<OrderStationItem> orderItems) {
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public OrderItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderItemHolder(ItemOrderItemsBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemHolder holder, int position) {
        holder.setData(orderItems.get(position));
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public void addItem(OrderStationItem item) {
        orderItems.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public class OrderItemHolder extends RecyclerView.ViewHolder {

        private ItemOrderItemsBinding binding;

        public OrderItemHolder(ItemOrderItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void setData(OrderStationItem testOrderItem) {
            //OrderItemItem item = orderItem.getExtra_items();
            if (AppController.getInstance().getAppSettingsPreferences().getAppLanguage().equals("en")) {
                binding.tvItemName.setText(testOrderItem.getItem_name());
                for (OrderStationItemExtra s : testOrderItem.getExtra_items()) {
                    binding.tvItemDescribe.append(s.getName() + "\n");
                }
            }
            /*} else {
                binding.tvItemName.setText(item.getName_ar());
                for (ExtraItems s : orderItem.getExtra_items()) {
                    binding.tvItemDescribe.append(s.getName_ar() + "\n");
                }
            }*/
            binding.tvItemQnt.setText(testOrderItem.getQty());
            binding.tvItemPrice.setText(testOrderItem.getTotal() + " " + AppController.getInstance()
                    .getAppSettingsPreferences().getUser().getCountry().getCurrency_code());
        }
    }
}
