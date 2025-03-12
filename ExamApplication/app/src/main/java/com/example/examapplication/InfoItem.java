package com.example.examapplication;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class InfoItem extends RecyclerView.ViewHolder {
    TextView tvNum, tvStation, tvPosition;
    View v;

    public InfoItem(View itemView, final InfoAdapter.OnItemClickListener listener) {
        super(itemView);
        v = itemView;
        tvNum = itemView.findViewById(R.id.tv_num);
        tvStation = itemView.findViewById(R.id.tv_station);
        tvPosition = itemView.findViewById(R.id.tv_lat_lng);
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
