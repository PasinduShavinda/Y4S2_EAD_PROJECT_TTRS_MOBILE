// Kalansooriya S. H.
// TI20137700

package com.example.traveleasemobileapp;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

import com.example.traveleasemobileapp.managers.ReservationManager;
import com.example.traveleasemobileapp.models.ReservationResponse;

import java.util.ArrayList;
import java.util.List;

public class ReservatinHistory extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ReservationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservatin_history);

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

            // Use the context from the parent view
            Context context = recyclerView.getContext();

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            View popupView = LayoutInflater.from(context).inflate(R.layout.edit_reservation_screen, null);
            dialogBuilder.setView(popupView);

            TextView popupDateReserved = popupView.findViewById(R.id.popupDateReserved);
            EditText edit1class= popupView.findViewById(R.id.update1class);
            EditText edit2class= popupView.findViewById(R.id.update2class);


          popupDateReserved.setText("Date : " + reservation.date);
            edit1class.setHint(String.valueOf(reservation.seatcount1)); // Convert to string
            edit2class.setHint(String.valueOf(reservation.seatcount2));

            dialogBuilder.setPositiveButton("OK", (dialog, which) -> {

                ReservationManager reservationManager = ReservationManager.getInstance();


                String reservationId = reservation.id;
                Integer updatedSeatcount1 = Integer.parseInt(edit1class.getText().toString());
                Integer updatedSeatcount2 = Integer.parseInt(edit2class.getText().toString());

                String updatedTrainName = reservation.trainName;
                String updatedTrainId = reservation.trainId;
                String updatedUserId = reservation.userId;
                String updatedScheduleId = reservation.sheduleId;
                String updatedDate = reservation.date;

// Call the updateReservation method
                reservationManager.updateReservation(
                        reservationId,
                        updatedSeatcount1,
                        updatedSeatcount2,
                        updatedTrainName,
                        updatedTrainId,
                        updatedUserId,
                        updatedScheduleId,
                        updatedDate,
                        () -> {
                            // Handle success (e.g., show a success message)
                            System.out.println("Reservation updated successfully");
                        },
                        error -> {

                            System.err.println("Error updating reservation: " + error);
                        }
                );

                dialog.dismiss();
                myreservations();
            });

            dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {

                dialog.dismiss();
            });


            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
        }



        private void onDeleteButtonClicked(int position,String id) {
            String reservationId = id;
            ReservationManager.getInstance().deleteReservation(
                    reservationId,
                    () -> {
                        myreservations();
                        System.out.println("deleted");
                    },
                    error -> {
                        System.out.println("error");
                    }
            );

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
