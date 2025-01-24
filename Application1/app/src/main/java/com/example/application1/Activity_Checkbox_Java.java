package com.example.application1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity_Checkbox_Java extends AppCompatActivity {

    CheckBox checkbox_answer_01, checkbox_answer_02, checkbox_answer_03, checkbox_answer_04;
    String strTmp;
    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            //strTmp = "本次選擇：";
            if (isChecked)
                strTmp ="本次選擇：" + buttonView.getText().toString();
            else
                strTmp ="本次取消：" + buttonView.getText().toString();

            Toast.makeText(Activity_Checkbox_Java.this, strTmp, Toast.LENGTH_LONG).show();

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkbox_java);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        checkbox_answer_01 = findViewById(R.id.checkbox_answer_01);
        checkbox_answer_02 = findViewById(R.id.checkbox_answer_02);
        checkbox_answer_03 = findViewById(R.id.checkbox_answer_03);
        checkbox_answer_04 = findViewById(R.id.checkbox_answer_04);

        CompoundButton.OnCheckedChangeListener listener2 = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                strTmp = "已選擇：";
                if (checkbox_answer_01.isChecked())
                {
                    strTmp+=checkbox_answer_01.getText().toString() + ",";
                }
                if (checkbox_answer_02.isChecked())
                {
                    strTmp+=checkbox_answer_02.getText().toString() + ",";
                }
                if (checkbox_answer_03.isChecked())
                {
                    strTmp+=strTmp+checkbox_answer_03.getText().toString() + ",";
                }
                if (checkbox_answer_04.isChecked())
                {
                    strTmp+=checkbox_answer_04.getText().toString();
                }
                Toast.makeText(Activity_Checkbox_Java.this, strTmp, Toast.LENGTH_LONG).show();
            }
        };

        checkbox_answer_01.setOnCheckedChangeListener(listener);
        checkbox_answer_02.setOnCheckedChangeListener(listener);
        checkbox_answer_03.setOnCheckedChangeListener(listener);
        checkbox_answer_04.setOnCheckedChangeListener(listener);
    }
}