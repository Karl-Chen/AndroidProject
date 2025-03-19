package com.example.modelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrderListActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        adapter = new ViewPagerAdapter(this);
        adapter.addFragment(new PageFragment("1", this));
        adapter.addFragment(new PageFragment("2", this));
        adapter.addFragment(new PageFragment("3", this));
        viewPager.setAdapter(adapter);

        // 連接 TabLayout 和 ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("未完成的訂單");
            } else if (position == 1) {
                tab.setText("己完成的訂單");
            } else {
                tab.setText("己取消的訂單");
            }
        }).attach();
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
            Intent i = new Intent(OrderListActivity.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.action_order_car) {
            finish();
            Intent i = new Intent(OrderListActivity.this, OrderCartItemActivity.class);
            startActivity(i);
        } else if (id == R.id.action_member) {

        }
        return super.onOptionsItemSelected(item);
    }

    private static class ViewPagerAdapter extends FragmentStateAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();

        public ViewPagerAdapter(AppCompatActivity activity) {
            super(activity);
        }

        public void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        public void updateFragment(int position) {
            if (position >= 0 && position < fragmentList.size()) {
                ((PageFragment)fragmentList.get(position)).updateContent();
                notifyItemChanged(position);
            }
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }

    public void GetOrderList(String type) {
        String url = UrlConfig.Url + UrlConfig.GetOrderList + "/" + MemberConfig.mMmeber.GetAccount() + "/" + type;
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
                    try {
                        JSONObject j = new JSONObject(responseData);
                        JSONArray arr = j.getJSONArray("item");
                        if (type == "1")
                            OrderConfig.orderItemAllArrayList.clear();
                        else if (type == "2")
                            OrderConfig.orderItemCancelArrayList.clear();
                        else if (type == "3")
                            OrderConfig.orderItemSuccessArrayList.clear();
                        for (int i = 0; i < arr.length(); i++)
                        {
                            OrderItem o = new OrderItem();
                            JSONObject obj1 = new JSONObject(arr.get(i).toString());
                            o.OrderNo = obj1.getString("OrderNo");
                            o.IsGoodPackage = obj1.getBoolean("isGoodPackage");
                            o.OrderDate = obj1.getString("orderDate");
                            o.ShippingDate = obj1.getString("shippingDate");
                            o.ShippingAddr = obj1.getString("shippingAddr");
                            o.OrderName = obj1.getString("orderName");
                            o.OrderPhone = obj1.getString("orderPhone");
                            o.MemberID = obj1.getString("memberID");
                            JSONObject obj2 = obj1.getJSONObject("payWay");
                            o.PayWayID = obj2.getString("payWayName");
                            JSONObject obj3 = obj1.getJSONObject("ordertatus");
                            o.OrdertatusID = obj3.getString("ordertatusName");
                            JSONObject obj4 = obj1.getJSONObject("shippingWay");
                            o.ShippingWayID = obj1.getString("shippingWayName");

                            if (type == "1")
                                OrderConfig.orderItemAllArrayList.add(o);
                            else if (type == "2")
                                OrderConfig.orderItemCancelArrayList.add(o);
                            else if (type == "3")
                                OrderConfig.orderItemSuccessArrayList.add(o);
                        }
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int index = Integer.parseInt(type) - 1;
                                adapter.updateFragment(index);
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
}