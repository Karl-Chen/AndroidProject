package com.example.modelapplication.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.modelapplication.Product;
import com.example.modelapplication.ProductConfig;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private ArrayList<Product> productList = new ArrayList<>();

    public ArrayList<Product> getProductList() {
        return productList;
    }

    // 更新資料
    public void setProductList(List<Product> newProductList) {
        productList = ProductConfig.productList;
    }
}