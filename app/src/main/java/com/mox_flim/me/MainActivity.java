package com.mox_flim.me;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickListener {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private final ArrayList<MovieResponse.Movie> movieArrayList = new ArrayList<>();
    private final String API_KEY = "YOUR_TMDB_API_KEY_HERE"; // এখানে আপনার TMDB API Key বসবে

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // টেস্টের জন্য সরাসরি কোড দিয়ে একটি RecyclerView তৈরি করে নিচ্ছি
        recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setContentView(recyclerView);

        // অ্যাডাপ্টার সেটআপ করা
        movieAdapter = new MovieAdapter(movieArrayList, this);
        recyclerView.setAdapter(movieAdapter);

        // সার্ভার থেকে মুভি ডেটা লোড করা
        loadMovies();
    }

    private void loadMovies() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<MovieResponse> call = apiService.getPopularMovies(API_KEY, 1);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movieArrayList.clear();
                    movieArrayList.addAll(response.body().getResults());
                    movieAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "ডেটা লোড করতে সমস্যা হয়েছে", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "নেটওয়ার্ক এরর: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMovieClick(MovieResponse.Movie movie) {
        // মুভিতে ক্লিক করলে ভিডিও প্লেয়ার স্ক্রিন ওপেন হবে
        Intent intent = new Intent(MainActivity.this, VideoPlayerActivity.class);
        intent.putExtra("video_url", movie.getVideoUrl()); // ভিডিওর লিঙ্ক পাস করা হচ্ছে
        startActivity(intent);
    }
}

