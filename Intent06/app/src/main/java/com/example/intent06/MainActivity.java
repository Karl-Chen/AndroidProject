package com.example.intent06;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Log.d("E", " 111111 MainActivity onCreate !!!!!!!!!!!!");
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        Button btn_satrt_one = findViewById(R.id.button_start_activiy_two);
        Button btn_start_two = findViewById(R.id.button_start_activiy_three);
        btn_satrt_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Activity_One.class);
                startActivity(i);
            }
        });

        btn_start_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Activity_Two.class);
                startActivity(i);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("E", " 111111 MainActivity onStart @@@@@@@@@@@@@");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("E", " 111111 MainActivity onResume #############");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("E", " 111111 MainActivity onPause $$$$$$$$$$$$$");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("E", " 111111 MainActivity onRestart %%%%%%%%%%%%%%%%%");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("E", " 111111 MainActivity onStop ^^^^^^^^^^^^^^^^");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("E", " 111111 MainActivity onDestroy &&&&&&&&&&&&&&&&&&&");
    }
}