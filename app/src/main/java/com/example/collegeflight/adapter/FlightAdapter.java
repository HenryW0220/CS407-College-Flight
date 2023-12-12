package com.example.collegeflight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeflight.DBHelper;
import com.example.collegeflight.R;
import com.example.collegeflight.bean.Flight;

import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {

    private List<Flight> flights;
    private Context context;
    private int userId;
    private String selectedDate;
    public FlightAdapter(List<Flight> flights, Context context, int userId, String selectedDate) {
        this.flights = flights;
        this.context = context;
        this.userId = userId;
        this.selectedDate = selectedDate;
    }

    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flight, parent, false);
        return new FlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
        Flight flight = flights.get(position);

        holder.tvFlightHeader.setText("Flight Details");
        holder.tvDeparture.setText("Departure: " + flight.getDeparture());
        holder.tvDestination.setText("Destination: " + flight.getDestination());
        holder.tvDepartureTime.setText("Dep Time: " + flight.getDepartureTime());
        holder.tvArrivalTime.setText("Arr Time: " + flight.getArrivalTime());
        holder.tvDuration.setText("Duration: " + flight.getDuration());
        holder.tvPrice.setText("Price: $" + flight.getPrice());

        holder.btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(context);

                dbHelper.purchaseFlight(flight, userId, selectedDate);
                Toast.makeText(context, "Purchase successful!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return flights.size();
    }

    public static class FlightViewHolder extends RecyclerView.ViewHolder {
        TextView tvFlightHeader, tvDeparture, tvDestination, tvDepartureTime, tvArrivalTime, tvDuration, tvPrice;
        Button btnPurchase;
        public FlightViewHolder(View itemView) {
            super(itemView);
            tvFlightHeader = itemView.findViewById(R.id.tvFlightHeader);
            tvDeparture = itemView.findViewById(R.id.tvDeparture);
            tvDestination = itemView.findViewById(R.id.tvDestination);
            tvDepartureTime = itemView.findViewById(R.id.tvDepartureTime);
            tvArrivalTime = itemView.findViewById(R.id.tvArrivalTime);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnPurchase=itemView.findViewById(R.id.btnPurchase);
        }
    }

}
