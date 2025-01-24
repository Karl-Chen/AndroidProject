package com.example.intent02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityTemperatureF extends AppCompatActivity {

    TextView textView_temp_f;
    double c, f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_temperature_f);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        Intent intent_01 = ActivityTemperatureF.this.getIntent();
//        Intent i = this.getIntent();//也這樣寫
        Bundle bundle_01 = intent_01.getExtras();
        textView_temp_f = findViewById(R.id.text_result);
        String tmpStr = "轉換後的華氏溫度：";
        if (bundle_01 != null) {
            String value = bundle_01.getString("TEMP_C_01");
            if (!value.isEmpty()) {
                c = Double.parseDouble(value);
                f = c * 9.0 / 5.0 + 32.0;
                tmpStr += f;
            }
        }

        textView_temp_f.setText(tmpStr);

        Button btn_close = findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}