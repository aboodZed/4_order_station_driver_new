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
import com.AbdUlla.a4_order_station_driver.feature.order.publicOrder.publicOrderView.PublicOrderViewFragment;
import com.AbdUlla.a4_order_station_driver.models.Order;
import com.AbdUlla.a4_order_station_driver.models.OrderStation;
import com.AbdUlla.a4_order_station_driver.models.PublicOrder;
import com.AbdUlla.a4_order_station_driver.models.Result;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.WaitDialogFragment;
import com.AbdUlla.a4_order_station_driver.utils.formatter.DecimalFormatterManager;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.NavigateUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;

import java.util.ArrayList;

public class ProcessOrderAdapter extends RecyclerView.Adapter<ProcessOrderAdapter.PubicOrderHolder> {

    private ArrayList<Order> publicOrders = new ArrayList<>();
    private Activity baseActivity;
    private ProcessListener processListener;

    public ProcessOrderAdapter(Activity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public void setProcessListener(ProcessListener processListener) {
        this.processListener = processListener;
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

    public void addAll(ArrayList<Order> arrayList) {
        if (arrayList != null) {
            publicOrders.addAll(arrayList);
            notifyDataSetChanged();
        }
    }

    class PubicOrderHolder extends RecyclerView.ViewHolder {

        private ItemOrderBinding binding;

        private Order order;

        public PubicOrderHolder(ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            click();
        }

        private void click() {
            binding.rootLayout.setOnClickListener(view -> openChat());
        }

        public void openChat() {
            if (order.getType().equals(AppContent.TYPE_ORDER_PUBLIC)) {
                openPublicOrder();
            } else {
                open4orderStation();
            }

        }

        private void open4orderStation() {
            processListener.openDialog();
            new APIUtil<Result<OrderStation>>(baseActivity).getData(AppController.getInstance()
                    .getApi().getOrderStation(order.getId()), new RequestListener<Result<OrderStation>>() {
                @Override
                public void onSuccess(Result<OrderStation> orderStationResult, String msg) {
                    WaitDialogFragment.newInstance().dismiss();
                    new NavigateUtil().openOrderStation(baseActivity, orderStationResult.getData()
                            , OrderStationViewFragment.page, true);
                }

                @Override
                public void onError(String msg) {
                    ToolUtil.showLongToast(msg, baseActivity);
                    WaitDialogFragment.newInstance().dismiss();
                }

                @Override
                public void onFail(String msg) {
                    ToolUtil.showLongToast(msg, baseActivity);
                    WaitDialogFragment.newInstance().dismiss();
                }
            });
        }

        private void openPublicOrder() {
            processListener.openDialog();
            new APIUtil<Result<PublicOrder>>(baseActivity).getData(AppController.getInstance()
                    .getApi().getPublicOrder(order.getId()), new RequestListener<Result<PublicOrder>>() {
                @Override
                public void onSuccess(Result<PublicOrder> result, String msg) {
                    WaitDialogFragment.newInstance().dismiss();
                    new NavigateUtil().openPublicOrder(baseActivity, result.getData()
                            , PublicOrderViewFragment.page, true);
                }

                @Override
                public void onError(String msg) {
                    ToolUtil.showLongToast(msg, baseActivity);
                    WaitDialogFragment.newInstance().dismiss();
                }

                @Override
                public void onFail(String msg) {
                    ToolUtil.showLongToast(msg, baseActivity);
                    WaitDialogFragment.newInstance().dismiss();
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void setData(Order order) {
            this.order = order;
            if (order.getCustomer() != null) {
                binding.tvReceiverName.setText(order.getCustomer().getName());
                binding.tvReceiverAddress.setText(order.getCustomer().getAddress());
            } else if (order.getStore_address() != null) {
                binding.tvReceiverName.setText(order.getCustomer_address().getName());
                binding.tvReceiverAddress.setText(order.getCustomer_address().getAddress());
            }
            if (order.getStore() == null) {
                order.setType(AppContent.TYPE_ORDER_PUBLIC);
                //String[] s = publicOrder.getCreated_timestamp().split(" ");
                //binding.tvDate.setText((s[0] + "\n" + s[1]));
                binding.tvTime.setText(ToolUtil.getTime(order.getCreated_timestamp()) + " | " +
                        ToolUtil.getDate(order.getCreated_timestamp()));
                binding.tvCoName.setText(order.getStore_name());
                binding.tvCoAddress.setText(order.getStore_address());
                //tvItemsNum.setText(publicOrder.get);
                if (order.getStatus().equals(AppContent.DELIVERED_STATUS)) {
                    binding.tvOrderState.setBackgroundResource(R.drawable.green_button);
                } else if (order.getStatus().equals(AppContent.CANCELLED_STATUS)) {
                    binding.tvOrderState.setBackgroundResource(R.drawable.red_button);
                }

                binding.tvPrice.setText((DecimalFormatterManager.getFormatterInstance()
                        .format(Double.parseDouble(order.getTotal())) + " " + AppController.
                        getInstance().getAppSettingsPreferences().getUser().getCountry().getCurrency_code()));
                binding.tvReceiverPoint.setText(baseActivity.getString(R.string.home));
                binding.tvOrderState.setText(order.getStatus_translation());
                binding.tvPaymentWay.setText(order.getPayment_type());
                binding.tvNumItems.setText("0 Items");
            } else {
                order.setType(AppContent.TYPE_ORDER_4STATION);
                binding.tvTime.setText(order.getOrder_date());
                //binding.tvDate.setText();
                binding.tvNumItems.setText((order.getItems_count() + " " + baseActivity.getString(R.string.items)));
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

                binding.tvCoName.setText(order.getStore().getName());
                binding.tvCoAddress.setText(order.getStore().getAddress());
            }
            binding.tvType.setText(order.getType());
        }
    }

    public interface ProcessListener {
        void openDialog();
    }
}
