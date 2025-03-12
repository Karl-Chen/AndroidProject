package com.example.examapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class InfoAdapter extends RecyclerView.Adapter<InfoItem> {

    ArrayList<HashMap<String, String>> arrayList;
    Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemListener(OnItemClickListener Listener) {
        this.listener = Listener;
    }

    public InfoAdapter(Context c) {
        context = c;
    }

    public void SetListData(ArrayList<HashMap<String, String>> list) {
        arrayList = list;
        notifyDataSetChanged();
    }

    @Override
    public InfoItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item_layout, parent, false);
        return new InfoItem(v, listener);
    }

    @Override
    public void onBindViewHolder(InfoItem holder, int position) {
        holder.tvNum.setText("車站編號：" + arrayList.get(position).get("Number"));
        holder.tvPosition.setText("車站位置：" + arrayList.get(position).get("Position"));
        holder.tvStation.setText("車站站名：" + arrayList.get(position).get("Station"));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
