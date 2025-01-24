package com.example.intent02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        setContentView(R.layout.activity_main);
        Button btnCtoF = findViewById(R.id.btn_ctof);
        btnCtoF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextTempC = findViewById(R.id.edit_temp_c);
                Bundle bundle_01 = new Bundle();
                bundle_01.putString("TEMP_C_01", editTextTempC.getText().toString());
                Intent intent01 = new Intent(MainActivity.this, ActivityTemperatureF.class);
                intent01.putExtras(bundle_01);
                startActivity(intent01);
            }
        });
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }
}