/**
 * FileName: MainActivity.java
 * FileType: Java Class
 * Author: IT20140298 Shavinda W.A.P
 * Description: This class represents the main activity of the TravelEase mobile app.
 * It serves as the entry point for the application and provides options to navigate to user registration,
 * login, and request activation screens.
 */

package com.example.traveleasemobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.traveleasemobileapp.managers.ContextManager;
public class MainActivity extends AppCompatActivity {
    private Button btnUserReg, btnLogin, btnSendReq;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContextManager.getInstance().setApplicationContext(getApplicationContext());

        btnUserReg = (Button) findViewById(R.id.btn_userProfile);

        btnLogin = (Button) findViewById(R.id.btn_logins);

        btnSendReq = (Button) findViewById(R.id.btn_send_req);

        btnUserReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistrationPage();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginPage();
            }
        });

        btnSendReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSendReqPage();
            }
        });
    }
    /**
     * Opens the user registration page.
     */
    // Navigation
    public void openRegistrationPage(){

        Intent intent = new Intent(this, TravelerSignUp.class);

        startActivity(intent);

    }

    public void openLoginPage(){

        Intent intent = new Intent(this, LogIn.class);

        startActivity(intent);
    }

    public void openSendReqPage(){

        Intent intent = new Intent(this, RequestActivate.class);

        startActivity(intent);
    }

}