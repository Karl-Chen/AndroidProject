package com.example.application1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity_Button_Java extends AppCompatActivity {
    Button btn_01, btn_02;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_button_java);
        btn_01 = (Button) findViewById(R.id.btn_01);
        btn_02 = (Button) findViewById(R.id.btn_02);
        btn_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                i++;
////                String str = btn_01.getText().toString();
//                btn_01.setText("已按下按鈕一：" + i + "次" );
                btn_01.setText("已按下按鈕一");
                btn_01.setTextColor(Color.RED);
                ((Button)v).setClickable(false);

                btn_02.setText("按鈕二");
                btn_02.setTextColor(Color.BLACK);
                btn_02.setClickable(true);
            }
        });

        btn_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_02.setText("已按下按鈕二");
                btn_02.setTextColor(Color.BLUE);
                ((Button)v).setClickable(false);

                btn_01.setText("按鈕一");
                btn_01.setTextColor(Color.BLACK);
                btn_01.setClickable(true);
            }
        });
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }
}