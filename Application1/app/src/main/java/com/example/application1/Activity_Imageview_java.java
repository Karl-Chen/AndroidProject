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

public class Activity_Imageview_java extends AppCompatActivity {

    ImageView img1;
    TextView text_change_pic, text_change_pic_scaletype;
    int imageIndex = 0, scaleIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_imageview_java);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        img1 = findViewById(R.id.image1);
        text_change_pic = findViewById(R.id.text_change_pic);
        text_change_pic_scaletype = findViewById(R.id.text_change_pic_scaletype);
        text_change_pic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(imageIndex == 2) {
                        img1.setImageResource(R.drawable.imageview_java_1);
                        imageIndex = 1;
                    } else {
                        img1.setImageResource(R.drawable.imageview_java_2);
                        imageIndex++;
                    }
                } else {
                    img1.setImageResource(R.drawable.strikefreedom);
                }
                return false;
            }
        });
        text_change_pic_scaletype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scaleIndex == 2) {
                    scaleIndex = 1;
                    img1.setScaleType(ImageView.ScaleType.FIT_CENTER);
                } else {
                    scaleIndex++;
                    img1.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }
        });

    }
}