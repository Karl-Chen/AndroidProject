package com.example.modelapplication.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modelapplication.Product;
import com.example.modelapplication.R;
import com.example.modelapplication.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new ProductAdapter(getContext());
        recyclerView.setAdapter(adapter);
        adapter.updateData();

//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        homeViewModel.getCardItems().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
//            @Override
//            public void onChanged(List<Product> items) {
//                adapter.notifyDataSetChanged();
//            }
//        });

        return root;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("FragmentManager Check", "ParentFragmentManager: " + getParentFragmentManager().toString());
        getActivity().getSupportFragmentManager().setFragmentResultListener("UpDateProduct", this, (requestKey, result) -> {
            boolean shouldUpdate = result.getBoolean("update", false);
            if (shouldUpdate && adapter != null) {
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}