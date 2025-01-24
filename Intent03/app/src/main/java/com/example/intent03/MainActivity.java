package com.example.intent03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

//    public static final int REQUEST_CODE_OP = 1001;
    ActivityResultLauncher launcher_callActivityOpt = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                int resultCode = o.getResultCode();
                Intent i = o.getData();
                if (resultCode == RESULT_OK) {
                    if (i != null && i.getExtras() != null) {
                        Double answer = i.getExtras().getDouble("CALCULATE_RESULT");
                        TextView textViewOutput = findViewById(R.id.textview_output);
                        textViewOutput.setText("運算結果" + answer.toString());
                    }
                }
            }
        });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button btnChoiceOp = findViewById(R.id.btn_choice_op);
        btnChoiceOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText1, editText2;
//                CheckBox checkBox = findViewById(R.id.checkbox_divide);
                editText1 = findViewById(R.id.edit_input_opd1);
                String msg = "";
                if (editText1.getText().toString().isEmpty())
                    msg+="運算元一";
                editText2 = findViewById(R.id.edit_input_opd2);
                if (editText2.getText().toString().isEmpty()) {
                    if (!msg.isEmpty())
                        msg += "及";
                    msg += "運算元二";
                }
                if (!msg.isEmpty()) {
                    Toast.makeText(MainActivity.this, msg + "不得為空值", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent i = new Intent(MainActivity.this, ActivityOperate.class);
                Bundle bundle = new Bundle();
                bundle.putString("value1", editText1.getText().toString());
                bundle.putString("value2", editText2.getText().toString());
//                bundle.putBoolean("isDivided", checkBox.isChecked());
                i.putExtras(bundle);
                launcher_callActivityOpt.launch(i);
//                startActivityForResult(i, REQUEST_CODE_OP);
                
            }
        });
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_OP)
//        {
//            if (resultCode == RESULT_OK) {
//                Bundle bundle = data.getExtras();
//                if (bundle != null) {
//                    Double answer = bundle.getDouble("CALCULATE_RESULT");
//                    TextView textViewOutput = findViewById(R.id.textview_output);
//                    textViewOutput.setText("運算結果" + answer.toString());
//                }
//            }
//        }
//    }
}