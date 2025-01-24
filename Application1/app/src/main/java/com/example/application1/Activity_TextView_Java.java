package com.example.application1;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity_TextView_Java extends AppCompatActivity {

    TextView text1, text2, text3;
    String strText1;
    int colorText2;
    float sizeText3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_text_view_java);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        strText1 = text1.getText().toString();
        colorText2 = text2.getCurrentTextColor();
        sizeText3 = text3.getTextSize() / getResources().getDisplayMetrics().scaledDensity;
        text1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    text1.setText("目前焦點：Focus在TextView1");
                }
                else
                {
                    text1.setText(strText1);
                }
            }
        });
        text2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    text2.setTextColor(0xff0000ff);
                }
                else
                {
                    text2.setTextColor(colorText2);
                }
            }
        });
        text3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    text3.setTextSize(TypedValue.COMPLEX_UNIT_SP,30f);
                }
                else
                {
                    text3.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeText3);
                }
            }
        });
    }
}