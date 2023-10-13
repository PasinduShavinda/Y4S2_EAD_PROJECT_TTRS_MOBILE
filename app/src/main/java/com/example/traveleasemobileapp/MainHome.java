package com.example.traveleasemobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.traveleasemobileapp.managers.ContextManager;

public class MainHome extends AppCompatActivity{

    private Button btnViewProf, btnDeactivate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        ContextManager.getInstance().setApplicationContext(getApplicationContext());

        btnViewProf = (Button) findViewById(R.id.btn_view_profile);

        btnDeactivate = (Button) findViewById(R.id.btn_deactive);

        btnViewProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfilePage();
            }
        });

        btnDeactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDeactivePage();
            }
        });

    }


    // Navigation
    public void openProfilePage(){

        Intent intent = new Intent(this, TravelerSignUp.class);

        startActivity(intent);
    }

    public void openDeactivePage(){

        Intent intent = new Intent(this, DeactivateProfile.class);

        startActivity(intent);
    }

}
