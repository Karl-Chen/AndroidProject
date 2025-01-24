package com.example.application1;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity_OnTouch_Java extends AppCompatActivity {

    ImageView img1;
    TextView text_change_pic, text_change_pic_typescale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview_java);
        img1 = findViewById(R.id.image1);
        text_change_pic = findViewById(R.id.text_change_pic);
        text_change_pic_typescale = findViewById(R.id.text_change_pic_scaletype);
        text_change_pic.setText("手勢觸發的動作型態：");
        img1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    text_change_pic_typescale.setText("ACTION_DOWN 動作被觸發");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    text_change_pic_typescale.setText("ACTION_UP 動作被觸發");
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    text_change_pic_typescale.setText("ACTION_MOVE 動作被觸發");
                } else {
                    text_change_pic_typescale.setText("ACTION_Code - " + event.getAction());
                }

                return false;
            }
        });
    }
}