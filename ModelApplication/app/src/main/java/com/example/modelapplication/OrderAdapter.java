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

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    Context _context;
    ArrayList<OrderItem> list;
    String strType;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textOrderNo, textOrderDate, textShippingDate, textAddr, textStatus;

        View v;
        Context _context;



        public ViewHolder(View view, Context c) {
            super(view);
            _context = c;
            textOrderNo = itemView.findViewById(R.id.text_orderno);
            textOrderDate = itemView.findViewById(R.id.text_orderdate);
            textShippingDate = itemView.findViewById(R.id.text_shippingdate);
            textAddr = itemView.findViewById(R.id.text_addr);
            textStatus = itemView.findViewById(R.id.text_status);

        }

    }

    public OrderAdapter(Context c, String type) {
        _context = c;
        strType = type;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void SetListData() {
        if (strType == "1") {
            list = OrderConfig.orderItemAllArrayList;
        } else if (strType == "2") {
            list = OrderConfig.orderItemCancelArrayList;
        } else {
            list = OrderConfig.orderItemSuccessArrayList;
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
                String index = strid.split(";")[1];
                int i = Integer.parseInt(index);
                OrderCartConfig.orderCartItemArrayList.remove(i);
                ((OrderCartItemActivity)_context).PutOrderCartToServer();
                Toast.makeText(_context, "您將在購物車內移除商品：" + strName, Toast.LENGTH_LONG).show();
                notifyDataSetChanged();
                return false;
            }
        });
        return new OrderAdapter.ViewHolder(view, _context);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        holder.textOrderNo.setText("訂單編號：" + list.get(position).OrderNo);
        holder.textOrderDate.setText("下訂日期：" + list.get(position).OrderDate);
        holder.textShippingDate.setText("出貨日期：" + list.get(position).ShippingDate);
        holder.textAddr.setText("出貨地址：" + list.get(position).ShippingAddr);
        holder.textStatus.setText("訂單狀態：" + list.get(position).OrdertatusID);
       
    }



    @Override
    public int getItemCount() {
        return OrderCartConfig.orderCartItemArrayList.size();
    }
}