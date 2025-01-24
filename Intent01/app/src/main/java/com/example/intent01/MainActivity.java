package com.example.intent01;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnStartTwo, btnStartThree;
    Group group_01;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnStartTwo = findViewById(R.id.button_start_activiy_two);
        btnStartThree = findViewById(R.id.button_start_activiy_three);
        group_01 = findViewById(R.id.group_01);
        btnStartTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Activity_two.class);
                startActivity(i);
            }
        });

        btnStartThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Activity_three.class);
//                startActivity(i);
                group_01.setVisibility(View.INVISIBLE);
                Handler h_01 = new Handler();
                h_01.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        group_01.setVisibility(View.VISIBLE);

                    }
                }, 3000);
                Handler h_02 = new Handler();
                h_02.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(i);
                    }
                }, 5000);
            }
        });

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }
}