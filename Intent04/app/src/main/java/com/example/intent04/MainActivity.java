package com.example.intent04;

import android.content.Intent;
import android.net.Uri;
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
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        Button btnOpenBwowser = findViewById(R.id.btn_browser);
        Button btnDialPhone = findViewById(R.id.btn_dialphone);
        Button btnMap = findViewById(R.id.btn_map);
        btnOpenBwowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editUrl = findViewById(R.id.edit_url);
                String strUrl = editUrl.getText().toString();
                if (strUrl.substring(0, 4) != "http")
                    strUrl = "https://" + strUrl;
                if (!strUrl.isEmpty()) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(strUrl));
                    startActivity(i);
                }
            }
        });
        btnDialPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:+886972013984"));
                startActivity(i);
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("geo:1.00,10.00"));
                startActivity(i);
            }
        });
    }
}