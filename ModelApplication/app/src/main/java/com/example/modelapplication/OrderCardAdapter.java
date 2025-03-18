package com.example.modelapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderCardAdapter extends RecyclerView.Adapter<OrderCardAdapter.ViewHolder> {

    Context _context;
    TextView textItemsTotalPrice;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textProductName, textProductID, textProductPrice, textTotalPrice, textMaxVol, outSideTextPrice;
        EditText editVol;
        Button btnDN, btnUP;
        ImageView imgItem;
        View v;
        Context _context;



        public ViewHolder(View view, TextView oTextTotalPrice, Context c) {
            super(view);
            outSideTextPrice = oTextTotalPrice;
            _context = c;
            textProductName = itemView.findViewById(R.id.text_productname);
            textProductID = itemView.findViewById(R.id.text_productid);
            textMaxVol = itemView.findViewById(R.id.text_maxvol);
            textProductPrice = itemView.findViewById(R.id.text_product_price);
            textTotalPrice = itemView.findViewById(R.id.text_total_price);
            editVol = itemView.findViewById(R.id.edit_vol);
            btnDN = itemView.findViewById(R.id.btn_dn);
            btnDN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int vol = Integer.parseInt(editVol.getText().toString());
                    vol--;
                    if (vol < 1)
                        vol = 1;
                    editVol.setText(String.valueOf(vol));
                    HandlerOrderItemsData(vol);
                    CacUnitTotalPrice();
                    CacAllTotalPrice();
                }
            });
            btnUP = itemView.findViewById(R.id.btn_up);
            btnUP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int vol = Integer.parseInt(editVol.getText().toString());
                    int maxVol = Integer.parseInt(textMaxVol.getText().toString());
                    vol++;
                    if (vol > maxVol)
                        vol = maxVol;
                    editVol.setText(String.valueOf(vol));
                    HandlerOrderItemsData(vol);
                    CacUnitTotalPrice();
                    CacAllTotalPrice();
                }
            });
            imgItem = itemView.findViewById(R.id.img_item);
        }

        private void CacUnitTotalPrice() {
            int vol = Integer.parseInt(editVol.getText().toString());
            String price = textProductPrice.getText().toString();
            price = price.replace("價格：", "");
            price = price.replace("元", "");
            int uPrice = Integer.parseInt(price);
            int totalPrice = uPrice * vol;
            textTotalPrice.setText("總價：" + String.valueOf(totalPrice) + "元");
        }
        private void CacAllTotalPrice () {
            int totalPrice = 0;
            for (var item:
                 OrderCartConfig.orderCartItemArrayList) {
                totalPrice += item.price * item.count;
            }
            outSideTextPrice.setText("商品總額：" + String.valueOf(totalPrice) + "元");
        }
        private void HandlerOrderItemsData(int vol)
        {
            //String str[] = textProductID.getText().toString().split(";");
            int position = getAdapterPosition();
            OrderCartConfig.orderCartItemArrayList.get(position).count = vol;
            ((OrderCartItemActivity)_context).PutOrderCartToServer();
        }

    }

    public OrderCardAdapter(Context c, TextView tv) {
        _context = c;
        textItemsTotalPrice = tv;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void SetListData() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_cart_item, parent, false);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String strid = ((TextView)view.findViewById(R.id.text_productid)).getText().toString();
//                String strName = ((TextView)view.findViewById(R.id.text_productname)).getText().toString();
//                Log.d("resID", "strProductID: " + strid);
////                PutOrderCartToServer(strid, strName);
//            }
//        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String strid = ((TextView)view.findViewById(R.id.text_productid)).getText().toString();
                String strName = ((TextView)view.findViewById(R.id.text_productname)).getText().toString();
                Log.d("resID", "strProductID: " + strid);

                Toast.makeText(_context, "您將在購物車內移除商品：" + strName, Toast.LENGTH_LONG).show();
                return false;
            }
        });
        return new OrderCardAdapter.ViewHolder(view, textItemsTotalPrice, _context);
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
        holder.textProductID.setText(OrderCartConfig.orderCartItemArrayList.get(position).product + ";" + position);
        holder.textMaxVol.setText(String.valueOf(OrderCartConfig.orderCartItemArrayList.get(position).MaxCount));
        holder.textProductPrice.setText("價格：" + String.valueOf(OrderCartConfig.orderCartItemArrayList.get(position).price) + "元");
        holder.editVol.setText(String.valueOf(OrderCartConfig.orderCartItemArrayList.get(position).count));
        int totalPrice = OrderCartConfig.orderCartItemArrayList.get(position).price * OrderCartConfig.orderCartItemArrayList.get(position).count;
        holder.textTotalPrice.setText("總價：" + String.valueOf(totalPrice));

        holder.imgItem.setImageResource(resId);
    }



    @Override
    public int getItemCount() {
        return OrderCartConfig.orderCartItemArrayList.size();
    }
}
