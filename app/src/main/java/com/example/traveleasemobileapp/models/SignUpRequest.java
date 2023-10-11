package com.example.traveleasemobileapp.models;

public class SignUpRequest {
    String Nic;
    String Email;
    String Username;
    String FullName;
    String Password;
    String ConfirmPassword;
    String Role;
    Boolean IsActive;

    public SignUpRequest(String Nic, String Email, String Username, String FullName, String Password, String ConfirmPassword, String Role, Boolean IsActive) {
        this.Nic = Nic;
        this.Email = Email;
        this.Username = Username;
        this.FullName = FullName;
        this.Password = Password;
        this.ConfirmPassword = ConfirmPassword;
        this.Role = Role;
        this.IsActive = IsActive;
    }
}
