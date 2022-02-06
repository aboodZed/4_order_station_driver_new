package com.AbdUlla.a4_order_station_driver.feature.main.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.ItemOrderBinding;
import com.AbdUlla.a4_order_station_driver.feature.order.publicOrder.publicOrderView.PublicOrderViewFragment;
import com.AbdUlla.a4_order_station_driver.models.PublicOrder;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.formatter.DecimalFormatterManager;
import com.AbdUlla.a4_order_station_driver.utils.util.NavigateUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;

import java.util.ArrayList;

public class PublicOrderAdapter extends RecyclerView.Adapter<PublicOrderAdapter.PubicOrderHolder> {

    private ArrayList<PublicOrder> publicOrders = new ArrayList<>();
    private Activity baseActivity;

    public PublicOrderAdapter(Activity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @NonNull
    @Override
    public PubicOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PubicOrderHolder(ItemOrderBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PubicOrderHolder holder, int position) {
        holder.setData(publicOrders.get(position));
    }

    @Override
    public int getItemCount() {
        return publicOrders.size();
    }

    public void addAll(ArrayList<PublicOrder> arrayList) {
        if (arrayList != null) {
            publicOrders.addAll(arrayList);
            notifyDataSetChanged();
        }
    }

    class PubicOrderHolder extends RecyclerView.ViewHolder {

        private ItemOrderBinding binding;

        private PublicOrder publicOrder;

        public PubicOrderHolder(ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            click();
        }

        private void click() {
            binding.rootLayout.setOnClickListener(view -> openChat());
        }

        public void openChat() {
            new NavigateUtil().openPublicOrder(baseActivity, publicOrder
                    , PublicOrderViewFragment.page, true);
        }

        @SuppressLint("SetTextI18n")
        public void setData(PublicOrder publicOrder) {
            this.publicOrder = publicOrder;
            publicOrder.setType(AppContent.TYPE_ORDER_PUBLIC);
            binding.tvType.setText(publicOrder.getType() + " " + baseActivity.getString(R.string.order));
            //String[] s = publicOrder.getCreated_timestamp().split(" ");
            //binding.tvDate.setText((s[0] + "\n" + s[1]));
            binding.tvTime.setText(ToolUtil.getTime(publicOrder.getCreated_timestamp()) + " | " +
                    ToolUtil.getDate(publicOrder.getCreated_timestamp()));
            binding.tvCoName.setText(publicOrder.getStore_name());
            binding.tvCoAddress.setText(publicOrder.getStore_address());
            //tvItemsNum.setText(publicOrder.get);
            if (publicOrder.getStatus().equals(AppContent.DELIVERED_STATUS)) {
                binding.tvOrderState.setBackgroundResource(R.drawable.green_button);
            } else if (publicOrder.getStatus().equals(AppContent.CANCELLED_STATUS)) {
                binding.tvOrderState.setBackgroundResource(R.drawable.red_button);
            }
            binding.tvReceiverName.setText(publicOrder.getCustomer().getName());
            binding.tvReceiverAddress.setText(publicOrder.getCustomer().getAddress());
            binding.tvPrice.setText((DecimalFormatterManager.getFormatterInstance()
                    .format(Double.parseDouble(publicOrder.getTotal())) + " " + AppController.
                    getInstance().getAppSettingsPreferences().getUser().getCountry().getCurrency_code()));
            binding.tvReceiverPoint.setText(baseActivity.getString(R.string.home));
            binding.tvOrderState.setText(publicOrder.getStatus_translation());
            binding.tvPaymentWay.setText(publicOrder.getPayment_type());
            binding.tvNumItems.setText(publicOrder.getAttachments().size() + baseActivity.getString(R.string.items));
        }
    }
}
