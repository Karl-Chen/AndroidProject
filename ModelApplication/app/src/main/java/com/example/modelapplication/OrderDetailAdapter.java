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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    Context _context;
    TextView textItemsTotalPrice;


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textProductName, textProductID, textProductPrice, textTotalPrice, textMaxVol, outSideTextPrice;
        EditText editVol;
        ImageView imgItem;
        View v;
        Context _context;



        public ViewHolder(View view, TextView oTextTotalPrice, Context c) {
            super(view);
            outSideTextPrice = oTextTotalPrice;
            _context = c;
            textProductName = itemView.findViewById(R.id.text_detail_productname);
            textProductID = itemView.findViewById(R.id.text_detail_productid);
            textMaxVol = itemView.findViewById(R.id.text_detail_maxvol);
            textProductPrice = itemView.findViewById(R.id.text_detail_product_price);
            textTotalPrice = itemView.findViewById(R.id.text_detail_total_price);
            editVol = itemView.findViewById(R.id.edit_detail_vol);

            imgItem = itemView.findViewById(R.id.img_detail_item);
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

    }

    public OrderDetailAdapter(Context c, TextView tv) {
        _context = c;
        textItemsTotalPrice = tv;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void SetListData() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_item, parent, false);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String strid = ((TextView)view.findViewById(R.id.text_productid)).getText().toString();
//                String strName = ((TextView)view.findViewById(R.id.text_productname)).getText().toString();
//                Log.d("resID", "strProductID: " + strid);
////                PutOrderCartToServer(strid, strName);
//            }
//        });
//        view.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                String strid = ((TextView)view.findViewById(R.id.text_productid)).getText().toString();
//                String strName = ((TextView)view.findViewById(R.id.text_productname)).getText().toString();
//                Log.d("resID", "strProductID: " + strid);
//                String index = strid.split(";")[1];
//                int i = Integer.parseInt(index);
//                PublicFunction.showConfirmDialog(_context, i, OrderDetailAdapter.this, "你將刪除購物車內此商品嗎？" + strName);
//
//                return false;
//            }
//        });
        return new OrderDetailAdapter.ViewHolder(view, textItemsTotalPrice, _context);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String strImg = OrderDetailConfig.OrderDetailArrayList.get(position).Photo.toLowerCase();
        if (strImg.endsWith(".jpg") || strImg.endsWith(".png") || strImg.endsWith(".jpeg")) {
            strImg = strImg.substring(0, strImg.lastIndexOf("."));
        }
        int resId = _context.getResources().getIdentifier(strImg, "drawable", _context.getPackageName());
        Log.d("resID", "resID: " + resId + ", strImg: " + strImg);
        holder.textProductName.setText("商品：" + OrderDetailConfig.OrderDetailArrayList.get(position).ProductName);
        holder.textProductID.setText(OrderDetailConfig.OrderDetailArrayList.get(position).ProductID + ";" + position);
        int uPrice = (int)(OrderDetailConfig.OrderDetailArrayList.get(position).CostJP * OrderDetailConfig.OrderDetailArrayList.get(position).PriceExchangeRage);
        holder.textProductPrice.setText("單價：" + String.valueOf(OrderDetailConfig.OrderDetailArrayList.get(position).CostJP * OrderDetailConfig.OrderDetailArrayList.get(position).PriceExchangeRage) + "元");
        holder.editVol.setText(String.valueOf(OrderDetailConfig.OrderDetailArrayList.get(position).Vol));
        int totalPrice =(int)(uPrice * OrderDetailConfig.OrderDetailArrayList.get(position).Vol);
        holder.textTotalPrice.setText("總價：" + String.valueOf(totalPrice));

        holder.imgItem.setImageResource(resId);
    }




    @Override
    public int getItemCount() {
        return OrderDetailConfig.OrderDetailArrayList.size();
    }
}

