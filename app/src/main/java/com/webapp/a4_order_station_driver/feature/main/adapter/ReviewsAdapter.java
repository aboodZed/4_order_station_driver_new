package com.webapp.a4_order_station_driver.feature.main.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.a4_order_station_driver.databinding.ItemRatingBinding;
import com.webapp.a4_order_station_driver.models.Rating;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder> {

    private ArrayList<Rating.RatingData> reviews = new ArrayList<>();
    private Activity activity;

    public ReviewsAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewHolder(ItemRatingBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        holder.setData(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void addItem(Rating.RatingData review) {
        reviews.add(review);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(ArrayList<Rating.RatingData> arrayList) {
        if (arrayList != null) {
            reviews.addAll(arrayList);
            notifyDataSetChanged();
        }
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {

        private ItemRatingBinding binding;

        public ReviewHolder(ItemRatingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(Rating.RatingData review) {
            /*APIImageUtil.loadImage(activity, binding.pbWaitAvater, review.getUser().getAvatar_url(), binding.ivUserImage);
            binding.tvUserName.setText(review.getUser().getName());
            binding.tvReviewText.setText(review.getReview());
            float rate = Float.parseFloat(review.getRate());
            binding.rbReview.setRating(rate);
            binding.tvDatetime.setText((ToolUtil.getTime(review.getCreated_timestamp()) + " "
                    + ToolUtil.getDate(review.getCreated_timestamp())));*/
        }
    }
}
