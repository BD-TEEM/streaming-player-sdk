package com.mox_flim.me;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

@UnstableApi
public class VideoPlayerActivity extends AppCompatActivity {

    private ExoPlayer player;
    private PlayerView playerView;
    private PlayerDataSourceManager dataSourceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // এখানে আপনার লেআউট সেট করুন (যেমন: R.layout.activity_video_player)
        // এই টেস্টে আমরা সরাসরি কোড দিয়ে PlayerView তৈরি করে নিচ্ছি
        playerView = new PlayerView(this);
        setContentView(playerView);

        dataSourceManager = new PlayerDataSourceManager(this);
        // প্রয়োজনে কাস্টম হেডার বা টোকেন যোগ করতে পারেন:
        // dataSourceManager.addHttpHeader("User-Agent", "MoxFlim-Player");

        initializePlayer();
    }

    private void initializePlayer() {
        // আমাদের কাস্টম ক্যাশ ডেটাসোর্স ফ্যাক্টরি ব্যবহার করে প্লেয়ার তৈরি
        player = new ExoPlayer.Builder(this)
                .setMediaSourceFactory(new androidx.media3.exoplayer.source.DefaultMediaSourceFactory(dataSourceManager.buildCacheDataSourceFactory()))
                .build();

        playerView.setPlayer(player);

        // এখানে আপনার টেস্ট ভিডিও স্ট্রিমিং লিঙ্কটি দিন
        String videoUrl = "https://html5test.com/videos/google-chrome.mp4"; 
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(videoUrl));
        
        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (player != null) {
            player.setPlayWhenReady(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.setPlayWhenReady(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}

