package com.mox_flim.me;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextUrl = findViewById(R.id.edit_text_url);
        Button btnPlay = findViewById(R.id.btn_play);

        btnPlay.setOnClickListener(v -> {
            String url = editTextUrl.getText().toString().trim();
            if (!url.isEmpty()) {
                Intent intent = new Intent(this, VideoPlayerActivity.class);
                intent.putExtra("VIDEO_URL", url);
                startActivity(intent);
            } else {
                Toast.makeText(this, "লিংক দিন!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
