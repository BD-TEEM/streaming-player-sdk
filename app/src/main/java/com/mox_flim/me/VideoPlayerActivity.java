package com.mox_flim.me;

import android.os.Bundle;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

public class VideoPlayerActivity extends AppCompatActivity {

    private ExoPlayer player;
    private PlayerView playerView;

    @OptIn(markerClass = UnstableApi.class)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        playerView = findViewById(R.id.player_view);

        // MainActivity থেকে পাঠানো ভিডিও লিংকটি রিসিভ করা
        String videoUrl = getIntent().getStringExtra("VIDEO_URL");

        if (videoUrl != null) {
            initializePlayer(videoUrl);
        }
    }

    @UnstableApi
    private void initializePlayer(String videoUrl) {
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // HLS (.m3u8) বা সাধারণ ভিডিও ফরম্যাট চেনার কনফিগারেশন
        MediaItem mediaItem;
        if (videoUrl.contains(".m3u8")) {
            mediaItem = new MediaItem.Builder()
                    .setUri(videoUrl)
                    .setMimeType(MimeTypes.APPLICATION_M3U8) // HLS এর জন্য বাধ্যতামূলক
                    .build();
        } else {
            mediaItem = MediaItem.fromUri(videoUrl);
        }

        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(true); // অটোপ্লে হবে
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
