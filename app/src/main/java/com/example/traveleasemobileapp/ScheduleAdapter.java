package com.example.traveleasemobileapp;


import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traveleasemobileapp.managers.ReservationManager;
import com.example.traveleasemobileapp.managers.SignUpManager;
import com.example.traveleasemobileapp.models.ScheduleResponse;
import java.util.List;
import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private List<ScheduleResponse> scheduleList;
    private Context context;


    public ScheduleAdapter(Context context, List<ScheduleResponse> scheduleList) {
        this.context = context;
        this.scheduleList = scheduleList != null ? scheduleList : new ArrayList<>();
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_schedule_item, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        ScheduleResponse schedule = scheduleList.get(position);

        // Bind data to the TextViews
        holder.trainNameTextView.setText(schedule.trainName);
        holder.departureCityTextView.setText("From " + schedule.departureCity + " To " + schedule.arrivalCity);
        holder.departureTimeTextView.setText("Departure Time : "+schedule.departuretime);
        holder.RemainingSeates.setText("Remaining "+ schedule.class1reservation+" first class seates and remaining "+schedule.class1reservation + " second class seats");
        // Set up the button click listener
        holder.detailsButton.setOnClickListener(view -> {
            // Display a dialog with the selected details and input fields
            showDetailsDialog(schedule, context);
        });
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public void setData(List<ScheduleResponse> scheduleList) {
        this.scheduleList = scheduleList;
        notifyDataSetChanged();
    }

    static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView trainNameTextView;
        TextView departureCityTextView;

        TextView departureTimeTextView;
        TextView title_dialog;
        TextView RemainingSeates;
        Button detailsButton;

        ScheduleViewHolder(View itemView) {
            super(itemView);
            trainNameTextView = itemView.findViewById(R.id.trainNameTextView);
            departureCityTextView = itemView.findViewById(R.id.departureCityTextView);
            departureTimeTextView = itemView.findViewById(R.id.departureTimeTextView);
            RemainingSeates = itemView.findViewById(R.id.RemainingSeates);
            detailsButton = itemView.findViewById(R.id.detailsButton);

        }
    }

    private void showDetailsDialog(ScheduleResponse schedule, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ReservationManager rereservationManager;
        rereservationManager = ReservationManager.getInstance();



        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_details, null);
        EditText editText1 = dialogView.findViewById(R.id.editText1);
        EditText editText2 = dialogView.findViewById(R.id.editText2);
        TextView detailsTextView = dialogView.findViewById(R.id.detailsTextView);
        TextView title_dialog = dialogView.findViewById(R.id.title_dialog);
        title_dialog.setText("Book Seats on "+schedule.trainName);
        detailsTextView.setText(
                "On : " + schedule.date+
                "\nFrom : " + schedule.departureCity
                + "\nTo : " + schedule.arrivalCity
                + "\nDeparture Time : " + schedule.departuretime
                + "\nArrival Time : " + schedule.arrivaltime
                 + "\nThe train will stops at " + TextUtils.join(", ", schedule.stopStations));


        builder.setView(dialogView);

        builder.setPositiveButton("OK", (dialog, which) -> {
            // Retrieve the values from the input fields
            String input1 = editText1.getText().toString();
            String input2 = editText2.getText().toString();
            try {
                // Convert the string inputs to integers
                int number1 = Integer.parseInt(input1);
                int number2 = Integer.parseInt(input2);

                rereservationManager.createreservation(number1,number2,
                        schedule.trainName,schedule.trainId,"test",
                        schedule._id, schedule.date , () -> dialog.dismiss(),
                        error -> dialog.dismiss()
                );

            } catch (NumberFormatException e) {
                // Handle the case where the input strings are not valid integers
                // You can show an error message or perform some error handling here
            }



            dialog.dismiss();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.show();


    }


}
