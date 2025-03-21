package com.example.modelapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class PageFragment extends Fragment {
    private String page;
    private Context _context;
    OrderAdapter adapter;
    RecyclerView recyclerView;
    TextView textErr;

    public PageFragment(String page, Context context) {
        this.page = page;
        _context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        textErr = view.findViewById(R.id.text_err);
        recyclerView = view.findViewById(R.id.recyclerView_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(_context));
        adapter = new OrderAdapter(_context, page);
        recyclerView.setAdapter(adapter);
        ((OrderListActivity)_context).GetOrderList(page);
//        recyclerView.
//        TextView textView = view.findViewById(R.id.textView);
//        textView.setText(page);
        return view;
    }

    public void SetActivityContext(Context c)
    {
        _context = c;
    }

    public void updateContent() {
        //TODO
        adapter.notifyDataSetChanged();
        textErr.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
    public void ShowErrMsg(String errmsg) {
        textErr.setVisibility(View.VISIBLE);
        textErr.setText(errmsg);
        recyclerView.setVisibility(View.GONE);
    }
}