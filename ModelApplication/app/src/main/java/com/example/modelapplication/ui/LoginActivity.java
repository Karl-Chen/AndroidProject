package com.example.modelapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.modelapplication.CreateAccountActivity;
import com.example.modelapplication.MainActivity;
import com.example.modelapplication.Member;
import com.example.modelapplication.MemberConfig;
import com.example.modelapplication.R;

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

public class LoginActivity extends AppCompatActivity {

    private EditText editTextAcc, editTextMima;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextAcc = findViewById(R.id.edit_acc);
        editTextMima = findViewById(R.id.edit_mima);

        ImageView img = findViewById(R.id.img_login);
        Glide.with(this).asGif().load(R.drawable.sf_fullstrike).into(img);

        Button btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strAcc = editTextAcc.getText().toString();
                String strMima = editTextMima.getText().toString();
                String url = MemberConfig.Url + MemberConfig.Login;
                OkHttpClient client = new OkHttpClient();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("account", strAcc);
                    jsonObject.put("mima", strMima);
                    jsonObject.put("memberID", "");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                RequestBody requestBody = RequestBody.create(
                        jsonObject.toString(), MediaType.get("application/json; charset=utf-8"));
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
                        if (response.isSuccessful() && response.body() != null) {
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
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
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

        Button btnCreate = findViewById(R.id.btn_create);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(i);
            }
        });
    }
}