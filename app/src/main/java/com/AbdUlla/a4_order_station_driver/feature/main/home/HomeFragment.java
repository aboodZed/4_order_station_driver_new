package com.AbdUlla.a4_order_station_driver.feature.main.home;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.feature.data.contact.ContactFragment;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.language.BaseActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.AbdUlla.a4_order_station_driver.databinding.FragmentHomeBinding;
import com.AbdUlla.a4_order_station_driver.feature.main.MainActivity2;
import com.AbdUlla.a4_order_station_driver.utils.location.LocationManager;
import com.AbdUlla.a4_order_station_driver.utils.location.tracking.GPSTracking;

public class HomeFragment extends Fragment implements OnMapReadyCallback, LocationManager.Listener {

    public static final int page = 201;
    public static boolean isOpen;
    private FragmentHomeBinding binding;

    private GoogleMap googleMap;
    private LocationManager locationManager;
    private boolean denialLock;

    private static BaseActivity activity;

    private ActivityResultLauncher<Intent> launcher;

    public static HomeFragment newInstance(BaseActivity baseActivity) {
        activity = baseActivity;
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        setOnActivityResult();
        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.getMapAsync(this);

        locationManager = new LocationManager(this, getActivity(), this);
        click();
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void click() {
        if (AppController.getInstance().getAppSettingsPreferences().getUser().getBalance().getOrders_balance().equals("0")) {
            binding.endSubscription.setText(getString(R.string.hi) + AppController.getInstance()
                    .getAppSettingsPreferences().getUser().getName() + "," + getString(R.string.end_balance));
            binding.endSubscription.setVisibility(View.VISIBLE);
        } else if (AppController.getInstance().getAppSettingsPreferences().getUser().getBalance().getEnd_date()) {
            binding.endSubscription.setText(getString(R.string.hi) + AppController.getInstance()
                    .getAppSettingsPreferences().getUser().getName() + "," + getString(R.string.end_subscription));
            binding.endSubscription.setVisibility(View.VISIBLE);
        }
        binding.endSubscription.setOnClickListener(v -> activity.navigate(ContactFragment.page));
    }

    private void setOnActivityResult() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        locationManager.fetchAutomaticLocation();
                    } else {
                        denialLock = true;
                        locationManager.showLocationDenialDialog();
                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setAllGesturesEnabled(true);

        //checkSelectedNeighborhood();
    }

    private void checkSelectedNeighborhood() {
       /* UpdateNeighborhoodDialog updateNeighborhoodDialog = new UpdateNeighborhoodDialog(baseActivity);
        updateNeighborhoodDialog.show(getChildFragmentManager(), "");*/
    }

    @Override
    public void onServicesOrPermissionChoice() {
        denialLock = false;
    }

    @Override
    public void onLocationFound(double latitude, double longitude) {
        if (!MainActivity2.isLoadingNewOrder) {
            zoomToLocation(new LatLng(latitude, longitude));
            //tracking
            GPSTracking.getInstance(getActivity()).startMyGPSTracking();
        }
    }

//    @Override
//    public void onRequestPermissionsResult(
//            int requestCode,
//            @NonNull String[] permissions,
//            @NonNull int[] grantResults) {
//        if (requestCode != LocationManager.LOCATION_PERMISSION_REQUEST_CODE) {
//            return;
//        }
//
//        // No need to check if the location permission has been granted because of the onResume() block
//        if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//            denialLock = true;
//            locationManager.showLocationPermissionDialog();
//        }
//    }
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    public void zoomToLocation(LatLng location) {
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(location));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(location)
                .zoom(16)
                .bearing(0)
                .tilt(0)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();

        // Run this here instead of onCreate() to cover the case where they return from turning on location
        if (!denialLock) {
            locationManager.fetchCurrentLocation();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.mapView.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
        locationManager.cleanUp();
    }
}
