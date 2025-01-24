package com.example.application2;

import android.location.Address;
import android.location.Geocoder;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPSDecodeActivity extends AppCompatActivity {

    private TextView tvResultAddr;
    private EditText edLat, edLng;

    private Geocoder geo;
    private List<Address> list_address;

    private double lat, lng;
    private static final int MAX_RESULT_LENGHT = 300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gpsdecode);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        tvResultAddr = findViewById(R.id.text_result_address);
        edLat = findViewById(R.id.edit_latitude);
        edLng = findViewById(R.id.edit_longitude);
        Button btnQueryAddr = findViewById(R.id.btn_query_address);
        geo = new Geocoder(this, Locale.TAIWAN);
        btnQueryAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strOutput = "";

                lat = Double.parseDouble(edLat.getText().toString());
                lng = Double.parseDouble(edLng.getText().toString());
                try {
                    list_address = geo.getFromLocation(lat, lng, MAX_RESULT_LENGHT);
                    if (list_address != null) {
                        if (!list_address.isEmpty()) {
                            for (int i = 0; i < list_address.size(); i++) {
                                Address tmpaddr = list_address.get(i);
                                strOutput += (i + 1) + " : ";
                                for (int j = 0; j <= tmpaddr.getMaxAddressLineIndex(); j++) {
                                    strOutput += tmpaddr.getAddressLine(j) + ", ";
                                }
                                strOutput+="\n";
                            }

                        } else {
                            strOutput = "查詢結果 ： 回傳的AddressList內容為空，輸入的 GPS 座標位置沒有符合的地址回傳!!";
                        }
                    } else {
                        strOutput = "AddressList = null， getFromLocation() 函數呼叫失敗";
                    }
                } catch (IOException ex) {
                    strOutput = "位置座標查詢失敗，發生例外狀況：" + ex.getMessage();
                }
                tvResultAddr.setText(strOutput);
            }
        });
    }
}