package com.AbdUlla.a4_order_station_driver.feature.main.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.ItemBalanceBinding;
import com.AbdUlla.a4_order_station_driver.models.Ongoing;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.formatter.DecimalFormatterManager;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;

import java.util.ArrayList;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletHolder> {

    private ArrayList<Ongoing> ongoings;
    private BaseActivity baseActivity;

    public WalletAdapter(ArrayList<Ongoing> ongoings, BaseActivity baseActivity) {
        this.ongoings = ongoings;
        this.baseActivity = baseActivity;
    }

    @NonNull
    @Override
    public WalletHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WalletHolder(ItemBalanceBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WalletHolder holder, int position) {
        holder.setData(ongoings.get(position));
    }

    @Override
    public int getItemCount() {
        return ongoings.size();
    }

    public void addItem(Ongoing ongoing) {
        ongoings.add(ongoing);
        notifyItemInserted(getItemCount() - 1);
    }

    public ArrayList<Ongoing> getOngoings() {
        return ongoings;
    }

    public class WalletHolder extends RecyclerView.ViewHolder {

        private ItemBalanceBinding binding;

        public WalletHolder(ItemBalanceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void setData(Ongoing ongoing) {
            binding.tvOrderId.setText((baseActivity.getString(R.string.order_balance) + ongoing.getOrder_number()));
            binding.tvType.setText(ongoing.getType());
            if (ongoing.getEnd_at() != null)
                binding.tvDatetime.setText(ongoing.getEnd_at());
            if (ongoing.getOrder_type() != null) {
                binding.tvBy.setText(baseActivity.getString(R.string.by) + ongoing.getOrder_type());
            } else {
                binding.tvBy.setText(baseActivity.getString(R.string.by) + baseActivity.getString(R.string.admin));
            }
            //binding.tvType.setText(ongoing.get);
//            binding.tvPaymentWay.setText(ongoing.getPayment_type());
//            binding.tvPrice.setText((DecimalFormatterManager
//                    .getFormatterInstance().format(ongoing.getTotal())));
//            binding.tvCurrency.setText(AppController.getInstance()
//                    .getAppSettingsPreferences().getUser().getCountry().getCurrency_code());
//            if (ongoing.getTotal() < 0) {
//                binding.tvPrice.setTextColor(baseActivity.getColor(R.color.red));
//                binding.tvCurrency.setTextColor(baseActivity.getColor(R.color.red));
//            }
        }
    }
}
