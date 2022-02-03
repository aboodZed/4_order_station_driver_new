package com.AbdUlla.a4_order_station_driver.feature.data.natification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.AbdUlla.a4_order_station_driver.databinding.FragmentNotificationBinding;
import com.AbdUlla.a4_order_station_driver.feature.main.adapter.NotificationsAdapter;
import com.AbdUlla.a4_order_station_driver.models.Notification;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.WaitDialogFragment;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;

import java.util.ArrayList;

public class NotificationFragment extends Fragment implements DialogView<ArrayList<Notification>> {

    public static final int page = 604;

    private FragmentNotificationBinding binding;

    //private NotificationsAdapter readIt;
    private NotificationsAdapter notReadIt;
    private BaseActivity baseActivity;

    public NotificationFragment(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public static NotificationFragment newInstance(BaseActivity baseActivity) {
        NotificationFragment fragment = new NotificationFragment(baseActivity);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(getLayoutInflater());
        new NotificationPresenter(baseActivity, this);
        return binding.getRoot();
    }

    @Override
    public void setData(ArrayList<Notification> notifications) {
        notReadIt = new NotificationsAdapter(baseActivity, this);
       // readIt = new NotificationsAdapter(baseActivity, this);
        binding.rvNotificationUnread.setLayoutManager(new LinearLayoutManager(getActivity()));
        //binding.rvNotification.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvNotificationUnread.setItemAnimator(new DefaultItemAnimator());
        //binding.rvNotification.setItemAnimator(new DefaultItemAnimator());
        binding.rvNotificationUnread.setAdapter(notReadIt);
        //binding.rvNotification.setAdapter(readIt);
        for (Notification notification : notifications){
          //  if (notification.getRead_at() == null){
                notReadIt.addItem(notification);
//            }else {
//                readIt.addItem(notification);
//            }
        }
    }

    @Override
    public void showDialog(String s) {
        WaitDialogFragment.newInstance().show(getChildFragmentManager(), "");
    }

    @Override
    public void hideDialog() {
        WaitDialogFragment.newInstance().dismiss();
    }
}
