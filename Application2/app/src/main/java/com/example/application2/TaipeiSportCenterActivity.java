package com.example.application2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TaipeiSportCenterActivity extends AppCompatActivity {

    ListView lsitView;
    TextView tvTitle;
    ArrayList<String> taipeiSportCenterList;
    ArrayAdapter<String> taipeiSportCenterAdapter;

    String strUrl = "https://data.taipei/api/v1/dataset/e7c46724-3517-4ce5-844f-5a4404897b7d?scope=resourceAquire";
    String strTaipeiSportCenter = "台北市運動中心資料";
    String strCenterName ,strCenterAddr, strLat, strLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_taipei_sport_center);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        Button btnGetData = findViewById(R.id.btn_taipei_sport_center_get_data);
        Button btnClearData = findViewById(R.id.btn_taipei_sport_center_clear_data);
        lsitView = findViewById(R.id.lv_taipei_sport_center_data);
        tvTitle = findViewById(R.id.tv_taipei_sport_center_title);

        taipeiSportCenterList = new ArrayList<String>();
        btnClearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((taipeiSportCenterList != null) && (taipeiSportCenterAdapter != null)) {
                    taipeiSportCenterList.clear();
                    taipeiSportCenterAdapter.notifyDataSetChanged();
                    tvTitle.setText(strTaipeiSportCenter + "\nAPI資料已清空....");

                }
            }
        });

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strUrl, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject jsonObject_result = response.getJSONObject("result");
                                JSONArray jsonArray_results = jsonObject_result.getJSONArray("results");
                                for (int i = 0; i < jsonArray_results.length(); i++) {
                                    JSONObject jsonObjectSportCenter = jsonArray_results.getJSONObject(i);
                                    strCenterName = jsonObjectSportCenter.getString("名稱");
                                    strCenterAddr = jsonObjectSportCenter.getString("地址");
                                    strLng = jsonObjectSportCenter.getString("經度");
                                    strLat = jsonObjectSportCenter.getString("緯度");
                                    taipeiSportCenterList.add((i + 1) + ". " + strCenterName + "\n" + strCenterAddr + "\n座標：" + strLat + "," + strLng);
                                }
                                taipeiSportCenterAdapter = new ArrayAdapter<String>(TaipeiSportCenterActivity.this, android.R.layout.simple_list_item_1,
                                        taipeiSportCenterList);
                                lsitView.setAdapter(taipeiSportCenterAdapter);
                                tvTitle.setText(strTaipeiSportCenter + "\n資料取得成功...");

                                lsitView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String strItem = taipeiSportCenterList.get(position);
                                        int intStartOfGeo = strItem.indexOf("座標：");
                                        String strGeoPosition = strItem.substring(intStartOfGeo + 3);
                                        String strArrayLatLng[] = strGeoPosition.split(",");
                                        String strLabel = strGeoPosition + "(運動中心位置)";
                                        String strQuery = Uri.decode(strLabel);
                                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + strGeoPosition + "?q=" + strQuery + "&z=32"));
                                        startActivity(i);
                                    }
                                });

                            } catch (JSONException e) {
                                tvTitle.setText(strTaipeiSportCenter + "\nAPI資料解析失敗，錯誤訊息：\n" + e.getMessage());
                                throw new RuntimeException(e);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            tvTitle.setText(strTaipeiSportCenter + "\nAPI資料取得失敗，錯誤訊息：\n" + error.getMessage());
                        }
                    });
                    //異步連線，Volly要在build.gradel加入implementation 'com.android.volley:volley:1.2.1' 才能import使用
                    Volley.newRequestQueue(TaipeiSportCenterActivity.this).add(request);
                    tvTitle.setText(strTaipeiSportCenter + "\nAPI資料讀取中....");
                } catch (Exception ex) {
                    tvTitle.setText(strTaipeiSportCenter + "\n嘗試發送API請求失敗，錯誤訊息：\n" + ex.getMessage());
                }
            }
        });
    }
}