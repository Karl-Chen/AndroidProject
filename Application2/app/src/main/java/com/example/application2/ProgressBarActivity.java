package com.example.application2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProgressBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_progress_bar);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        Button btn_cycle = findViewById(R.id.btn_startcycle);
        Button btn_spinner = findViewById(R.id.btn_start_spinner);
        Button btn_runProgress = findViewById(R.id.btn_run_progress);
        ProgressBar pb_cycle = findViewById(R.id.pb_cycle);
        ProgressBar pb_spinner = findViewById(R.id.pb_spinner);
        final ProgressDialog[] pd_HorizontalDialog = new ProgressDialog[1];
        btn_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_spinner.setVisibility(View.VISIBLE);
                pb_cycle.setVisibility(View.INVISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i =0; i< 101; i++) {
                            pb_spinner.setProgress(i);
                            SystemClock.sleep(100);
                        }
                        pb_spinner.setVisibility(View.INVISIBLE);
                    }
                }).start();
            }
        });
        btn_cycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_spinner.setVisibility(View.INVISIBLE);
                pb_cycle.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i =0; i< 101; i++) {
                            pb_cycle.setProgress(i);
                            SystemClock.sleep(100);
                        }
                        pb_cycle.setVisibility(View.INVISIBLE);
                    }
                }).start();
            }
        });
        btn_runProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd_HorizontalDialog[0] = new ProgressDialog(ProgressBarActivity.this);
                pd_HorizontalDialog[0].setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd_HorizontalDialog[0].setTitle("我是進度條");
                pd_HorizontalDialog[0].show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 100; i++) {
                            pd_HorizontalDialog[0].setMessage("已執行：" + i + "%");
                            SystemClock.sleep(50);
                        }
                        pd_HorizontalDialog[0].dismiss();
                    }
                }).start();

            }
        });
    }
}