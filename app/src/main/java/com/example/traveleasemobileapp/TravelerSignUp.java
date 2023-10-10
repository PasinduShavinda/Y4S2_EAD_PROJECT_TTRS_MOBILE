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
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText dateOfBirthEditText;
    EditText phoneEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText confPasswordEditText;
    Button singUpBtn;

    ProgressDialog progressDialog;

    SignUpManager signUpManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_traveler_sign_up);
        ContextManager.getInstance().setApplicationContext(getApplicationContext());

        getSupportActionBar().hide();

        this.signUpManager = SignUpManager.getInstance();
        this.nicEditText = findViewById(R.id.nicEditText);
        this.firstNameEditText = findViewById(R.id.firstNameEditText);
        this.lastNameEditText = findViewById(R.id.lastNameEditText);
        this.dateOfBirthEditText = findViewById(R.id.dateOfBirthEditText);
        this.phoneEditText = findViewById(R.id.phoneEditText);
        this.emailEditText = findViewById(R.id.emailEditText);
        this.passwordEditText = findViewById(R.id.passwordEditText);
        this.confPasswordEditText = findViewById(R.id.confPasswordEditText);
        this.singUpBtn = findViewById(R.id.signUpBtn);
        this.singUpBtn.setOnClickListener(view -> signUp());
    }

    //Validate details and sign up
    private void signUp() {
        String nic = this.nicEditText.getText().toString();
        String firstName = this.firstNameEditText.getText().toString();
        String lastName = this.lastNameEditText.getText().toString();
        String dateOfBirth = this.dateOfBirthEditText.getText().toString();
        String phoneNo = this.phoneEditText.getText().toString();
        String email = this.emailEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();
        String confPassword = this.confPasswordEditText.getText().toString();

        if (!nic.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() && !String.valueOf(phoneNo).isEmpty() && !dateOfBirth.isEmpty() && !email.isEmpty()
                && !password.isEmpty() && !confPassword.isEmpty()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();

            if (confPassword.equals(password)) {
                signUpManager.signUp(nic, firstName, lastName, dateOfBirth, Integer.parseInt(phoneNo), email, password, () -> handleSignUpSuccessful(),
                        error -> handleSignUpFailed(error)
                );
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Please fill all the fields!", Toast.LENGTH_LONG).show();
        }
    }

    //Called if sign up was successful
    private void handleSignUpSuccessful(){
        progressDialog.dismiss();
        Toast.makeText(this, "Successful!", Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(this, LogIn.class);
//        startActivity(intent);
    }

    //Called if sign up failed
    private void handleSignUpFailed(String error){
        progressDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}