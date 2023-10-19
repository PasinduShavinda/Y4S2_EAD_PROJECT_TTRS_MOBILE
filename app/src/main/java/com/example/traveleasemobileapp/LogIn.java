/**
 * FileName: LogIn.java
 * FileType: Java Class
 * Author: IT20140298 Shavinda W.A.P
 * Description: This class represents the user login screen in the TravelEase mobile app.
 * It allows users to log in using their email and password and navigate to the main home screen upon successful login.
 */

package com.example.traveleasemobileapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.traveleasemobileapp.managers.ContextManager;
import com.example.traveleasemobileapp.managers.LogInManager;

public class LogIn extends AppCompatActivity {

    LogInManager logInManager;
    EditText emailEditText;
    EditText passwordEditText;

    TextView signUpLink;

    TextView activeLink;
    Button loginBtn;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        ContextManager.getInstance().setApplicationContext(getApplicationContext());
        getSupportActionBar().hide();

        logInManager = LogInManager.getInstance();

        this.emailEditText = findViewById(R.id.loginEmailEditText);
        this.passwordEditText = findViewById(R.id.loginPasswordEditText);
        this.signUpLink = findViewById(R.id.signUpLink);
        this.activeLink = findViewById(R.id.activereqLink);
        this.loginBtn = findViewById(R.id.logInBtn);
        this.signUpLink.setOnClickListener(view -> goToSignUp());
        this.activeLink.setOnClickListener(view -> goToActive());
        this.loginBtn.setOnClickListener(view -> login());
    }
    /**
     * Initiates the login process.
     * Validates email and password fields, displays a progress dialog, and attempts to log in the user.
     */
    private void login(){
        if(!emailEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
            logInManager.login(
                    emailEditText.getText().toString(),
                    passwordEditText.getText().toString(),
                    () -> handleLoginSuccess(),
                    error -> handleLoginFailed(error));
        } else{
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
        }

    }
    /**
     * Handles a successful login.
     * Displays a success message, dismisses the progress dialog, and navigates to the main home screen.
     */
    private void handleLoginSuccess(){
        //logInManager.setLoggedInState(true);
        Toast.makeText(this, "SUCCESS", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
        Intent intent = new Intent(this, ReservationSearch.class);
        startActivity(intent);
        finish();
    }
    /**
     * Handles a failed login attempt.
     * Dismisses the progress dialog and displays an error message to the user.
     *
     * @param error The error message describing the login failure.
     */

    private void handleLoginFailed(String error){
        progressDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    /**
     * Navigates to the sign-up screen.
     */

    private void goToSignUp() {
        Intent intent = new Intent(this, TravelerSignUp.class);
        startActivity(intent);
    }
    /**
     * Navigates to the activate screen.
     */

    private void goToActive() {
        Intent intent = new Intent(this, RequestActivate.class);
        startActivity(intent);
    }
}