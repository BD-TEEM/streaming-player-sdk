package com.mox_flim.me;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide; // ইমেজ লোড করার জন্য প্রফেশনাল লাইব্রেরি
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final List<MovieResponse.Movie> movieList;
    private final OnMovieClickListener clickListener;

    // ক্লিক ইভেন্ট হ্যান্ডেল করার জন্য ইন্টারফেস
    public interface OnMovieClickListener {
        void onMovieClick(MovieResponse.Movie movie);
    }

    public MovieAdapter(List<MovieResponse.Movie> movieList, OnMovieClickListener clickListener) {
        this.movieList = movieList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // এখানে আমরা মুভি আইটেমের কাস্টম লেআউট ইনফ্লেট করব
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.activity_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieResponse.Movie movie = movieList.get(position);
        holder.titleTextView.setText(movie.getTitle());

        // গ্লাইড লাইব্রেরি দিয়ে মুভির পোস্টার ইমেজ লোড করা (TMDB এর ইমেজ পাথসহ)
        String posterUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
        
        // এখানে টেস্টের জন্য অ্যান্ড্রোয়েডের ডিফল্ট টেক্সটভিউ ব্যবহার করা হয়েছে
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onMovieClick(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView posterImageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            // অ্যান্ড্রোয়েডের ডিফল্ট টেক্সট আইডি (পরবর্তীতে আমরা কাস্টম XML দিয়ে এটি পরিবর্তন করব)
            titleTextView = itemView.findViewById(android.R.id.text1);
        }
    }
}

