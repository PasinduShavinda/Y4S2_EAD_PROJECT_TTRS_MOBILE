package com.example.traveleasemobileapp;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList; // Add this import
import java.util.Calendar;
import java.util.List; // Add this import
import java.util.Locale;

import android.util.Log;

import com.example.traveleasemobileapp.managers.ContextManager;
import com.example.traveleasemobileapp.managers.SheduleManager;
import com.example.traveleasemobileapp.models.ScheduleResponse;
public class ReservationSearch extends AppCompatActivity {
    SheduleManager sheduleManager;
    Button searchButton;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private Button datePickerButton;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private int selectedYear, selectedMonth, selectedDay;
    private RecyclerView scheduleRecyclerView;
    private ScheduleAdapter scheduleAdapter;

    private RecyclerView recyclerView;

    private List<String> locations = new ArrayList<String>() {
        {

            add("Colombo Fort");
            add("Maradana");
            add("Matara");
            add("Galle");
            add("Badulla");
            add("Mount Lavinia");
            add("Jaffna");
            add("Polgahawela");

        }
    };
    private List<ScheduleResponse> filteredSchedules;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_search);

        this.sheduleManager = SheduleManager.getInstance();
        this.searchButton = findViewById(R.id.searchButton);
        this.searchButton.setOnClickListener(view -> filterSchedules());
        this.fromSpinner = findViewById(R.id.fromSpinner);
        this.toSpinner = findViewById(R.id.toSpinner);
        this.datePickerButton = findViewById(R.id.datePickerButton);
        this.searchButton = findViewById(R.id.searchButton);
       ;
        this.calendar = Calendar.getInstance();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        scheduleRecyclerView = findViewById(R.id.scheduleRecyclerView);
        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        scheduleAdapter = new ScheduleAdapter(this, new ArrayList<>());
        scheduleRecyclerView.setAdapter(scheduleAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(ReservationSearch.this, android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapters for the Spinners
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        // Initialize the scheduleAdapter and set it to the ListView


        // Set OnClickListener for the date picker button
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void filterSchedules() {
        sheduleManager.filterSchedules(
               datePickerButton.getText().toString(),
               fromSpinner.getSelectedItem().toString(),
               toSpinner.getSelectedItem().toString(),
                () -> {
                    // Handle success
                    if (sheduleManager.getFilteredSchedules() != null) {
                        // Log the size of filteredSchedules to see if data is received
                        Log.d("FilteredSchedules", "Size: " + sheduleManager.getFilteredSchedules().size());
                        Toast.makeText(ReservationSearch.this, "Filtering successful", Toast.LENGTH_SHORT).show();

                        if (scheduleAdapter != null) {
                            scheduleAdapter.setData(sheduleManager.getFilteredSchedules());
                        }
                    } else {
                        Toast.makeText(ReservationSearch.this, "No data received", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // Handle error
                    Toast.makeText(ReservationSearch.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
        );
    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedYear = year;
                selectedMonth = monthOfYear;
                selectedDay = dayOfMonth;

                // Update the button text with the selected date
                calendar.set(selectedYear, selectedMonth, selectedDay);
                String formattedDate = dateFormat.format(calendar.getTime());
                datePickerButton.setText(formattedDate);
            }
        };

        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ReservationSearch.this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Show the DatePickerDialog
        datePickerDialog.show();
    }



}