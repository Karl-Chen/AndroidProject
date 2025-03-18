package com.example.modelapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderCartItemActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    OrderCardAdapter orderCardAdapter;
    TextView textTotalPrice;

    Spinner spSendWay;
    EditText editSendAddr, editGetName, editGetPhone;
    CheckBox checkFix;

    String[] items = {"自取", "711 取貨", "全家取貨", "萊爾富取貨", "OK 取貨"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_cart_item);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        textTotalPrice = findViewById(R.id.text_totalprice);
        GetOrderCartList();
        recyclerView = findViewById(R.id.recyclerView_ordercart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderCardAdapter = new OrderCardAdapter(this, textTotalPrice);
        recyclerView.setAdapter(orderCardAdapter);
        spSendWay = findViewById(R.id.spinner_send_way);
        editSendAddr = findViewById(R.id.edit_send_addr);
        editGetName = findViewById(R.id.edit_get_name);
        editGetPhone = findViewById(R.id.edit_get_phone);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spSendWay.setAdapter(adapter);
        spSendWay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = items[i];
                Toast.makeText(OrderCartItemActivity.this, "選擇: " + selectedItem, Toast.LENGTH_SHORT).show();
                if (i == 0) {
                    editSendAddr.setEnabled(true);
                } else {
                    editSendAddr.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Button btnSubmint = findViewById(R.id.btn_order);
        btnSubmint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_products) {
//            replaceFragment(fragment, R.id.nav_host_fragment_content_main);
            finish();
            Intent i = new Intent(OrderCartItemActivity.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.action_order_car) {

        } else if (id == R.id.action_member) {

        }
        return super.onOptionsItemSelected(item);
    }

    public void GetOrderCartList() {
        String url = UrlConfig.Url + UrlConfig.GetOrderCartItem + "/" + MemberConfig.mMmeber.GetAccount();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
//        HttpUrl url = HttpUrl.parse(strUrl)
//                .newBuilder()
//                .addQueryParameter("acc", MemberConfig.mMmeber.GetAccount())
//                .build();


        Request request = new Request.Builder()
                .url(url) // `PUT /api/products/{id}`
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    System.out.println("Update successful: " + responseData);
                    try {
                        JSONObject j = new JSONObject(responseData);
                        OrderCartConfig.isFix = j.getString("isFix");
                        OrderCartConfig.sendWay = j.getString("sendWay");
                        JSONArray arr = j.getJSONArray("item");
                        OrderCartConfig.orderCartItemArrayList.clear();

                        for (int i = 0; i < arr.length(); i++)
                        {
                            OrderCartItem o = new OrderCartItem();
                            JSONObject obj1 = new JSONObject(arr.get(i).toString());
                            o.product = obj1.getString("product");
                            o.name = obj1.getString("name");
                            o.img = obj1.getString("img");
                            o.price = obj1.getInt("price");
                            o.offset = obj1.getDouble("offset");
                            o.count = obj1.getInt("count");
                            o.MaxCount = obj1.getInt("maxCount");

                            OrderCartConfig.orderCartItemArrayList.add(o);
                        }
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int totalPrice = 0;
                                for (var item:
                                        OrderCartConfig.orderCartItemArrayList) {
                                    totalPrice += item.price * item.count;
                                }
                                textTotalPrice.setText("商品總額：" + String.valueOf(totalPrice));
                                orderCardAdapter.SetListData();
                                orderCardAdapter.notifyDataSetChanged();
                            }
                        }, 100);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("Update failed: " + response.message());
                }
            }
        });
    }

    public void PutOrderCartToServer() {
        String url = UrlConfig.Url + UrlConfig.PutOrderCarData + "/" + MemberConfig.mMmeber.GetAccount();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        JSONArray jsonArray = new JSONArray();
        try {
            for (var item:
                 OrderCartConfig.orderCartItemArrayList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("productid", item.product);
                jsonObject.put("count", item.count);
                jsonArray.put(jsonObject);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        RequestBody requestBody = RequestBody.create(
                jsonArray.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url) // `PUT /api/products/{id}`
                .put(requestBody)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    System.out.println("Update successful: " + responseData);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(OrderCartItemActivity.this, responseData, Toast.LENGTH_LONG).show();
                        }
                    }, 100);
                } else {
                    System.out.println("Update failed: " + response.message());
                }
            }
        });
    }

}