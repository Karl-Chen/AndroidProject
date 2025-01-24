package com.example.intent03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityOperate extends AppCompatActivity {

    double value1, value2;
    RadioGroup rgOp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_operate);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Button btnCalToRet = findViewById(R.id.btn_cal_to_ret);
        rgOp = findViewById(R.id.rg_op);
        value1 = Double.parseDouble(bundle.getString("value1"));
        value2 = Double.parseDouble(bundle.getString("value2"));

        btnCalToRet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = rgOp.getCheckedRadioButtonId();
                CheckBox checkBox = findViewById(R.id.checkbox_divide);
                Boolean isDivided = checkBox.isChecked();
                double ret;
                if (id == R.id.rb_add) {
                    ret = value1 + value2;
                } else if (id == R.id.rb_dec) {
                    ret = value1 - value2;
                } else if (id == R.id.rb_multiply) {
                    ret = value1 * value2;
                } else if (id == -1) {
                    Toast.makeText(ActivityOperate.this, "請選擇運算子！！", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    if (isDivided) {
                        ret = (int)(value1 / value2);
                    } else {
                        ret = value1 / value2;
                    }
                }
                Intent i = new Intent();
                Bundle bundle = new Bundle();
                bundle.putDouble("CALCULATE_RESULT", ret);
                i.putExtras(bundle);
                setResult(RESULT_OK, i);
                finish();
            }
        });

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }
}