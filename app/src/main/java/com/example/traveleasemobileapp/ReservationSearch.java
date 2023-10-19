// Kalansooriya S. H.
// TI20137700
package com.example.traveleasemobileapp;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
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


        ContextManager.getInstance().setApplicationContext(getApplicationContext());
        getSupportActionBar().hide();

        setContentView(R.layout.activity_reservation_search);
        ImageButton item1Button = findViewById(R.id.bottom_nav_item1);
        ImageButton item2Button = findViewById(R.id.bottom_nav_item2);
        ImageButton item3Button = findViewById(R.id.bottom_nav_item3);

        item1Button.setOnClickListener(view -> {
            Intent intent = new Intent(this, ReservationSearch.class);
            startActivity(intent);
        });

        item2Button.setOnClickListener(view -> {
            Intent intent = new Intent(this, ReservatinHistory.class);
            startActivity(intent);
        });

        item3Button.setOnClickListener(view -> {
            Intent intent = new Intent(this, DeactivateProfile.class);
            startActivity(intent);
        });



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




        // Set OnClickListener for the date picker button
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void filterSchedules() {
        String selectedDate = datePickerButton.getText().toString();

        // Get today's date
        Calendar today = Calendar.getInstance();
        String todayDate = dateFormat.format(today.getTime());

        // Parse the selected date and today's date for comparison
        try {
            today.setTime(dateFormat.parse(todayDate));
            Calendar selected = Calendar.getInstance();
            selected.setTime(dateFormat.parse(selectedDate));

            // Calculate the difference in days
            long differenceInDays = (selected.getTimeInMillis() - today.getTimeInMillis()) / (24 * 60 * 60 * 1000);

            // Check if the selected date is within 30 days from today
            if (differenceInDays <= 30) {
                // The date is within 30 days, proceed with filtering
                sheduleManager.filterSchedules(
                        selectedDate,
                        fromSpinner.getSelectedItem().toString(),
                        toSpinner.getSelectedItem().toString(),
                        () -> {
                            // Handle success
                            if (sheduleManager.getFilteredSchedules() != null) {
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
            } else {
                // The date is not within 30 days, display an error message
                Toast.makeText(ReservationSearch.this, "Selected date must be within 30 days from today", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        // Create a DatePickerDialog with the custom theme
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ReservationSearch.this,
                R.style.DatePickerTheme, // Apply the custom theme here
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Show the DatePickerDialog
        datePickerDialog.show();
    }




}