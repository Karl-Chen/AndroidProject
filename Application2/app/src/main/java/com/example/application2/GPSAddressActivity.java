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

public class GPSAddressActivity extends AppCompatActivity {

    TextView tvGPSResult;
    EditText edAddr;
    String addressName, strOutput;
    Geocoder geo;
    List<Address> lat_lng_list;
    double lat, lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gpsaddress);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        tvGPSResult = findViewById(R.id.text_result_lat_lng);
        edAddr = findViewById(R.id.edit_address);
        Button btnQuery = findViewById(R.id.btn_query);
        geo = new Geocoder(GPSAddressActivity.this, Locale.TAIWAN);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressName = edAddr.getText().toString();
                try {

                    lat_lng_list = geo.getFromLocationName(addressName, 1);
                    if (lat_lng_list != null && !lat_lng_list.isEmpty()) {
                        Address tempAddress = lat_lng_list.get(0);
                        lat = tempAddress.getLatitude();
                        lng = tempAddress.getLongitude();
                        strOutput = "\n緯度：" + lat + "\n經度：" + lng;
                    } else {
                        if (lat_lng_list != null)
                            strOutput = "\nlat_lng_list = null, getFromLocationName() 函式呼叫失敗";
                        else
                            strOutput = "\n回傳的AddressList內容為空，輸入的地址/地名沒有符合的經緯度座標資料被回覆";
                    }
                    tvGPSResult.setText("位置座標查詢結果：" + strOutput);
                } catch (IOException ex) {
                    tvGPSResult.setText("位置座標查詢，發生例外狀況：" + ex.getMessage());
                }
            }
        });
    }
}