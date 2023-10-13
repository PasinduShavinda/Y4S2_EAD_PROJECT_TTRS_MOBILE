/**
 * FileName: TravelerSignUp.java
 * FileType: Java Class
 * Author: IT20140298 Shavinda W.A.P
 * Description: This class represents the Traveler Sign-Up activity in the TravelEase mobile app.
 * It allows travelers to create an account by providing their details, including NIC (National Identification Card) number,
 * email, username, full name, and password. The class communicates with the server through the SignUpManager
 * to register the new user.
 */

package com.example.traveleasemobileapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.traveleasemobileapp.managers.ContextManager;
import com.example.traveleasemobileapp.managers.SignUpManager;

public class TravelerSignUp extends AppCompatActivity {
    EditText nicEditText;
    EditText emailEditText;
    EditText userNameEditText;
    EditText fullNameEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    Button singUpBtn;
    ProgressDialog progressDialog;
    SignUpManager signUpManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_traveler_sign_up);
        ContextManager.getInstance().setApplicationContext(getApplicationContext());



        this.signUpManager = SignUpManager.getInstance();
        this.nicEditText = findViewById(R.id.nicEditText);
        this.emailEditText = findViewById(R.id.emailEditText);
        this.userNameEditText = findViewById(R.id.userNameEditText);
        this.fullNameEditText = findViewById(R.id.fullNameEditText);
        this.passwordEditText = findViewById(R.id.passwordEditText);
        this.confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        this.singUpBtn = findViewById(R.id.signUpBtn);
        this.singUpBtn.setOnClickListener(view -> signUp());
    }

    /**
     * This function is called when the "Sign Up" button is clicked.
     * It collects user input for registration, validates it, and initiates the registration process.
     */

    private void signUp() {
        String nic = this.nicEditText.getText().toString();
        String email = this.emailEditText.getText().toString();
        String userName = this.userNameEditText.getText().toString();
        String fullName = this.fullNameEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();
        String confirmPassword = this.confirmPasswordEditText.getText().toString();
        String role = "Traveller";
        Boolean isActive = true;

        if (!nic.isEmpty() && !email.isEmpty() && !userName.isEmpty() && !fullName.isEmpty() && !confirmPassword.isEmpty()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();

            if (confirmPassword.equals(password)) {
                signUpManager.signUp(nic, email, userName, fullName, password, confirmPassword, role, isActive, () -> handleSignUpSuccessful(),
                        error -> handleSignUpFailed(error)
                );
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Please fill all the fields!", Toast.LENGTH_LONG).show();
        }
    }
    /**
     * This function is called when the sign-up process is successful.
     * It dismisses the progress dialog and shows a success message to the user.
     */
    private void handleSignUpSuccessful(){
        progressDialog.dismiss();
        Toast.makeText(this, "Successful!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }
    /**
     * This function is called when the sign-up process fails.
     * It dismisses the progress dialog and displays an error message to the user.
     * @param error The error message to display.
     */
    private void handleSignUpFailed(String error){
        progressDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        System.out.println(error);

    }
}