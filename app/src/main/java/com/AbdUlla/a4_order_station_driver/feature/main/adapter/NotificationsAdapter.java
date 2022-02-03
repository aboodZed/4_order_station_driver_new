package com.AbdUlla.a4_order_station_driver.feature.main.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.ItemNotificationBinding;
import com.AbdUlla.a4_order_station_driver.feature.order.orderStation.newOrderStation.NewOrderStationFragment;
import com.AbdUlla.a4_order_station_driver.feature.order.publicOrder.publicOrderView.PublicOrderViewFragment;
import com.AbdUlla.a4_order_station_driver.models.Notification;
import com.AbdUlla.a4_order_station_driver.models.OrderStation;
import com.AbdUlla.a4_order_station_driver.models.PublicOrder;
import com.AbdUlla.a4_order_station_driver.models.Result;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;
import com.AbdUlla.a4_order_station_driver.utils.listeners.RequestListener;
import com.AbdUlla.a4_order_station_driver.utils.util.APIUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.NavigateUtil;
import com.AbdUlla.a4_order_station_driver.utils.util.ToolUtil;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationHolder> {

    private ArrayList<Notification> notifications = new ArrayList<>();
    private BaseActivity baseActivity;
    private DialogView<ArrayList<Notification>> dialogView;

    public NotificationsAdapter(BaseActivity baseActivity, DialogView<ArrayList<Notification>> dialogView) {
        this.dialogView = dialogView;
        this.baseActivity = baseActivity;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationHolder(ItemNotificationBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        holder.setData(notifications.get(position), baseActivity, dialogView);
    }

    public void addItem(Notification notification) {
        this.notifications.add(notification);
        notifyItemInserted(getItemCount() - 1);
    }

    public void deleteItem(Notification notification) {
        int p = this.notifications.indexOf(notification);
        this.notifications.remove(notification);
        notifyItemRemoved(p);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    static class NotificationHolder extends RecyclerView.ViewHolder {

        private ItemNotificationBinding binding;

        private int id;
        private String type = "";
        private BaseActivity baseActivity;
        private DialogView<ArrayList<Notification>> dialogView;

        public NotificationHolder(ItemNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            click();
        }

        private void click() {
            binding.llNotification.setOnClickListener(view -> openOrder());
        }

        @SuppressLint("SetTextI18n")
        private void setData(Notification notification, BaseActivity baseActivity
                , DialogView<ArrayList<Notification>> dialogView) {
            this.baseActivity = baseActivity;
            this.dialogView = dialogView;

            if (notification.getData().getType() != null) {
                this.type = notification.getData().getType();
                if (this.type.equals(AppContent.TYPE_ORDER_PUBLIC)) {
                    this.id = notification.getData().getOrder_id();
                    if (id == 0) {
                        this.id = notification.getData().getPublic_order_id();
                    }
                } else if (this.type.equals(AppContent.TYPE_ORDER_4STATION)) {
                    this.id = notification.getData().getOrder_id();
                }
            }

            if (id == 0) {
                binding.tvCoName.setText(baseActivity.getString(R.string.admin_message));
            } else {
                binding.tvCoName.setText(String.valueOf(id));
            }
            binding.tvDatetime.setText(ToolUtil.getTime(notification.getCreated_at())
                    + " " + ToolUtil.getDate(notification.getCreated_at()));
            binding.tvMessage.setText(notification.getData().getMsg());
        }

        public void openOrder() {
            if (id != 0) {
                if (!type.equals(AppContent.TYPE_ORDER_PUBLIC)) {
                    openOrderStation();
                } else {
                    openPublicOrder();
                }
            }
        }

        private void openOrderStation() {
            dialogView.showDialog("");
            new APIUtil<Result<OrderStation>>(baseActivity).getData(AppController
                            .getInstance().getApi().getOrderStation(id)
                    , new RequestListener<Result<OrderStation>>() {
                        @Override
                        public void onSuccess(Result<OrderStation> result, String msg) {
                            dialogView.hideDialog();
                            //MainActivity.setId(result);
                            if (result.getData().getStatus().equals(AppContent.READY_STATUS)) {

                                new NavigateUtil().openOrderStation(baseActivity, result.getData()
                                        , NewOrderStationFragment.page, true);
                                //baseActivity.navigate(6);
//                            } else if (result.getData().getDriver() != null) {
//                                if (result.getData().getDriver().getId() == AppController.getInstance()
//                                        .getAppSettingsPreferences().getUser().getId()) {
//
//                                    new NavigateUtil().openOrderStation(baseActivity, result.getData()
//                                            , OrderStationViewFragment.page, true);
//                                    //baseActivity.navigate(7);
//                                } else {
//                                    ToolUtil.showLongToast(baseActivity.getString(R.string.can_not_open), baseActivity);
//                                }
                            } else {
                                ToolUtil.showLongToast(baseActivity.getString(R.string.can_not_open), baseActivity);
                            }
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

        private void openPublicOrder() {
            dialogView.showDialog("");

            new APIUtil<Result<PublicOrder>>(baseActivity).getData(AppController.getInstance()
                    .getApi().getPublicOrder(id), new RequestListener<Result<PublicOrder>>() {
                @Override
                public void onSuccess(Result<PublicOrder> result, String msg) {
//                                    if (publicOrderObject.getData().getId() == 0) {
//
//                                        new NavigateUtil().openOrder(baseActivity, publicOrderObject.getData()
//                                                , NewPublicOrderFragment.page, true);
//
//                                    } else if (publicOrderObject.getData().getType()
//                                            .equals(String.valueOf(AppController.getInstance()
//                                                    .getAppSettingsPreferences().getUser().getId()))) {
//
//                                        new NavigateUtil().openOrder(baseActivity, publicOrderObject.getData()
//                                                , PublicOrderViewFragment.page, true);
//                                    } else {
//                                        ToolUtil.showLongToast(baseActivity.getString(R.string.can_not_open), baseActivity);
//                                    }
                    new NavigateUtil().openPublicOrder(baseActivity, result.getData()
                            , PublicOrderViewFragment.page, true);
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
    }
}
