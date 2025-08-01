package com.AbdUlla.a4_order_station_driver.feature.main.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.ItemOrderBinding;
import com.AbdUlla.a4_order_station_driver.feature.order.orderStation.orderStationView.OrderStationViewFragment;
import com.AbdUlla.a4_order_station_driver.models.OrderStation;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.util.NavigateUtil;

import java.util.ArrayList;

public class OrderStationAdapter extends RecyclerView.Adapter<OrderStationAdapter.OrdersHolder> {

    private ArrayList<OrderStation> orders = new ArrayList<>();
    private Activity baseActivity;

    public OrderStationAdapter(Activity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @NonNull
    @Override
    public OrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrdersHolder(ItemOrderBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersHolder holder, int position) {
        holder.setData(orders.get(position), baseActivity);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void addAll(ArrayList<OrderStation> arrayList) {
        if (arrayList != null) {
            orders.addAll(arrayList);
            notifyDataSetChanged();
        }
    }

    public void clear(){
        orders.clear();
        notifyDataSetChanged();
    }

    public class OrdersHolder extends RecyclerView.ViewHolder {

        private ItemOrderBinding binding;

        private OrderStation order;
        private Activity baseActivity;

        public OrdersHolder(ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            click();
        }

        private void click() {
            binding.rootLayout.setOnClickListener(view -> setId());
        }

        public void setId() {
            new NavigateUtil().openOrderStation(baseActivity, order, OrderStationViewFragment.page, true);
        }

        @SuppressLint("SetTextI18n")
        public void setData(OrderStation order, Activity baseActivity) {
            this.baseActivity = baseActivity;
            this.order = order;
            order.setType(AppContent.TYPE_ORDER_4STATION);
            binding.tvTime.setText(order.getOrder_date());
            binding.tvType.setText(order.getType());
            //binding.tvDate.setText();
            binding.tvNumItems.setText((order.getOrderItems().size() + " " + baseActivity.getString(R.string.items)));
            binding.tvPaymentWay.setText(order.getPayment_type());
            binding.tvReceiverPoint.setText(order.getType_of_receive());
            if (order.getStatus().equals(AppContent.DELIVERED_STATUS)) {
                binding.tvOrderState.setBackgroundResource(R.drawable.green_button);
            } else if (order.getStatus().equals(AppContent.CANCEL_STATUS)) {
                binding.tvOrderState.setBackgroundResource(R.drawable.red_button);
            }
            binding.tvOrderState.setText(order.getStatus_translation());
            binding.tvPrice.setText(order.getTotal());
            binding.tvCurrency.setText(AppController.getInstance().getAppSettingsPreferences()
                    .getUser().getCountry().getCurrency_code());
            binding.tvReceiverName.setText(order.getReceiver_name());
            binding.tvReceiverAddress.setText(order.getReceiver_address());

            /*if (AppController.getInstance().getAppSettingsPreferences()
                    .getAppLanguage().equals(AppLanguageUtil.English)) {*/
                binding.tvCoName.setText(order.getStore().getName());
                binding.tvCoAddress.setText(order.getStore().getAddress());
           /* } else {
                binding.tvCoName.setText(order.getShop().getName_ar());
                binding.tvCoAddress.setText(order.getShop().getAddress_ar());
            }*/
        }
    }
}
