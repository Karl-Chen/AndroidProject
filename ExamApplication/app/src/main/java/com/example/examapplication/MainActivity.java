package com.example.examapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    final String fileName = "MRKPosition.txt";
    TextView tvTitle;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;

    InfoAdapter adapterInfo;


    String strUrl = "https://api.kcg.gov.tw/api/service/Get/4278fc6a-c3ea-4192-8ce0-40f00cdb40dd";

    ArrayList<HashMap<String, String>> listStationInfo;
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


        listStationInfo = new ArrayList<HashMap<String, String>>();

        tvTitle = findViewById(R.id.tv_title);
        recyclerView = findViewById(R.id.recycle_view);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.sr_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listStationInfo.clear();
                progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1000);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }).start();
                JsonRequestLink();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        adapterInfo = new InfoAdapter(this);
        adapterInfo.SetListData(listStationInfo);
        adapterInfo.setOnItemListener(position -> {
                String strItem = listStationInfo.get(position).get("Position");
                String strStation = listStationInfo.get(position).get("Station");
                String strLabel = strItem + "(" + strStation + ")";
                String strQuery = Uri.decode(strLabel);
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + strItem + "?q=" + strQuery + "&z=32"));
                startActivity(i);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterInfo);


        Button btnGetInfo = findViewById(R.id.btn_get_data);
        btnGetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listStationInfo.clear();
                progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1000);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }).start();
                JsonRequestLink();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        Button btnClearData = findViewById(R.id.btn_clear_data);
        btnClearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listStationInfo.clear();
                adapterInfo.notifyDataSetChanged();
                tvTitle.setText("高雄捷運站資訊表\n資料已清空");
            }
        });

    }

    private void JsonRequestLink() {
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (!HandleAdapter(response)) {
                        String json = ReadBackUPPosition();
                        try {
                            JSONObject object = new JSONObject(json);
                            HandleAdapter(object);
                            tvTitle.setText("高雄捷運站資訊表\n離線資料讀取完畢");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        WriteJsonToFile(response.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    tvTitle.setText("高雄捷運站資訊表" + "\nAPI資料取得失敗，錯誤訊息：\n" + error.getMessage());
                    String json = ReadBackUPPosition();
                    try {
                        JSONObject object = new JSONObject(json);
                        HandleAdapter(object);
                        tvTitle.setText("高雄捷運站資訊表\n離線資料讀取完畢");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            Volley.newRequestQueue(this).add(request);
            tvTitle.setText("高雄捷運站資訊表" + "\nAPI資料讀取中....");
        } catch (Exception ex) {
            tvTitle.setText("高雄捷運站資訊表" + "\n嘗試發送API請求失敗，錯誤訊息：\n" + ex.getMessage());
            String json = ReadBackUPPosition();
            try {
                JSONObject object = new JSONObject(json);
                HandleAdapter(object);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean HandleAdapter(JSONObject response)
    {
        try {
            JSONArray arr = response.getJSONArray("data");
            for (int i = 0; i < arr.length(); i++)
            {
                JSONObject jsonItem = arr.getJSONObject(i);
                HashMap<String, String> tmpItem = JsonItemToHashItem(jsonItem);
                listStationInfo.add(tmpItem);
            }
            adapterInfo.notifyDataSetChanged();
            tvTitle.setText("高雄捷運站資訊表\n資料讀取完畢");
            return true;
        } catch (JSONException e) {
            tvTitle.setText("高雄捷運站資訊表" + "\nAPI舊資料解析失敗，錯誤訊息：\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private HashMap<String, String> JsonItemToHashItem(JSONObject jsonItem) {
        HashMap<String, String> retItem = new HashMap<String, String>();
        try {
            String lat = jsonItem.getString("車站緯度");
            String lng = jsonItem.getString("車站經度");
            retItem.put("Number", jsonItem.getString("車站編號"));
            retItem.put("Station", jsonItem.getString("車站中文名稱"));
            retItem.put("Position", lat + "," + lng);
        } catch (JSONException ex)
        {
            tvTitle.setText("高雄捷運站資訊表" + "\nAPI資料解析失敗，錯誤訊息：\n" + ex.getMessage());
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return  retItem;
    }

    private String ReadBackUPPosition() {
        String json = "";
        try (FileInputStream fis = openFileInput(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            json = sb.toString();
        } catch (FileNotFoundException e) {
            tvTitle.setText("高雄捷運站資訊表" + "\nAPI離線資料解析失敗，錯誤訊息：\n" + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            tvTitle.setText("高雄捷運站資訊表" + "\nAPI離線資料讀取失敗，錯誤訊息：\n" + e.getMessage());
            throw new RuntimeException(e);
        }
        return json;
    }

    private boolean WriteJsonToFile(String json) {

        try (FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
        } catch (IOException e) {
            return false;
        }
        return true;

    }
}