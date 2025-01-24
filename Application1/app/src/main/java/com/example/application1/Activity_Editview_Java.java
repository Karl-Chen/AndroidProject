package com.example.application1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity_Editview_Java extends AppCompatActivity {

    Button btn_add;
    TextView text_answer;
    EditText edit_input_01, edit_input_02;
    double double_01, double_02, double_answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editview_java);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        btn_add = findViewById(R.id.btn_add);
        text_answer = findViewById(R.id.text_answer);
        edit_input_01 = findViewById(R.id.edit_input01);
        edit_input_02 = findViewById(R.id.edit_input02);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTmp1, strTmp2;
                strTmp1 = edit_input_01.getText().toString();
                double_01 = Double.parseDouble(strTmp1);
                strTmp2 = edit_input_02.getText().toString();
                double_02 = Double.parseDouble(strTmp2);
                double_answer = double_01 + double_02;
                text_answer.setText(" = " + double_answer);
            }
        });
    }
}