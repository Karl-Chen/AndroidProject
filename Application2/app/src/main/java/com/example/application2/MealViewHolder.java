package com.example.application2;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MealViewHolder extends RecyclerView.ViewHolder {
    TextView tvMenuItem, tvMealContents, tvMealPrice;
    View mItemView;

    public MealViewHolder(View itemView) {
        super(itemView);
        tvMealContents = itemView.findViewById(R.id.tv_meal_contents);
        tvMenuItem = itemView.findViewById(R.id.tv_menu_item);
        tvMealPrice = itemView.findViewById(R.id.tv_meal_price);
        mItemView = itemView;
    }
}
