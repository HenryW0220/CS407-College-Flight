package com.example.collegeflight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.collegeflight.adapter.FlightAdapter;
import com.example.collegeflight.bean.Flight;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FlightAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent intent = getIntent();
        String departure = intent.getStringExtra("DEPARTURE");
        String destination = intent.getStringExtra("DESTINATION");
        String date = intent.getStringExtra("DATE");
        TextView summaryTextView = findViewById(R.id.summaryTextView);
        String summaryText = String.format("Results for:\nDeparture: %s\nDestination: %s\nDate: %s", departure, destination, date);
        summaryTextView.setText(summaryText);
        int userId = getUserIdFromPreferences();
        recyclerView = findViewById(R.id.resultsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DBHelper dbHelper = new DBHelper(this);
        List<Flight> flights = dbHelper.getFlights(departure, destination);

        Button buttonSortByDuration = findViewById(R.id.buttonSortByDuration);
        buttonSortByDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(flights, new Comparator<Flight>() {
                    @Override
                    public int compare(Flight f1, Flight f2) {
                        int duration1 = parseDurationToMinutes(f1.getDuration());
                        int duration2 = parseDurationToMinutes(f2.getDuration());
                        return Integer.compare(duration1, duration2);
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });




        adapter = new FlightAdapter(flights, this, userId, date);
        recyclerView.setAdapter(adapter);
    }
    private int parseDurationToMinutes(String duration) {
        int hours = 0;
        int minutes = 0;

        String[] parts = duration.split("h |m");

        if (parts.length > 0) {
            hours = Integer.parseInt(parts[0].trim());
        }

        if (parts.length > 1) {
            minutes = Integer.parseInt(parts[1].trim());
        }

        return hours * 60 + minutes;
    }

    private int getUserIdFromPreferences() {
        SharedPreferences preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        String userIdString = preferences.getString("userId", "");
        try {
            return Integer.parseInt(userIdString);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}