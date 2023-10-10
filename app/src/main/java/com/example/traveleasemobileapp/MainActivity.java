package com.example.traveleasemobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.traveleasemobileapp.managers.ContextManager;
public class MainActivity extends AppCompatActivity {
    private Button btnUserReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContextManager.getInstance().setApplicationContext(getApplicationContext());

        btnUserReg = (Button) findViewById(R.id.btn_userProfile);

        btnUserReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistrationPage();
            }
        });

    }

    // Navigation
    public void openRegistrationPage(){

        Intent intent = new Intent(this, TravelerSignUp.class);

        startActivity(intent);

    }

}