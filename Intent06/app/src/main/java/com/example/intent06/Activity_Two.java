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

public class Activity_Two extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_two);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        Log.d("E", " 333333 Activity_Two onCreate !!!!!!!!!!!!");
        Button btnCloseTwo = findViewById(R.id.button_close_activiy_three);
        Button btnChangeMain = findViewById(R.id.button_start_activiy_main);
        btnChangeMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Two.this, MainActivity.class);
                startActivity(i);
            }
        });
        btnCloseTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("E", " 333333 Activity_Two onStart @@@@@@@@@@@@@");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("E", " 333333 Activity_Two onResume #############");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("E", " 333333 Activity_Two onPause $$$$$$$$$$$$$");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("E", " 333333 Activity_Two onRestart %%%%%%%%%%%%%%%%%");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("E", " 333333 Activity_Two onStop ^^^^^^^^^^^^^^^^");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("E", " 333333 Activity_Two onDestroy &&&&&&&&&&&&&&&&&&&");
    }
}