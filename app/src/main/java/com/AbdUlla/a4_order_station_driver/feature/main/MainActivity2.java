package com.AbdUlla.a4_order_station_driver.feature.main;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.ActivityMain2Binding;
import com.AbdUlla.a4_order_station_driver.feature.data.DataActivity;
import com.AbdUlla.a4_order_station_driver.feature.data.contact.ContactFragment;
import com.AbdUlla.a4_order_station_driver.feature.data.natification.NotificationFragment;
import com.AbdUlla.a4_order_station_driver.feature.data.privacy.PrivacyPolicyFragment;
import com.AbdUlla.a4_order_station_driver.feature.data.rate.RatingFragment;
import com.AbdUlla.a4_order_station_driver.feature.login.LoginActivity;
import com.AbdUlla.a4_order_station_driver.feature.main.home.HomeFragment;
import com.AbdUlla.a4_order_station_driver.feature.main.orders.OrdersFragment;
import com.AbdUlla.a4_order_station_driver.feature.main.profile.ProfileFragment;
import com.AbdUlla.a4_order_station_driver.feature.main.wallets.WalletFragment;
import com.AbdUlla.a4_order_station_driver.feature.splash.SplashActivity;
import com.AbdUlla.a4_order_station_driver.models.User;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.SignOutDialog;
import com.AbdUlla.a4_order_station_driver.utils.location.tracking.OrderGPSTracking;
import com.AbdUlla.a4_order_station_driver.utils.util.APIImageUtil;
import com.AbdUlla.a4_order_station_driver.utils.AppContent;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.util.NavigateUtil;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.NewOrderStationDialog;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.NewPublicOrderDialog;
import com.AbdUlla.a4_order_station_driver.utils.dialogs.WaitDialogFragment;
import com.AbdUlla.a4_order_station_driver.utils.language.AppLanguageUtil;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity2 extends BaseActivity implements DialogView<Boolean> {

    public static final int page = 200;

    public static boolean isLoadingNewOrder;

    private ActivityMain2Binding binding;
    private MainPresenter presenter;

    private SwitchCompat onOffOnline;
    private TextView tvStatus;
    private CircleImageView civ_avatar;
    private TextView tv_user_name;
    private RatingBar rb_user_rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        super.setRootView(binding.getRoot());
        super.onCreate(savedInstanceState);

        //toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout
                , binding.appBarMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setToolbarNavigationClickListener(view -> binding.drawerLayout.openDrawer(GravityCompat.START));
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //presenter
        presenter = new MainPresenter(this, this);
        HomeFragment.isOpen = false;
        navigate(HomeFragment.page);
        data();
        //define clicking
        click();
        dataOfNavigationHeader();
    }

    private void data() {
        //dialog
        if (AppController.getInstance().getAppSettingsPreferences().getToken().isEmpty() ||
                !AppController.getInstance().getAppSettingsPreferences().getUser().getBalance().getIsSubscribe()) {
            new NavigateUtil().activityIntent(this, SplashActivity.class, false);
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            try {
                String message = (String) bundle.get(AppContent.FIREBASE_MESSAGE);
                JSONObject body = new JSONObject(message).getJSONObject(AppContent.FIREBASE_DATA);
                int id;
                //String msg = body.getString(AppContent.FIREBASE_MSG);
                String type = body.getString(AppContent.FIREBASE_TYPE);
                String status = body.getString(AppContent.FIREBASE_STATUS);
                if (status.equals(AppContent.DRIVER_APPROVED)) {
                    new NavigateUtil().activityIntent(this, LoginActivity.class, false);
                    return;
                } else if ((status.equals(AppContent.CONFIRM_DELIVIERY))) {
                    AppController.getInstance().getAppSettingsPreferences().setTrackingPublicOrder(null);
                    OrderGPSTracking.newInstance(this).removeUpdates();
                } else if (status.equals(AppContent.SUBSCRIBE_STATUS)) {
                    new NavigateUtil().activityIntent(this, SplashActivity.class, false);
                }
                switch (type) {
                    case AppContent.TYPE_ORDER_4STATION:
                    case AppContent.TYPE_ORDER_PUBLIC:
                        id = body.getInt(AppContent.ORDER_Id);
                        break;
                    default:
                        id = -1;
                }


                if (status.contains(AppContent.NEW_ORDER)) {
                    if (!isLoadingNewOrder) {
                        createNewOrder(id, type);
                    }
                } else if (type.equals(AppContent.TYPE_ORDER_PUBLIC)
                        || type.equals(AppContent.TYPE_ORDER_4STATION)) {
                    navigate(OrdersFragment.page);
                    if (status.equals(AppContent.NEW_MESSAGE)) {
                        if (type.equals(AppContent.TYPE_ORDER_PUBLIC)) {
                            presenter.getPublicOrder(id, type);
                        } else {
                            presenter.getOrderStation(id, type);
                        }
                    }
                } else if (type.contains(AppContent.WALLET)) {
                    navigate(WalletFragment.page);
                } else if (type.equals(AppContent.RATE)) {
                    navigate(RatingFragment.page);
                }
            } catch (Exception e) {
                e.printStackTrace();
                isLoadingNewOrder = false;
            }
        }
    }


    private void createNewOrder(int id, String type) {
        isLoadingNewOrder = true;
        if (type != null)
            if (type.equals(AppContent.TYPE_ORDER_4STATION)) {
                NewOrderStationDialog dialog = NewOrderStationDialog.newInstance(id);
                dialog.show(getSupportFragmentManager(), "");
                dialog.setListener(new NewOrderStationDialog.NewOrderListener() {
                    @Override
                    public void allowLoadNewOrder() {
                        isLoadingNewOrder = false;
                    }

                    @Override
                    public void cancel() {
                        allowLoadNewOrder();
                        HomeFragment.isOpen = false;
                        navigate(HomeFragment.page);
                    }
                });
            } else {
                NewPublicOrderDialog dialog = NewPublicOrderDialog.newInstance(id);
                dialog.show(getSupportFragmentManager(), "");
                dialog.setListener(new NewPublicOrderDialog.NewPublicOrderListener() {
                    @Override
                    public void allowLoadNewOrder() {
                        isLoadingNewOrder = false;
                    }

                    @Override
                    public void cancel() {
                        allowLoadNewOrder();
                        HomeFragment.isOpen = false;
                        navigate(HomeFragment.page);
                    }
                });
            }
    }

    private void click() {
        //menu
        Menu menu = binding.navView.getMenu();
        MenuItem home = menu.findItem(R.id.nav_home);
        MenuItem rating = menu.findItem(R.id.nav_rating);
        MenuItem contact = menu.findItem(R.id.nav_contact);
        MenuItem privacy = menu.findItem(R.id.nav_privacy);
        MenuItem logout = menu.findItem(R.id.nav_log_out);

        //home
        home.setOnMenuItemClickListener(menuItem -> {
            navigate(HomeFragment.page);
            return false;
        });

        //rating
        rating.setOnMenuItemClickListener(menuItem -> {
            navigate(RatingFragment.page);
            return false;
        });

        //contact
        contact.setOnMenuItemClickListener(menuItem -> {
            navigate(ContactFragment.page);
            return false;
        });

        //privacy
        privacy.setOnMenuItemClickListener(menuItem -> {
            navigate(PrivacyPolicyFragment.page);
            return false;
        });

        //language toggle
        MenuItem language = menu.findItem(R.id.nav_language);
        View actionView = language.getActionView();
        SwitchCompat aSwitch = actionView.findViewById(R.id.s_language);

        aSwitch.setChecked(AppController.getInstance().getAppSettingsPreferences()
                .getAppLanguage().equals(AppLanguageUtil.ARABIC));

        aSwitch.setOnClickListener(view -> {
            if (aSwitch.isChecked()) {
                AppLanguageUtil.getInstance().setAppLanguage(MainActivity2.this, AppLanguageUtil.ARABIC);
            } else {
                AppLanguageUtil.getInstance().setAppLanguage(MainActivity2.this, AppLanguageUtil.English);
            }
            recreate();
        });

        //logout
        logout.setOnMenuItemClickListener(menuItem -> {
            SignOutDialog signOutDialog = new SignOutDialog(this, getSupportFragmentManager()
                    , signOutDialog1 -> presenter.logout(signOutDialog1));
            signOutDialog.show();
            return false;
        });

        //bottom bar
        binding.appBarMain.ivNotification.setOnClickListener(view -> navigate(NotificationFragment.page));
        binding.appBarMain.llHome.setOnClickListener(view -> navigate(HomeFragment.page));
        binding.appBarMain.llWallet.setOnClickListener(view -> navigate(WalletFragment.page));
        binding.appBarMain.llOrders.setOnClickListener(view -> navigate(OrdersFragment.page));
        binding.appBarMain.llProfile.setOnClickListener(view -> navigate(ProfileFragment.page));
    }

    public void dataOfNavigationHeader() {
        View v = binding.navView.getHeaderView(0);
        civ_avatar = v.findViewById(R.id.iv_user_image);
        tv_user_name = v.findViewById(R.id.tv_user_name);
        rb_user_rating = v.findViewById(R.id.rb_review);
        onOffOnline = v.findViewById(R.id.s_on_off_line);
        tvStatus = v.findViewById(R.id.tv_status);

        //header data
        User user = AppController.getInstance().getAppSettingsPreferences().getUser();
        APIImageUtil.loadImage(this, v.findViewById(R.id.pb_wait), AppContent.IMAGE_STORAGE_URL + user.getAvatar_url(), civ_avatar);
        tv_user_name.setText(user.getName());
        rb_user_rating.setRating(user.getRate());
        //onOffOnline.setChecked(AppController.getInstance().getAppSettingsPreferences().getUser().getIs_online());
        setStatus(tvStatus);

        //click on header
        onOffOnline.setOnClickListener(view -> {
            presenter.updateState(onOffOnline.isChecked());
        });
    }

    private void setStatus(TextView tvStatus) {
        onOffOnline.setChecked(AppController.getInstance().getAppSettingsPreferences().getUser().isOnline());
        if (onOffOnline.isChecked()) {
            tvStatus.setText(getString(R.string.online));
            tvStatus.setTextColor(getColor(R.color.green));
        } else {
            tvStatus.setText(getString(R.string.offline));
            tvStatus.setTextColor(getColor(R.color.red));
        }
    }

    public void navigate(int page) {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        switch (page) {
            case WalletFragment.page://2
                refreshBottomBar();
                WalletFragment walletFragment = WalletFragment.newInstance(this);
                new NavigateUtil().replaceFragment(getSupportFragmentManager()
                        , walletFragment, R.id.nav_host_fragment_content_main);
                binding.appBarMain.ivIcWallet.setBackgroundResource(R.drawable.ic_wallet_active);
                binding.appBarMain.tvTextWallet.setTextColor(getColor(R.color.colorPrimary));
                binding.appBarMain.tvPageTitle.setText(R.string.order_count);
                HomeFragment.isOpen = false;
                break;

            case OrdersFragment.page://3
                refreshBottomBar();
                OrdersFragment ordersFragment = OrdersFragment.newInstance(this);
                new NavigateUtil().replaceFragment(getSupportFragmentManager()
                        , ordersFragment, R.id.nav_host_fragment_content_main);
                binding.appBarMain.ivIcOrders.setBackgroundResource(R.drawable.ic_order_active);
                binding.appBarMain.tvTextOrders.setTextColor(getColor(R.color.colorPrimary));
                binding.appBarMain.tvPageTitle.setText(R.string.orders);
                HomeFragment.isOpen = false;
                break;

            case ProfileFragment.page://4
                refreshBottomBar();
                ProfileFragment profileFragment = ProfileFragment.newInstance(this);
                new NavigateUtil().replaceFragment(getSupportFragmentManager()
                        , profileFragment, R.id.nav_host_fragment_content_main);
                profileFragment.setListener(this::dataOfNavigationHeader);
                binding.appBarMain.ivIcProfile.setBackgroundResource(R.drawable.ic_profile_active);
                binding.appBarMain.tvTextProfile.setTextColor(getColor(R.color.colorPrimary));
                binding.appBarMain.tvPageTitle.setText(R.string.profile);
                HomeFragment.isOpen = false;
                break;

            case RatingFragment.page:
                new NavigateUtil().activityIntentWithPage(MainActivity2.this, DataActivity.class
                        , true, RatingFragment.page);
                break;

            case ContactFragment.page:
                new NavigateUtil().activityIntentWithPage(MainActivity2.this, DataActivity.class
                        , true, ContactFragment.page);
                break;

            case PrivacyPolicyFragment.page:
                new NavigateUtil().activityIntentWithPage(MainActivity2.this, DataActivity.class
                        , true, PrivacyPolicyFragment.page);
                break;

            case NotificationFragment.page://8
                new NavigateUtil().activityIntentWithPage(MainActivity2.this, DataActivity.class
                        , true, NotificationFragment.page);
                break;
            case LoginActivity.page:
                new NavigateUtil().activityIntentWithPage(MainActivity2.this, LoginActivity.class
                        , false, LoginActivity.page);
                break;
            default:
                refreshBottomBar();
                binding.appBarMain.ivIcHome.setBackgroundResource(R.drawable.ic_home_active);
                binding.appBarMain.tvTextHome.setTextColor(getColor(R.color.colorPrimary));
                if (!HomeFragment.isOpen) {
                    HomeFragment homeFragment = HomeFragment.newInstance(this);
                    new NavigateUtil().replaceFragment(getSupportFragmentManager()
                            , homeFragment, R.id.nav_host_fragment_content_main);
                    binding.appBarMain.tvPageTitle.setText(R.string.home);
                    HomeFragment.isOpen = true;
                }
                break;
        }

        if (HomeFragment.isOpen) {
            binding.appBarMain.flTop.setBackgroundResource(R.color.transparent);
            binding.appBarMain.toolbar.setNavigationIcon(R.drawable.ic_menu_home);
            binding.appBarMain.ivNotification.setImageResource(R.drawable.ic_bill_home);
            binding.appBarMain.tvPageTitle.setTextColor(getColor(R.color.colorPrimary));
        } else {
            binding.appBarMain.flTop.setBackgroundResource(R.drawable.top_bar);
            binding.appBarMain.toolbar.setNavigationIcon(R.drawable.ic_menu);
            binding.appBarMain.ivNotification.setImageResource(R.drawable.ic_bill);
            binding.appBarMain.tvPageTitle.setTextColor(getColor(R.color.white));
        }
    }

    private void refreshBottomBar() {
        binding.appBarMain.ivIcHome.setBackgroundResource(R.drawable.ic_home_inactive);
        binding.appBarMain.ivIcWallet.setBackgroundResource(R.drawable.ic_wallet_inactive);
        binding.appBarMain.ivIcOrders.setBackgroundResource(R.drawable.ic_order_inactive);
        binding.appBarMain.ivIcProfile.setBackgroundResource(R.drawable.ic_profile_inactive);
        binding.appBarMain.tvTextHome.setTextColor(getColor(R.color.hint));
        binding.appBarMain.tvTextWallet.setTextColor(getColor(R.color.hint));
        binding.appBarMain.tvTextOrders.setTextColor(getColor(R.color.hint));
        binding.appBarMain.tvTextProfile.setTextColor(getColor(R.color.hint));
    }

    @Override
    public void setData(Boolean is_success) {
        if (is_success) {
            if (onOffOnline.isChecked()) {
                tvStatus.setText(getString(R.string.online));
                tvStatus.setTextColor(getColor(R.color.green));
            } else {
                tvStatus.setText(getString(R.string.offline));
                tvStatus.setTextColor(getColor(R.color.red));
            }
        } else {
            if (onOffOnline.isChecked()) {
                onOffOnline.setChecked(false);
            } else {
                onOffOnline.setChecked(true);
            }
        }
    }

    @Override
    public void showDialog(String s) {
        WaitDialogFragment.newInstance().show(getSupportFragmentManager(), "");

    }

    @Override
    public void hideDialog() {
        WaitDialogFragment.newInstance().dismiss();
    }

    @Override
    public void onBackPressed() {
        if (HomeFragment.isOpen) {
            super.onBackPressed();
        } else {
            navigate(HomeFragment.page);
        }
    }
}