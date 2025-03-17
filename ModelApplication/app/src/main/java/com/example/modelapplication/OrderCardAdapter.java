package com.example.modelapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modelapplication.ui.home.ProductAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderCardAdapter extends RecyclerView.Adapter<OrderCardAdapter.ViewHolder> {

    Context _context;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textProductName, textProductID, textProductPrice, textTotalPrice;
        EditText editVol;
        Button btnDN, btnUP;
        ImageView imgItem;
        View v;


        public ViewHolder(View view) {
            super(view);
            textProductName = itemView.findViewById(R.id.text_productname);
            textProductID = itemView.findViewById(R.id.text_productid);
            textProductPrice = itemView.findViewById(R.id.text_product_price);
            textTotalPrice = itemView.findViewById(R.id.text_total_price);
            editVol = itemView.findViewById(R.id.edit_vol);
            btnDN = itemView.findViewById(R.id.btn_dn);
            btnUP = itemView.findViewById(R.id.btn_up);
        }
    }

    public OrderCardAdapter(Context c) {
        _context = c;
    }

    public void SetListData() {
        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public OrderCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_cards, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strid = ((TextView)view.findViewById(R.id.text_productid)).getText().toString();
                String strName = ((TextView)view.findViewById(R.id.text_name)).getText().toString();
                Log.d("resID", "strProductID: " + strid);
//                PutOrderCartToServer(strid, strName);
            }
        });
        return new OrderCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String strImg = OrderCartConfig.orderCartItemArrayList.get(position).img.toLowerCase();
        if (strImg.endsWith(".jpg") || strImg.endsWith(".png") || strImg.endsWith(".jpeg")) {
            strImg = strImg.substring(0, strImg.lastIndexOf("."));
        }
        int resId = _context.getResources().getIdentifier(strImg, "drawable", _context.getPackageName());
        Log.d("resID", "resID: " + resId + ", strImg: " + strImg);
        holder.textProductName.setText("商品：" + OrderCartConfig.orderCartItemArrayList.get(position).name);
        holder.textProductID.setText(OrderCartConfig.orderCartItemArrayList.get(position).product);
        holder.textProductPrice.setText("價格：" + String.valueOf(OrderCartConfig.orderCartItemArrayList.get(position).price) + "元");
        holder.editVol.setText(String.valueOf(OrderCartConfig.orderCartItemArrayList.get(position).count));
        holder.textTotalPrice.setText("總價：" + String.valueOf(OrderCartConfig.orderCartItemArrayList.get(position).count * OrderCartConfig.orderCartItemArrayList.get(position).price));

        holder.imgItem.setImageResource(resId);
    }



    @Override
    public int getItemCount() {
        return OrderCartConfig.orderCartItemArrayList.size();
    }
}
