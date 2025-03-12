package com.example.application2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewActivity extends AppCompatActivity {

    private ListView listVeiw;
    String[] strMenuItem = {"赤肉麵線焿+脆皮炸雞", "五榖瘦肉粥+無骨雞塊", "鮮酥雞肉焿+黃金鮮酥雞", "五殼瘦肉焿+脆皮炸雞","鮮脆雞腿堡+玉米濃湯",
            "鮮酥雞肉焿+烤醬雞堡", "烤醬雞堡+脆皮炸雞", "香檸吉司豬排堡+脆皮炸雞", "赤肉麵線焿+鮮脆雞腿堡", "兩塊脆皮炸雞(腿+塊任選)", "香檸吉司豬排堡+無骨雞塊",
            "五殼瘦肉粥+黃金鮮酥雞", "素肉麵線焿+豬肉可樂餅", "香檸吉司豬排堡+香酥米糕"};
    String[] strPriceItem = {"104", "97", "97", "104", "99", "103", "107", "114", "114", "108", "107", "94", "82", "99"};
    String[] strTitle = {"menu_item", "price_item"};
    int[] intResourceID = {R.id.item_menu, R.id.item_price};
    SimpleAdapter simpleAdapterForListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_view);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        listVeiw = findViewById(R.id.listview_main);
        ArrayList<HashMap<String, String>> arrayListItemData = new ArrayList<>();
        for (int i = 0; i < strMenuItem.length; i++) {
            HashMap<String, String> hashMapItem = new HashMap<>();
            hashMapItem.put("menu_item", strMenuItem[i]);
            hashMapItem.put("price_item", strPriceItem[i]);
            arrayListItemData.add(hashMapItem);
        }
        simpleAdapterForListView = new SimpleAdapter(ListViewActivity.this, arrayListItemData, R.layout.item_layout, strTitle, intResourceID);
        listVeiw.setAdapter(simpleAdapterForListView);
        listVeiw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strMenu = strMenuItem[position];
                String strPrice = strPriceItem[position];
                if (view.getId() == R.id.item_menu)
                {
                    Log.d("E", "點擊餐點名稱");
                }
                else if (view.getId() == R.id.item_price)
                {
                    Log.d("E", "點擊價格");
                }
//                else if (view.getId() == R.layout.item_layout)
//                {
//                    Log.d("E", "點擊項目");
//                }
                Toast.makeText(ListViewActivity.this, "您是點：" + strMenu + " 共 " + strPrice + "元", Toast.LENGTH_LONG).show();
            }
        });


    }
}