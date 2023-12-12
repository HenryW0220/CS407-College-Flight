package com.example.collegeflight.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeflight.R;
import com.example.collegeflight.bean.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orders;

    public OrderAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.tvOrderDepartureCity.setText("Departure City: " + order.getDepartureCity());
        holder.tvOrderDestinationCity.setText("Destination City: " + order.getDestinationCity());
        holder.tvOrderDepartureTime.setText("Departure Time: " + order.getDepartureTime());
        holder.tvOrderArrivalTime.setText("Arrival Time: " + order.getArrivalTime());
        holder.tvOrderTotalDuration.setText("Total Duration: " + order.getTotalDuration());
        holder.tvOrderFlightDate.setText("Flight Date: " + order.getFlightDate());
    }



    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderDepartureCity, tvOrderDestinationCity, tvOrderDepartureTime, tvOrderArrivalTime, tvOrderTotalDuration, tvOrderFlightDate;

        public OrderViewHolder(View itemView) {
            super(itemView);
            tvOrderDepartureCity = itemView.findViewById(R.id.tvOrderDepartureCity);
            tvOrderDestinationCity = itemView.findViewById(R.id.tvOrderDestinationCity);
            tvOrderDepartureTime = itemView.findViewById(R.id.tvOrderDepartureTime);
            tvOrderArrivalTime = itemView.findViewById(R.id.tvOrderArrivalTime);
            tvOrderTotalDuration = itemView.findViewById(R.id.tvOrderTotalDuration);
            tvOrderFlightDate = itemView.findViewById(R.id.tvOrderFlightDate);
        }
    }

}
