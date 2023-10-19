// Kalansooriya S. H.
// TI20137700

package com.example.traveleasemobileapp;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traveleasemobileapp.managers.ContextManager;
import com.example.traveleasemobileapp.managers.ReservationManager;
import com.example.traveleasemobileapp.models.ReservationResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservatinHistory extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ReservationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservatin_history);

        ContextManager.getInstance().setApplicationContext(getApplicationContext());
        getSupportActionBar().hide();

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

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ReservationAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myreservations();
    }

    public void myreservations() {
        String userId = "test";
        ReservationManager reservationManager = ReservationManager.getInstance();

        reservationManager.getReservationsByUserId(userId,
                reservations -> {
                    // Update the adapter with the list of reservations
                    adapter.setReservations(reservations);
                },
                error -> {
                    // Handle the error case
                    Log.e("Error", "Failed to get reservations: " + error);
                }
        );
    }

    private class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {
        private List<ReservationResponse> reservations = new ArrayList<>();

        public void setReservations(List<ReservationResponse> reservations) {
            this.reservations = reservations;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_card_item, parent, false);
            return new ReservationViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
            ReservationResponse reservation = reservations.get(position);

            holder.date_reserved.setText("Date : " + reservation.date);
            holder.textTrainName.setText("Train Name : " + reservation.trainName);
            holder.Firststclaaseats.setText("First Class Seats : "+ reservation.seatcount1);
            holder.SecondClassSteats.setText("Second Class Seats : "+ reservation.seatcount2) ;

            holder.editIconButton.setOnClickListener(v -> onEditButtonClicked(position));
            holder.deleteIconButton.setOnClickListener(v -> onDeleteButtonClicked(position, reservation.id));


        }

        private void onEditButtonClicked(int position) {
            ReservationResponse reservation = reservations.get(position);
            String reservationDate = reservation.date;

            // Check if the date is within 5 days from today
            if (isDateWithin5Days(reservationDate)) {
                // Display an error message or handle it as needed
                AlertDialog.Builder errorDialog = new AlertDialog.Builder(recyclerView.getContext());
                errorDialog.setMessage("Can't edit reservations within 5 days.");
                errorDialog.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                errorDialog.show();
            } else {
                // Continue with the edit dialog
                Context context = recyclerView.getContext();
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                View popupView = LayoutInflater.from(context).inflate(R.layout.edit_reservation_screen, null);
                dialogBuilder.setView(popupView);

                TextView popupDateReserved = popupView.findViewById(R.id.popupDateReserved);
                EditText edit1class = popupView.findViewById(R.id.update1class);
                EditText edit2class = popupView.findViewById(R.id.update2class);

                popupDateReserved.setText("Date : " + reservation.date);
                edit1class.setHint(String.valueOf(reservation.seatcount1)); // Convert to string
                edit2class.setHint(String.valueOf(reservation.seatcount2));

                dialogBuilder.setPositiveButton("OK", (dialog, which) -> {
                    // Add your edit logic here
                    dialog.dismiss();
                    myreservations();
                });

                dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                });

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        }






        private void onDeleteButtonClicked(int position, String id) {
            ReservationResponse reservation = reservations.get(position);
            String reservationId = id;
            String reservationDate = reservation.date;

            // Check if the date is within 5 days from today
            if (isDateWithin5Days(reservationDate)) {
                // Display an error message or handle it as needed
                AlertDialog.Builder errorDialog = new AlertDialog.Builder(recyclerView.getContext());
                errorDialog.setMessage("Can't delete reservations within 5 days.");
                errorDialog.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                errorDialog.show();
            } else {
                // Proceed with the deletion
                ReservationManager.getInstance().deleteReservation(
                        reservationId,
                        () -> {
                            myreservations();
                            System.out.println("Deleted");
                        },
                        error -> {
                            System.out.println("Error deleting reservation: " + error);
                        }
                );
            }
        }

        private boolean isDateWithin5Days(String dateString) {
            // Parse the reservation date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date reservationDate = dateFormat.parse(dateString);
                Date currentDate = new Date();

                // Calculate the difference in days
                long differenceInDays = (reservationDate.getTime() - currentDate.getTime()) / (1000 * 60 * 60 * 24);

                return differenceInDays <= 5;
            } catch (ParseException e) {
                e.printStackTrace();
                return false; // Handle parsing errors as needed
            }
        }


        @Override
        public int getItemCount() {
            return reservations.size();
        }

        public class ReservationViewHolder extends RecyclerView.ViewHolder {
            TextView date_reserved;
            TextView textTrainName;
            TextView Firststclaaseats;
            TextView SecondClassSteats;
            ImageButton editIconButton;
            ImageButton deleteIconButton;

            public ReservationViewHolder(View itemView) {
                super(itemView);

                date_reserved = itemView.findViewById(R.id.date_reserved);
                textTrainName = itemView.findViewById(R.id.textTrainName);
                Firststclaaseats =itemView.findViewById(R.id.Firststclaaseats);
                SecondClassSteats = itemView.findViewById(R.id.SecondClassSteats);
                editIconButton = itemView.findViewById(R.id.editIconButton);
                deleteIconButton = itemView.findViewById(R.id.deleteIconButton);




            }
        }
    }
}
