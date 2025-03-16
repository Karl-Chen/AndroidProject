package com.example.modelapplication.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modelapplication.ProductConfig;
import com.example.modelapplication.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context _context;
    public ProductAdapter(Context context)
    {
        _context = context;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textName, textDesc, textPrice, textProductID;


        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            textName = view.findViewById(R.id.text_name);
            textDesc = view.findViewById(R.id.text_desc);
            textPrice = view.findViewById(R.id.text_price);
            textProductID = view.findViewById(R.id.text_productid);
        }
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_cards, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strid = ((TextView)view.findViewById(R.id.text_productid)).getText().toString();
                Log.d("resID", "strProductID: " + strid);
            }
        });
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {

        String strImg = ProductConfig.productList.get(position).Photo.toLowerCase();
        if (strImg.endsWith(".jpg") || strImg.endsWith(".png") || strImg.endsWith(".jpeg")) {
            strImg = strImg.substring(0, strImg.lastIndexOf("."));
        }
        int resId = _context.getResources().getIdentifier(strImg, "drawable", _context.getPackageName());
        Log.d("resID", "resID: " + resId + ", strImg: " + strImg);
        holder.textName.setText("名稱：" + ProductConfig.productList.get(position).ProductName);
        holder.textDesc.setText("簡介：" + ProductConfig.productList.get(position).Description);
        holder.textPrice.setText("價格：" + String.valueOf((int)(ProductConfig.productList.get(position).PriceExchangeRage * ProductConfig.productList.get(position).CostJP)) + "元");
        holder.textProductID.setText(ProductConfig.productList.get(position).ProductID);
        holder.imageView.setImageResource(resId);

    }

    public void updateData() {
        notifyDataSetChanged();
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return ProductConfig.productList.size();
    }
}
