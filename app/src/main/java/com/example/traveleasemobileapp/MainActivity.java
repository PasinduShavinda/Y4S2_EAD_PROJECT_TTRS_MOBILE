package com.example.traveleasemobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import com.example.traveleasemobileapp.managers.ContextManager;
import com.example.traveleasemobileapp.managers.ReservationManager;
import com.example.traveleasemobileapp.models.ReservationResponse;
public class MainActivity extends AppCompatActivity {
    private Button btnUserReg;
    private Button btnReservationSerch;
    private Button myreservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContextManager.getInstance().setApplicationContext(getApplicationContext());

        btnUserReg = (Button) findViewById(R.id.btn_userProfile);
        btnReservationSerch=(Button) findViewById(R.id.btn_reservation_Search);
        myreservation =(Button)findViewById(R.id.btn_my_reservations);
        btnUserReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistrationPage();
            }
        });

        btnReservationSerch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReservationPage();
            }
        });
        myreservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //myreservations();
                openReservationHistoryPage();
            }
        });

    }

    // Navigation
    public void openRegistrationPage(){

        Intent intent = new Intent(this, TravelerSignUp.class);

        startActivity(intent);

    }
    public void openReservationPage(){

        Intent intent = new Intent(this, ReservationSearch.class);

        startActivity(intent);

    }
    public void openReservationHistoryPage(){

        Intent intent = new Intent(this, ReservatinHistory.class);

        startActivity(intent);

    }


}