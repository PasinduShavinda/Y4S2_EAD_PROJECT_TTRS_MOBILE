package com.example.traveleasemobileapp.models;


public class ScheduleRequest {
    String Nic;
    String Email;
    String Username;


    public ScheduleRequest(String Nic, String Email, String Username, String FullName, String Password, String ConfirmPassword, String Role, Boolean IsActive) {
        this.Nic = Nic;
        this.Email = Email;

    }
}