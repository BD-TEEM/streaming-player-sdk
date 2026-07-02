package com.mox_flim.me;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

@OptIn(markerClass = UnstableApi.class)
public class VideoPlayerActivity extends AppCompatActivity {
    private ExoPlayer player;
    private PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        playerView = findViewById(R.id.player_view);
        String url = getIntent().getStringExtra("VIDEO_URL");

        if (url == null || url.isEmpty()) {
            Toast.makeText(this, "লিংকটি কাজ করছে না!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // প্লেয়ার তৈরি
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // মিডিয়া আইটেম সেট করা
        MediaItem mediaItem = MediaItem.fromUri(url);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.pause(); // অ্যাপ বন্ধ হলে ভিডিও পজ হবে, ক্র্যাশ করবে না
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
।
