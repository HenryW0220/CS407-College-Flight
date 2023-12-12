package com.example.collegeflight.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.collegeflight.DBHelper;
import com.example.collegeflight.R;
import com.example.collegeflight.adapter.OrderAdapter;
import com.example.collegeflight.bean.Order;

import java.util.List;


public class MyTripsFragment extends Fragment {
    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_trips, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewMyTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DBHelper dbHelper = new DBHelper(getContext());
        String userId = getUserIdFromPreferences();
        List<Order> orders = dbHelper.getOrdersByUserId(userId);

        adapter = new OrderAdapter(orders);
        recyclerView.setAdapter(adapter);

        return view;
    }
    private String getUserIdFromPreferences() {
        SharedPreferences preferences = getActivity().getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        return preferences.getString("userId", null);
    }
}