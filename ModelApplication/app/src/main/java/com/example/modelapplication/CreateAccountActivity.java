package com.example.modelapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class CreateAccountActivity extends AppCompatActivity {

    private EditText editAcc, editMima, editMima2, editEmail, editAddr, editName, editPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        Button btnCreate = findViewById(R.id.btn_create);
        Button btnBack = findViewById(R.id.btn_back);
        editAcc = findViewById(R.id.edit_acc);
        editMima = findViewById(R.id.edit_mima);
        editMima2 = findViewById(R.id.edit_mima2);
        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
        editAddr = findViewById(R.id.edit_addr);
        editPhone = findViewById(R.id.edit_phone);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strAcc = editAcc.getText().toString();
                String strMima = editMima.getText().toString();
                String strMima2 = editMima2.getText().toString();
                if (!strMima2.equals(strMima)) {
                    Toast.makeText(CreateAccountActivity.this, "確認密碼與密碼不符，請確定您要輸入的密碼", Toast.LENGTH_LONG).show();
                    return;
                }
                String strName = editName.getText().toString();
                String strEmail = editEmail.getText().toString();
                String strAddr = editAddr.getText().toString();
                String strPhone = editPhone.getText().toString();
                String url = UrlConfig.Url + UrlConfig.CreateMember;
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("account", strAcc);
                    jsonObject.put("oldmima", "");
                    jsonObject.put("mima", strMima);
                    jsonObject.put("remima", strMima2);
                    jsonObject.put("name", strName);
                    jsonObject.put("address", strAddr);
                    jsonObject.put("email", strEmail);
                    jsonObject.put("telnumber", strPhone);
                    jsonObject.put("point",0);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                RequestBody requestBody = RequestBody.create(
                        jsonObject.toString(), MediaType.get("application/json; charset=utf-8"));
                Request request = new Request.Builder()
                        .url(url) // `PUT /api/products/{id}`
                        .post(requestBody)
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
                                JSONObject obj = new JSONObject(responseData);
                                MemberConfig.mMmeber.SetMemberID(obj.getString("memberID"));
                                MemberConfig.mMmeber.SetName(obj.getString("name"));
                                MemberConfig.mMmeber.SetAddress(obj.getString("address"));
                                MemberConfig.mMmeber.SetEmail(obj.getString("email"));
                                MemberConfig.mMmeber.SetPoint(obj.getInt("point"));
                                JSONArray arrTel = obj.getJSONArray("memberTel");
                                JSONObject obj1 = new JSONObject(arrTel.get(0).toString());
                                MemberConfig.mMmeber.SetTel(obj1.getString("telNumber"));
                                JSONObject obj2 = new JSONObject(obj.getJSONObject("memberAcc").toString());
                                MemberConfig.mMmeber.SetAccount(obj2.getString("account"));
//                                Intent i = new Intent(CreateAccountActivity.this, LoginActivity.class);
//                                startActivity(i);
                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(CreateAccountActivity.this, "帳號建立完成，請重新登入!", Toast.LENGTH_LONG).show();

                                        finish();
                                    }
                                }, 100);
                                //Toast.makeText(CreateAccountActivity.this, "帳號建立完成，請重新登入!", Toast.LENGTH_LONG);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            System.out.println("Update failed: " + response.message());
                        }
                    }
                });

            }
        });
    }
}