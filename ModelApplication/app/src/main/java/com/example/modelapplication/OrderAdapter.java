package com.example.modelapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> implements DialogCallback{
    Context _context;
    ArrayList<OrderItem> list;
    String strType;



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textOrderNo, textOrderDate, textShippingDate, textAddr, textStatus, textOrderName, textOrderPhone, textPosition;

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
            textOrderName = itemView.findViewById(R.id.text_ordername);
            textOrderPhone = itemView.findViewById(R.id.text_orderphone);
            textPosition = itemView.findViewById(R.id.text_position);
        }

    }

    public OrderAdapter(Context c, String type) {
        _context = c;
        strType = type;
        SetListData();
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

    @Override
    public void DeletItem(int i) {

        String orderNo = list.get(i).OrderNo;
        ((OrderListActivity)_context).CancelOrderToServer(orderNo);
        ((OrderListActivity)_context).GetOrderList("2");
        Toast.makeText(_context, "您將刪除此筆訂單：" + list.get(i).OrderNo, Toast.LENGTH_LONG).show();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
//        ((OrderListActivity)_context).GetOrderList(strType);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(_context, OrderDetailActivity.class);
                String strNo = ((TextView)view.findViewById(R.id.text_orderno)).getText().toString();
                int index = Integer.parseInt(((TextView)view.findViewById(R.id.text_position)).getText().toString());
                i.putExtra("orderNo", list.get(index).OrderNo);
                i.putExtra("type", strType);

                _context.startActivity(i);
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String strNo = ((TextView)view.findViewById(R.id.text_orderno)).getText().toString();
                int i = Integer.parseInt(((TextView)view.findViewById(R.id.text_position)).getText().toString());
                if (!strType.equals("1") || list.get(i).OrdertatusID.equals("02") || list.get(i).OrdertatusID.equals("03")
                        || list.get(i).OrdertatusID.equals("04") || list.get(i).OrdertatusID.equals("05") || list.get(i).OrdertatusID.equals("10")
                        || list.get(i).OrdertatusID.equals("11"))
                    return false;
                PublicFunction.showConfirmDialog(_context, i, OrderAdapter.this, "你將取消此筆訂單嗎？\n訂單號碼：" + strNo);
                return false;
            }
        });
        return new OrderAdapter.ViewHolder(view, _context);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        holder.textOrderNo.setText(list.get(position).OrderNo);
        String strOrderDate = list.get(position).OrderDate;
        strOrderDate = strOrderDate.substring(0, 10);
        holder.textOrderDate.setText(strOrderDate);
        String strShippingDate = list.get(position).ShippingDate == "null" ? "----/--/--" : list.get(position).ShippingDate.substring(0, 10);
        holder.textShippingDate.setText(strShippingDate);
        holder.textAddr.setText("出貨地址：" + list.get(position).ShippingAddr);
        holder.textStatus.setText("訂單狀態：" + list.get(position).OrdertatusName);
        holder.textOrderName.setText("領貨人姓名：" + list.get(position).OrderName);
        holder.textOrderPhone.setText("領貨人電話：" + list.get(position).OrderPhone);
        holder.textPosition.setText(String.valueOf(position));
    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}