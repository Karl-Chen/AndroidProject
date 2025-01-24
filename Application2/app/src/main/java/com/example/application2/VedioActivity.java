package com.example.application2;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VedioActivity extends AppCompatActivity {

    private MediaController mController;
    private VideoView video;
    private String uriResourceTitle ="android.resource://";
    private String videoFileName = "point21";
    private String strVideoResourceUri;
    private int intVideoResourceID;

    private TextView textView_title;

    private int intPauseTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vedio);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        textView_title = findViewById(R.id.textview_title);
        video = findViewById(R.id.video);
        intVideoResourceID = getResources().getIdentifier(videoFileName, "raw", getPackageName());
        strVideoResourceUri = uriResourceTitle + getPackageName() + "/" + intVideoResourceID;
        video.setVideoURI(Uri.parse(strVideoResourceUri));
        mController = new MediaController(this);
        video.setMediaController(mController);
        video.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (video != null) {
            video.seekTo(intPauseTime);
            video.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        video.pause();
        intPauseTime = video.getCurrentPosition();
        textView_title.setText("功能-影片播放器：播放暫停於[" + String.valueOf(intPauseTime) + "]");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (video != null)
            if (video.isPlaying())
                video.stopPlayback();
        video = null;
        mController = null;
    }
}