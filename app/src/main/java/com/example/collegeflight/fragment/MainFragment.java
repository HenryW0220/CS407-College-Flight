package com.example.collegeflight.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.collegeflight.DBHelper;
import com.example.collegeflight.R;
import com.example.collegeflight.SearchResultActivity;

import java.util.List;
import java.util.Locale;


public class MainFragment extends Fragment {
    private Button buttonPickDate;
    private Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        buttonPickDate = view.findViewById(R.id.buttonPickDate);
        calendar = Calendar.getInstance();


        buttonPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        loadDeparturesIntoSpinner(view);
        loadDestinationsIntoSpinner(view);


        final Spinner spinnerDeparture = view.findViewById(R.id.spinnerDeparture);
        final Spinner spinnerDestination = view.findViewById(R.id.spinnerDestination);
        final Button buttonSearch = view.findViewById(R.id.buttonSearch);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String departure = spinnerDeparture.getSelectedItem() != null ? spinnerDeparture.getSelectedItem().toString() : "";
                String destination = spinnerDestination.getSelectedItem() != null ? spinnerDestination.getSelectedItem().toString() : "";
                String fullDateText = buttonPickDate.getText().toString();

                if (!departure.isEmpty() && !destination.isEmpty() && isValidDate(fullDateText)) {
                    String selectedDate = fullDateText.split(": ")[1];
                    Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                    intent.putExtra("DEPARTURE", departure);
                    intent.putExtra("DESTINATION", destination);
                    intent.putExtra("DATE", selectedDate);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Please make sure all selections are made", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    private boolean isValidDate(String date) {
        String regex = "Selected Date: \\d{4}-\\d{2}-\\d{2}";
        return date.matches(regex);
    }
    private void loadDeparturesIntoSpinner(View view) {
        DBHelper dbHelper = new DBHelper(getContext());
        List<String> departures = dbHelper.getAllDepartures();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, departures);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerDeparture = view.findViewById(R.id.spinnerDeparture);
        spinnerDeparture.setAdapter(adapter);
    }

    private void loadDestinationsIntoSpinner(View view) {
        DBHelper dbHelper = new DBHelper(getContext());
        List<String> destinations = dbHelper.getAllDestinations();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, destinations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerDestination = view.findViewById(R.id.spinnerDestination);
        spinnerDestination.setAdapter(adapter);
    }

    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        handleDateSelection(year, month, dayOfMonth);
                    }
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    private void handleDateSelection(int year, int month, int dayOfMonth) {
        String selectedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, dayOfMonth);

        buttonPickDate.setText("Selected Date: " + selectedDate);
    }
}