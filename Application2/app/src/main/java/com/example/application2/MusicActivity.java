package com.example.application2;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MusicActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener{

    MediaPlayer m_player;
    TextView tv_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        Button btn_start = findViewById(R.id.btn_start);
        Button btn_pause = findViewById(R.id.btn_pause);
        Button btn_stop = findViewById(R.id.btn_stop);
        tv_status = findViewById(R.id.textview_status);
        m_player = MediaPlayer.create(this, R.raw.piano01);
        m_player.setOnCompletionListener(this);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m_player != null && !m_player.isPlaying()) {
                    m_player.start();
                    tv_status.setText("播放狀態：音樂播放中");
                }
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m_player != null && m_player.isPlaying()) {
                    m_player.pause();
                    tv_status.setText("播放狀態：暫停播放");
                }
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m_player != null) {
                    m_player.stop();
                    tv_status.setText("播放狀態：停止播放");
                    try {
                        m_player.prepareAsync();
                    } catch (IllegalStateException e) {
                        tv_status.setText("異常狀態：" + e.getMessage());
                    }
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (m_player != null) {
            if (m_player.isPlaying()) {
                m_player.stop();
            }
            m_player.release();
            m_player = null;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        tv_status.setText("播放狀態：播放完畢");
        mp.seekTo(0);
        try{
            mp.stop();
            mp.prepareAsync();
        } catch (IllegalStateException ex) {
            tv_status.setText("播放狀態：" + ex.getMessage() );
            ex.printStackTrace();
        }
    }
}