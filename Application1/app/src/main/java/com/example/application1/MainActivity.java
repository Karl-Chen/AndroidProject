package com.example.application1;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exam_relativelayout);
//        Button btn = (Button) findViewById(R.id.btn1);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "hi 哩賀！", Toast.LENGTH_LONG).show();
//            }
//        });
//        btn.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(MainActivity.this, "hello 哇賀！", Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });
//        EditText ed = (EditText) findViewById(R.id.edit1);
//        ed.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_UP)
//                    Toast.makeText(MainActivity.this, "keyCode = " + keyCode + ", event = " + event, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });


        //下面那段功用是不用findViewbyId拿物件，但xml最外層要加tools:context=".MainActivity"
//        TextView tv = (TextView) findViewById(R.id.example);
//        tv.setText("Hello 顯示文字練習!");
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }
}