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

public class Activity_One extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_one);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        Log.d("E", " 222222 Activity_One onCreate !!!!!!!!!!!!");
        Button btnCloseOne = findViewById(R.id.button_close_activiy_two);
        Button btnChangeTwo = findViewById(R.id.button_start_activiy_three);
        btnCloseOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnChangeTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_One.this, Activity_Two.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("E", " 222222 Activity_One onStart @@@@@@@@@@@@@");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("E", " 222222 Activity_One onResume #############");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("E", " 222222 Activity_One onPause $$$$$$$$$$$$$");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("E", " 222222 Activity_One onRestart %%%%%%%%%%%%%%%%%");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("E", " 222222 Activity_One onStop ^^^^^^^^^^^^^^^^");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("E", " 222222 Activity_One onDestroy &&&&&&&&&&&&&&&&&&&");
    }
}