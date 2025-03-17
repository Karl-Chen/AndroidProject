package com.example.modelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.modelapplication.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import javax.net.ssl.HandshakeCompletedEvent;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private ArrayList<Integer> productTypeIDList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        productTypeIDList = new ArrayList<>();
        GetProductType();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();
        if (id == R.id.action_products) {
//            replaceFragment(fragment, R.id.nav_host_fragment_content_main);

        } else if (id == R.id.action_order_car) {
            finish();
            Intent i = new Intent(MainActivity.this, OrderCartItemActivity.class);
            startActivity(i);

        } else if (id == R.id.action_member) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void GetProductType() {
        String url = UrlConfig.Url + UrlConfig.GetProductSpecification;
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
                        JSONArray arr = new JSONArray(responseData);
                        ProductConfig.productList.clear();
                        productTypeIDList.clear();
                        ProductType alloPt = new ProductType();
                        alloPt.ProductTypeName = "全部商品";
                        alloPt.ProductTypeID = "";
                        ProductConfig.productTypes.add(alloPt);
                        for (int i = 0; i < arr.length(); i++)
                        {
                            ProductType pt = new ProductType();
                            JSONObject obj1 = new JSONObject(arr.get(i).toString());
//                            pt.ProductTypeID = obj1.getString("specificationID");
//                            pt.ProductTypeName = obj1.getString("specificationName");
                            pt.ProductTypeID = obj1.getString("specificationID");
                            pt.ProductTypeName = obj1.getString("specificationName");
                            ProductConfig.productTypes.add(pt);
                        }
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                SetNavigationItem();
                                GetProductList("");
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

    private void GetProductList(String strType) {
        String strUrl = UrlConfig.Url + UrlConfig.GetProductList;
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        boolean isAll = strType == "" ? true : false;
        strType = strType == "" ? "A" : strType;
        HttpUrl url = HttpUrl.parse(strUrl)
                .newBuilder()
                .addQueryParameter("type", strType)
                .addQueryParameter("isAll", String.valueOf(isAll))  // 帶入布林值作為字串
                .build();


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
                        JSONArray arr = new JSONArray(responseData);
                        ProductConfig.productList.clear();

                        for (int i = 0; i < arr.length(); i++)
                        {
                            Product p = new Product();
                            JSONObject obj1 = new JSONObject(arr.get(i).toString());
                            p.ProductID = obj1.getString("productID");
                            p.ProductName = obj1.getString("productName");
                            p.Description = obj1.getString("description");
                            p.Photo = obj1.getString("photo");
                            p.CostJP = obj1.getInt("costJP");
                            p.CostExchangeRate = obj1.getDouble("costExchangeRate");
                            p.PriceExchangeRage = obj1.getDouble("priceExchangeRage");
                            p.Inventory = obj1.getInt("inventory");
                            p.OrderedQuantity = obj1.getInt("orderedQuantity");
                            p.ProductTypeID = obj1.getString("productTypeID");
                            p.ProductSpecificationID = obj1.getString("productSpecificationID");
                            p.BrandID = obj1.getString("brandID");
                            p.SupplierID = obj1.getString("supplierID");
                            ProductConfig.productList.add(p);
                        }
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                UpdateProductListItem();
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
    private void SetNavigationItem() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        Menu menu = navigationView.getMenu();

        for (int i = 0; i < ProductConfig.productTypes.size(); i++)
        {
            ProductConfig.productTypes.get(i).id = View.generateViewId();
            productTypeIDList.add(ProductConfig.productTypes.get(i).id);
            MenuItem item = menu.add(Menu.NONE, ProductConfig.productTypes.get(i).id, Menu.NONE, ProductConfig.productTypes.get(i).ProductTypeName);
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                    int index = productTypeIDList.indexOf(menuItem.getItemId());
                    GetProductList(ProductConfig.productTypes.get(index).ProductTypeID);
                    return true;
                }
            });
        }
    }

    private void UpdateProductListItem() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("update", true);
        getSupportFragmentManager().setFragmentResult("UpDateProduct", bundle);
        Log.d("FragmentManager Check", "Activity's FragmentManager: " + getSupportFragmentManager().toString());

    }

}