package com.mox_flim.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private M3uAdapter adapter; // আপনার ম্যারাথন লিস্ট দেখানোর অ্যাডাপ্টার
    private List<M3uSource> m3uList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(findViewById(R.id.recycler_view) != null ? R.id.recycler_view : android.R.id.list);
        progressBar = findViewById(findViewById(R.id.progress_bar) != null ? R.id.progress_bar : android.R.id.progress);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        // অ্যাডাপ্টার সেটআপ এবং ক্লিকের লজিক
        adapter = new M3uAdapter(m3uList, new M3uAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(M3uSource source) {
                // ইউজার আইটেমে ক্লিক করলে ভিডিও প্লেয়ার ওপেন হবে
                Intent intent = new Intent(MainActivity.this, VideoPlayerActivity.class);
                intent.putExtra("VIDEO_URL", source.getUrl());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        // সার্ভার থেকে ডেটা লোড করা
        loadStreamingData();
    }

    private void loadStreamingData() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<M3uSource>> call = apiService.getStreamingSources();

        call.enqueue(new Callback<List<M3uSource>>() {
            @Override
            public void onResponse(Call<List<M3uSource>> call, Response<List<M3uSource>> response) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    m3uList.clear();
                    m3uList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "ডেটা লোড করতে সমস্যা হয়েছে", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<M3uSource>> call, Throwable t) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "নেটওয়ার্ক সমস্যা: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

