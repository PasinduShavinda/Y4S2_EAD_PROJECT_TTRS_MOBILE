/**
 * FileName: DeactivateProfile.java
 * FileType: Java File
 * Author: IT20140298 Shavinda W.A.P
 * Description: This activity allows users to deactivate their profile. It provides a UI for users to enter their
 *              NIC (National Identification Card) and confirm the deactivation. It communicates with the DeactivateManager
 *              to send a deactivation request to the backend. If successful, the user is logged out, and the app navigates
 *              to the login screen. If an error occurs, an error message is displayed.
 */

package com.example.traveleasemobileapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.traveleasemobileapp.managers.ContextManager;
import com.example.traveleasemobileapp.managers.DatabaseManager;
import com.example.traveleasemobileapp.managers.DeactivateManager;
import com.example.traveleasemobileapp.managers.LogInManager;
import android.widget.ImageButton;
public class DeactivateProfile extends AppCompatActivity {

    EditText nic;
    Button confirmBtn;
    DeactivateManager deactivateManager;
    DatabaseManager databaseManager;
    LogInManager logInManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deactivate_profile);
        ContextManager.getInstance().setApplicationContext(getApplicationContext());

        getSupportActionBar().setTitle("Deactivate Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deactivateManager = DeactivateManager.getInstance();
        databaseManager = DatabaseManager.getInstance();
        logInManager = LogInManager.getInstance();

        this.nic = (EditText) findViewById(R.id.nicEditText);
        this.confirmBtn = findViewById(R.id.confirmBtn);
        this.confirmBtn.setOnClickListener(view -> onClickConfirm());

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

    }
    /**
     * Handles the user's confirmation to deactivate the profile.
     * Validates the entered NIC and initiates the deactivation process.
     */
    private void onClickConfirm() {
        String nc = this.nic.getText().toString().trim();

        if(nc.isEmpty()) {
            this.displayToast("Please enter your NIC");
        } else {
//            String nic = getIntent().getExtras().getString("nic");
            SharedPreferences sharedPreferences = getSharedPreferences("loginstate", Context.MODE_PRIVATE);
            String nic = sharedPreferences.getString("user_nic", "");
            if(nc.equals(nic)) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Loading...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.show();
                deactivateManager.deactivateProfile(nic,  () -> handleUpdateSuccessful(),
                        error -> handleUpdateFailed(error));
            } else {
                this.displayToast("Incorrect NIC");
            }
        }
    }

    /**
     * Displays a toast message with the given message.
     *
     * @param message The message to be displayed.
     */

    private void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    /**
     * Handles the successful deactivation of the profile.
     * Logs out the user, displays a success message, and navigates to the login screen.
     */
    private void handleUpdateSuccessful(){
        progressDialog.dismiss();
        Toast.makeText(this, "Successful!", Toast.LENGTH_LONG).show();

        this.logInManager.logout();
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
        finish();
    }
    /**
     * Handles the failure of the deactivation process.
     * Dismisses the progress dialog, displays an error message, and informs the user of the failure.
     *
     * @param error The error message describing the failure.
     */
    private void handleUpdateFailed(String error){
        progressDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
