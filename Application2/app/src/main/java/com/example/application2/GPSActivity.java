package com.example.application2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.Manifest;

public class GPSActivity extends AppCompatActivity implements LocationListener {

    private TextView tvGPSInfo;
    private LocationManager lm;
    private Location currentLocation;
    private String bestProvider;
    private static final int PERMISSION_REQUEST_GPS = 1101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gpsactivity);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        tvGPSInfo = findViewById(R.id.text_gps_info);
        Button btnGPSSet = findViewById(R.id.btn_set_gps);
        Button btnGPSStart = findViewById(R.id.btn_start_gps);
        Button btnMapShow = findViewById(R.id.btn_show_map);
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        checkGPSProviderStatus(lm);
        checkSDKVersion();

        btnGPSSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        });
        btnGPSStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat, lng;
                String strOutput;
                bestProvider = "gps";//lm.getBestProvider(new Criteria(), true);
                try {
                    if (bestProvider != null) {
                        lm.requestLocationUpdates(bestProvider, 1000, 1.0f, GPSActivity.this);
                        currentLocation = lm.getLastKnownLocation(bestProvider);
                        if (currentLocation != null) {
                            lat = currentLocation.getLatitude();
                            lng = currentLocation.getLongitude();
                            strOutput = "GPS資訊：啟動 GPS 更新成功!! \n定位資料提供者 ： " + bestProvider +
                                    "\n 目前座標，緯度 ： " + lat + "\n - 經度 ： " + lng;
                        } else {
                            strOutput = "GPS資訊：啟動 GPS 更新失敗";
                        }
                        tvGPSInfo.setText(strOutput);
                    }
                } catch (SecurityException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnMapShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLocation == null)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GPSActivity.this);
                    builder.setTitle("GPS 地圖功能");
                    builder.setMessage("目前GPS自動更新功能尚末完全啟用，請先按鈕啟動GPS更新，否則無法正確示地圖座標");
                    builder.setPositiveButton("確定", null);
                    builder.create();
                    builder.show();

                    return;}
                double lat, lng;
                String label = "目前位置";
                String geoString, queryString, uriString;
                lat = currentLocation.getLatitude();
                lng = currentLocation.getLongitude();
                geoString = "geo:" + lat + "," + lng;
                queryString = lat + "," + lng + "(" + label + ")";
                uriString = Uri.encode(queryString);
                geoString += "?q=" + uriString;

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(geoString));
                startActivity(i);
            }
        });
    }

    private void checkGPSProviderStatus(LocationManager lm) {
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("GPS 硬體功能設定");
            b.setMessage("GPS 硬體裝置未啟用!!\n請先啟用 GPS 裝置功能，否則無法接收位置資訊...");
            b.setPositiveButton("已充份了解了", null);
            b.setNegativeButton("我不要", null);
            b.create();
            b.show();
        }
        else {
            Toast.makeText(this, "GPS 裝置已啟用", Toast.LENGTH_LONG).show();
        }
    }

    private void checkSDKVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_GPS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        String strOutput;
        if (requestCode == PERMISSION_REQUEST_GPS) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    strOutput = "GPS 資訊：要求精確 GPS 裝置存取權限成功 !!";
                } else {
                    strOutput = "GPS 資訊：要求精確 GPS 裝置存取權限失敗...";
                }
            } else {
                strOutput = "GPS 資訊：系統未成功回應 GPS 裝置授權要求...";
            }
            tvGPSInfo.setText(strOutput);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat, lng;
        String strOutput;
        if (location != null) {
            currentLocation = location;
            lat = currentLocation.getLatitude();
            lng = currentLocation.getLongitude();
            strOutput = "GPS 資訊 ： GPS 座標更新成功!! \n定位資料提供者 ： " + bestProvider +
                    "\n 目前座標，緯度 ： " + lat + "\n - 經度 ： " + lng;
            tvGPSInfo.setText(strOutput);
        } else {
            strOutput = "GPS 資訊 ： GPS 座標更新失敗.... onLocationChanged -> location 為 null";
            tvGPSInfo.setText(strOutput);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (lm != null)
        {
            lm.removeUpdates(this);
            Log.d("GPSActiviy", "GPS 訊號自動更新功能關閉!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lm != null) {
            int minFlashTime = 1000;
            float minFlashDistance = 1.0f;
            String strOutput;
            if (bestProvider == null || bestProvider.equals("")) {
                bestProvider = "gps";
            }
            try {
                lm.requestLocationUpdates(bestProvider, minFlashTime, minFlashDistance, this);
                strOutput = "GPS資訊：啟動 GPS 更新成功!! \n定位資料提供者 ： " + bestProvider;
            } catch (SecurityException ex) {
                strOutput = "GPS資訊：啟動 GPS 更新失敗... \n發生SecurityException，錯誤訊息：" + ex.getMessage();
                ex.printStackTrace();
            }
            Log.d("GPSActivity", strOutput);
            tvGPSInfo.setText(strOutput);
        }
    }
}