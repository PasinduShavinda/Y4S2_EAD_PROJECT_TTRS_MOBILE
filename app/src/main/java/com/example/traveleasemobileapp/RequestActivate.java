/**
 * FileName: RequestActivate.java
 * FileType: Java Class
 * Author:IT20140298 Shavinda W.A.P
 * Description: This class represents the Request Activation activity in the TravelEase mobile app.
 * It allows users to request activation by providing their NIC (National Identification Card) number.
 * The user's request is sent to the server via the SendActiveReqManager for processing.
 */

package com.example.traveleasemobileapp;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.traveleasemobileapp.managers.ContextManager;
import com.example.traveleasemobileapp.managers.DatabaseManager;
import com.example.traveleasemobileapp.managers.SendActiveReqManager;
import com.example.traveleasemobileapp.managers.LogInManager;

public class RequestActivate extends AppCompatActivity{

    EditText nic;
    Button confirmBtn;
    SendActiveReqManager sendActiveReqManager;
    DatabaseManager databaseManager;
    LogInManager logInManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_active_request);
        ContextManager.getInstance().setApplicationContext(getApplicationContext());

        getSupportActionBar().setTitle("Requesting to Activation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sendActiveReqManager = SendActiveReqManager.getInstance();
        databaseManager = DatabaseManager.getInstance();
        logInManager = LogInManager.getInstance();

        this.nic = (EditText) findViewById(R.id.nicEditText2);
        this.confirmBtn = findViewById(R.id.confirmBtn2);
        this.confirmBtn.setOnClickListener(view -> onClickConfirm());
    }
    /**
     * This function is called when the "Confirm" button is clicked.
     * It collects the user's NIC from the input field, validates it, and initiates the activation request.
     */
    private void onClickConfirm() {
        String nc = this.nic.getText().toString().trim();

        if(nc.isEmpty()) {
            this.displayToast("Please enter your NIC");
        } else {
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Loading...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.show();
                sendActiveReqManager.sendActivateReq(nc,  () -> handleUpdateSuccessful(),
                        error -> handleUpdateFailed(error));
        }
    }
    /**
     * Display a toast message to the user.
     * @param message The message to display.
     */
    private void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    /**
     * This function is called when the activation request is successful.
     * It dismisses the progress dialog, shows a success message to the user, and logs out the user.
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
     * This function is called when the activation request fails.
     * It dismisses the progress dialog and displays an error message to the user.
     * @param error The error message to display.
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
