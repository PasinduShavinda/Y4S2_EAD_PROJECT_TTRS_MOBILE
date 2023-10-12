package com.example.traveleasemobileapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        String userId = "wbhebhebhfb";
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
            // Implement the edit button click action here
            // You can pass the selected reservation data or position to an edit activity/fragment.
            System.out.println("edited");
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
