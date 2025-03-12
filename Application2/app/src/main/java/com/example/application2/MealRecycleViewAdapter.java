package com.example.application2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class MealRecycleViewAdapter extends RecyclerView.Adapter<MealViewHolder> {

    ArrayList<HashMap<String, String>> mealArrayList;
    Context context;

    public MealRecycleViewAdapter(Context activity)
    {
        context = activity;
    }

    @Override
    public MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item_layout, parent, false);
        return new MealViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MealViewHolder holder, int position) {
        holder.tvMenuItem.setText(mealArrayList.get(position).get("tv_menu_item"));
        holder.tvMealContents.setText(mealArrayList.get(position).get("tv_meal_contents"));
        holder.tvMealPrice.setText(mealArrayList.get(position).get("tv_meal_price"));
    }

    @Override
    public int getItemCount() {
        return mealArrayList == null ? 0 : mealArrayList.size();
    }

    public void setMealArrayList(ArrayList<HashMap<String, String>> inputArrayList) {
        this.mealArrayList = inputArrayList;
        notifyDataSetChanged();
    }
}
