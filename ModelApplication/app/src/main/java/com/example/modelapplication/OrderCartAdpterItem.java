package com.example.modelapplication;

import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class OrderCartAdpterItem extends RecyclerView.ViewHolder {
    TextView textProductName, textProductID, textProductPrice, textTotalPrice;
    EditText editVol;
    Button btnDN, btnUP;
    ImageView imgItem;
    View v;

    public OrderCartAdpterItem(View itemView, final OrderCardAdapter.OnItemClickListener listener) {
        super(itemView);
        v = itemView;
        textProductName = itemView.findViewById(R.id.text_productname);
        textProductID = itemView.findViewById(R.id.text_productid);
        textProductPrice = itemView.findViewById(R.id.text_product_price);
        textTotalPrice = itemView.findViewById(R.id.text_total_price);
        editVol = itemView.findViewById(R.id.edit_vol);
        btnDN = itemView.findViewById(R.id.btn_dn);
        btnUP = itemView.findViewById(R.id.btn_up);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int pos = getBindingAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(pos);
                    }
                }
            }
        });
    }
}