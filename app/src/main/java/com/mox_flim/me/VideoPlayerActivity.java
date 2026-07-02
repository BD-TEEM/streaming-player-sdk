package com.mox_flim.me;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
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

        // MainActivity থেকে পাঠানো লাইভ স্ট্রিমিং লিঙ্কটি রিসিভ করা
        String videoUrl = getIntent().getStringExtra("VIDEO_URL");

        if (videoUrl != null && !videoUrl.isEmpty()) {
            initializePlayer(videoUrl);
        } else {
            Toast.makeText(this, "ভিডিও লিঙ্কটি সঠিক নয়!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @UnstableApi
    private void initializePlayer(String videoUrl) {
        // নতুন ExoPlayer ইন্সট্যান্স তৈরি
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // আইপিটিভি লাইভ চ্যানেল (.m3u8) প্লে করার জন্য মিডিয়া আইটেম কনফিগারেশন
        MediaItem mediaItem = new MediaItem.Builder()
                .setUri(videoUrl)
                .setMimeType(MimeTypes.APPLICATION_M3U8) // লাইভ এইচএলএস স্ট্রিমের জন্য বাধ্যতামূলক
                .build();

        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(true); // অ্যাপ ওপেন হলেই অটোমেটিক প্লে হবে

        // কোনো কারণে চ্যানেল লোড না হলে বা এরর আসলে তা হ্যান্ডেল করা
        player.addListener(new Player.Listener() {
            @Override
            public void onPlayerError(PlaybackException error) {
                Toast.makeText(VideoPlayerActivity.this, "এই চ্যানেলটি বর্তমানে অফলাইন আছে বা প্লে করা যাচ্ছে না!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.pause(); // ব্যাকগ্রাউন্ডে গেলে ভিডিও পজ হবে
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release(); // মেমোরি খালি করার জন্য প্লেয়ার রিলিজ করা
            player = null;
        }
    }
}
