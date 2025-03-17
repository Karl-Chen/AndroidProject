package com.example.modelapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class OrderCartItemActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    OrderCardAdapter orderCardAdapter;
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
        recyclerView = findViewById(R.id.recyclerView_ordercart);
        orderCardAdapter = new OrderCardAdapter(this);
        orderCardAdapter.SetListData();
        orderCardAdapter.setOnItemListener(position -> {
            //取得position
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

}