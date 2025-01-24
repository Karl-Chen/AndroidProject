package com.example.application1;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity_RadioButton_java extends AppCompatActivity {

    RadioGroup rg1, rg2;
    RadioButton rb11, rb12,rb13, rb21, rb22, rb23;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_radio_button_java);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        TextView tvAnswer1 = findViewById(R.id.textview_answer1);
        TextView tvAnswer2 = findViewById(R.id.textview_answer2);
        rg1 = findViewById(R.id.radioGroup_01);
        rg2 = findViewById(R.id.radioGroup_02);

        rb11 = findViewById(R.id.radioButton_01);
        rb12 = findViewById(R.id.radioButton_02);
        rb13 = findViewById(R.id.radioButton_03);
        rb21 = findViewById(R.id.radioButton_04);
        rb22 = findViewById(R.id.radioButton_05);
        rb23 = findViewById(R.id.radioButton_06);
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                //Toast.makeText(Activity_RadioButton_java.this, rb.getText(), Toast.LENGTH_SHORT).show();
                tvAnswer1.setText("第一題選擇了：" + rb.getText().toString());
            }
        });

        View.OnClickListener radioClickListener_01 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int indexID = rg1.getCheckedRadioButtonId();  // 取得被選擇的RadioButton的ID
                tvAnswer2.setText("第二題選擇了：" + ((RadioButton)v).getText().toString());
            }
        };

        rb21.setOnClickListener(radioClickListener_01);
        rb22.setOnClickListener(radioClickListener_01);
        rb23.setOnClickListener(radioClickListener_01);
        String msg = "" + rg1.getCheckedRadioButtonId();    //getCheckedRadioButtonId()，如果都沒被選的話回傳值是-1
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}