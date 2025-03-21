package com.example.modelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView textDetailSendWay, textDetailSendAddr, textDetailGetName, textDetailGetPhone, textDetailSendDate, textDetailTotalPrice, textDetailSendPrice, textDetailOrderStatus;
    Button btnDetailOrder;
    CheckBox checkBoxDetail;
    OrderDetailAdapter orderDetailAdapter;
    String strType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_detail);
/*        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        String orderNo = getIntent().getStringExtra("orderNo");
        strType = getIntent().getStringExtra("Type");
        GetOrderDetailList(orderNo);
        btnDetailOrder = findViewById(R.id.btn_detail_order);
        btnDetailOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelOrderToServer(orderNo);
            }
        });
        recyclerView = findViewById(R.id.recyclerView_order_detail);
        textDetailSendWay = findViewById(R.id.text_detail_send_way);
        textDetailSendAddr = findViewById(R.id.text_detail_send_addr);
        checkBoxDetail = findViewById(R.id.checkBox_detail);
        textDetailSendDate = findViewById(R.id.text_detail_send_date);
        textDetailGetName = findViewById(R.id.text_detail_get_name);
        textDetailGetPhone = findViewById(R.id.text_detail_get_phone);
        textDetailTotalPrice = findViewById(R.id.text_detail_totalprice);
        textDetailSendPrice = findViewById(R.id.text_detail_send_price);
        textDetailOrderStatus = findViewById(R.id.text_detail_order_status);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderDetailAdapter = new OrderDetailAdapter(this, textDetailTotalPrice);
        recyclerView.setAdapter(orderDetailAdapter);
    }
    private void GetOrderDetailList(String orderNo) {
        String url = UrlConfig.Url + UrlConfig.GetOrderDetailList + "/" + orderNo;
        OkHttpClient client = new OkHttpClient().newBuilder().build();

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
                    if (!(responseData == null || responseData == "[null]")) {
                        try {
                            OrderDetailConfig.OrderDetailArrayList.clear();
                            JSONArray arr = new JSONArray(responseData);
                            if (arr.length() > 0)
                            {
                                JSONObject objOrder = (JSONObject)((JSONObject)arr.get(0)).get("order");
                                OrderDetailConfig.isFix = objOrder.getBoolean("isGoodPackage");
                                OrderDetailConfig.OrderNo = objOrder.getString("orderNo");
                                OrderDetailConfig.ShippingAddr = objOrder.getString("shippingAddr");
                                OrderDetailConfig.ShippingDate = objOrder.getString("shippingDate");
                                OrderDetailConfig.OrderDate = objOrder.getString("orderDate");
                                OrderDetailConfig.OrderName = objOrder.getString("orderName");
                                OrderDetailConfig.OrderPhone = objOrder.getString("orderPhone");
                                OrderDetailConfig.OrderStatusName = ((JSONObject)objOrder.get("ordertatus")).getString("ordertatusName");
                                OrderDetailConfig.OrderStatusID = ((JSONObject)objOrder.get("ordertatus")).getString("ordertatusID");
                                OrderDetailConfig.sendWay = ((JSONObject)objOrder.get("shippingWay")).getString("shippingWayName");
                                if (objOrder.has("invoice") && !objOrder.isNull("invoice")) {
                                    OrderDetailConfig.InvoiceID = ((JSONObject) objOrder.get("invoice")).getString("invoiceNo");
                                }
                            }
                            for (int i = 0; i < arr.length(); i++) {
                                OrderDetailItem o = new OrderDetailItem();
                                String str = arr.get(i).toString();
                                System.out.println("str: " + str);
                                JSONObject obj1 = new JSONObject(str);
                                o.OrderNo = obj1.getString("orderNo");
                                JSONObject obj2 = obj1.getJSONObject("products");
                                o.ProductName = obj2.getString("productName");
                                o.ProductID = obj1.getString("productID");
                                o.Photo = obj2.getString("photo");
                                o.CostJP = obj2.getInt("costJP");
                                o.CostExchangeRate = obj2.getDouble("costExchangeRate");
                                o.PriceExchangeRage = obj2.getDouble("priceExchangeRage");
                                o.Off = obj1.getDouble("off");
                                o.Vol = obj1.getInt("vol");

                                OrderDetailConfig.OrderDetailArrayList.add(o);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            orderDetailAdapter.SetListData();
                            orderDetailAdapter.notifyDataSetChanged();
                            InvaldateUI();
                        }
                    }, 100);
                } else {
                    String responseData = response.body().string();
                    System.out.println("Update failed: " + responseData);
//                    System.out.println("Update failed: " + response.message());

                }
            }
        });
    }

    private void InvaldateUI() {
        textDetailSendWay.setText(OrderDetailConfig.sendWay);
        textDetailGetPhone.setText(OrderDetailConfig.OrderPhone);
        textDetailGetName.setText(OrderDetailConfig.OrderName);
        textDetailOrderStatus.setText(OrderDetailConfig.OrderStatusName);
//        checkBoxDetail.setEnabled(true);
        checkBoxDetail.setChecked(OrderDetailConfig.isFix);
//        checkBoxDetail.setEnabled(false);
        textDetailSendAddr.setText(OrderDetailConfig.ShippingAddr);

        String strShippingDate;
        if (OrderDetailConfig.ShippingDate != null && !OrderDetailConfig.ShippingDate.isEmpty() && OrderDetailConfig.ShippingDate.length() > 10) {
            strShippingDate = OrderDetailConfig.ShippingDate.substring(10);
            textDetailSendDate.setText(strShippingDate);
        }
        int sendPrice = 0;
        if (OrderDetailConfig.isFix)
            sendPrice += 60;
        if (!OrderDetailConfig.sendWay.equals("自取"))
            sendPrice += 60;
        textDetailSendPrice.setText("運費：" + String.valueOf(sendPrice));
        int totalPrice = 0;
        for (var item : OrderDetailConfig.OrderDetailArrayList)
        {
            int uPrice = (int)(item.CostJP * item.PriceExchangeRage);
            totalPrice += uPrice * item.Vol;
        }
        totalPrice += sendPrice;
        textDetailTotalPrice.setText("總價：" + totalPrice);
        if(OrderDetailConfig.OrderStatusID.equals("05") || OrderDetailConfig.OrderStatusID.equals("10") ||OrderDetailConfig.OrderStatusID.equals("11") ||
                OrderDetailConfig.OrderStatusID.equals("02") || OrderDetailConfig.OrderStatusID.equals("03") || OrderDetailConfig.OrderStatusID.equals("04")) {
            btnDetailOrder.setEnabled(false);
        } else {
            btnDetailOrder.setEnabled(true);
        }
    }

    public void CancelOrderToServer(String orderNo) {
        String url = UrlConfig.Url + UrlConfig.CancelOrder + "/" + orderNo + "/" + MemberConfig.mMmeber.GetAccount();
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        RequestBody requestBody = RequestBody.create(
                "", MediaType.get("application/json; charset=utf-8"));
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
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(OrderDetailActivity.this, "此訂單已取消。訂單號碼：" + OrderDetailConfig.OrderNo, Toast.LENGTH_LONG).show();
                            OrderDetailConfig.OrderDetailArrayList.clear();
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("stype", strType);
                            setResult(RESULT_OK, returnIntent);
                            finish(); // 結束 Activity，回到 MainActivity

                        }
                    }, 100);


                } else {
                    String responseData = response.body().string();
                    System.out.println("Update failed: " + responseData);
//                    System.out.println("Update failed: " + response.message());
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(OrderDetailActivity.this, "取消訂單號碼失敗，請聯絡管理員。訂單號碼：" + OrderDetailConfig.OrderNo, Toast.LENGTH_LONG).show();
                        }
                    }, 100);
                }
            }
        });
    }
}