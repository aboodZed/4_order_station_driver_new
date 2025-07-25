package com.AbdUlla.a4_order_station_driver.feature.data.rate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.AbdUlla.a4_order_station_driver.R;
import com.AbdUlla.a4_order_station_driver.databinding.FragmentRatingBinding;
import com.AbdUlla.a4_order_station_driver.feature.main.adapter.ReviewsAdapter;
import com.AbdUlla.a4_order_station_driver.models.Rating;
import com.AbdUlla.a4_order_station_driver.utils.AppController;
import com.AbdUlla.a4_order_station_driver.utils.listeners.DialogView;

public class RatingFragment extends Fragment implements DialogView<Rating> {

    public static final int page = 601;

    private FragmentRatingBinding binding;

    private ReviewsAdapter reviewsAdapter;
    //private String next_page_url;

    private boolean loadingMoreItems;
    private RatingPresenter presenter;

    public static RatingFragment newInstance() {
        RatingFragment fragment = new RatingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRatingBinding.inflate(getLayoutInflater());
        setUserRating();
        initRecycleView();
        presenter = new RatingPresenter(requireActivity(), this);
        presenter.getRatings();
        return binding.getRoot();
    }

    private void setUserRating() {
        binding.rbReview.setRating(AppController.getInstance().getAppSettingsPreferences()
                .getUser().getRate());
    }

    //functions


    private void setRecyclerViewScrollListener() {
        binding.rvRating.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
               /* if (!recyclerView.canScrollVertically(1)
                        && !TextUtils.isEmpty(next_page_url) && !loadingMoreItems) {
                    presenter.getRatings(next_page_url);
                }*/
            }
        });
    }

    private void initRecycleView() {
        reviewsAdapter = new ReviewsAdapter(getActivity());
        binding.rvRating.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvRating.setItemAnimator(new DefaultItemAnimator());
        binding.rvRating.setAdapter(reviewsAdapter);
        //reviewsAdapter.addItem(new RatingData());

        setRecyclerViewScrollListener();
    }

    @Override
    public void setData(Rating rating) {
        //next_page_url = ratingObject.getRatings().getNext_page_url();
        binding.rbReview.setRating(Float.parseFloat(rating.getRate()));
        reviewsAdapter.addAll(rating.getData());
        if (rating.getData().isEmpty()) {
            binding.ivEmptyRate.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showDialog(String s) {
        loadingMoreItems = true;
        binding.avi.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDialog() {
        loadingMoreItems = false;
        binding.avi.setVisibility(View.GONE);
    }
}
