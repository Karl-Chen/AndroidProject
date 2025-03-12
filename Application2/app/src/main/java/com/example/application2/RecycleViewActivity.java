package com.example.application2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class RecycleViewActivity extends AppCompatActivity {

    String[] strMenuItem = {"赤肉麵線焿+脆皮炸雞", "五榖瘦肉粥+無骨雞塊", "鮮酥雞肉焿+黃金鮮酥雞", "五殼瘦肉焿+脆皮炸雞","鮮脆雞腿堡+玉米濃湯",
            "鮮酥雞肉焿+烤醬雞堡", "烤醬雞堡+脆皮炸雞", "香檸吉司豬排堡+脆皮炸雞", "赤肉麵線焿+鮮脆雞腿堡", "兩塊脆皮炸雞(腿+塊任選)", "香檸吉司豬排堡+無骨雞塊",
            "五殼瘦肉粥+黃金鮮酥雞", "素肉麵線焿+豬肉可樂餅", "香檸吉司豬排堡+香酥米糕"};
    String[] strPriceItem = {"104", "97", "97", "104", "99", "103", "107", "114", "114", "108", "107", "94", "82", "99"};
    String[] strTitle = {"tv_menu_item", "tv_meal_contents", "tv_meal_price"};

    RecyclerView recyclerView;

    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    MealRecycleViewAdapter adapterMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recycle_view);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        prepareMealData();
        recyclerView = findViewById(R.id.recycle_view_main);
        swipeRefreshLayout = findViewById(R.id.swiperefreshlayout_main);
        adapterMeal = new MealRecycleViewAdapter(this);
        adapterMeal.setMealArrayList(arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapterMeal);
        swipeRefreshLayout.setColorSchemeColors(0x0000ff);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList.clear();
                prepareMealData();
                adapterMeal.setMealArrayList(arrayList);
                swipeRefreshLayout.setRefreshing(false);//取消轉轉轉
            }
        });

    }

    private void prepareMealData() {
        for (int i = 0; i < strMenuItem.length; i++) {
            HashMap<String, String> hmp = new HashMap<>();
            hmp.put("tv_menu_item", (i + 1) + "號餐");
            hmp.put("tv_meal_price", strPriceItem[i]);
            hmp.put("tv_meal_contents", strMenuItem[i]);
            arrayList.add(hmp);
        }

    }
}