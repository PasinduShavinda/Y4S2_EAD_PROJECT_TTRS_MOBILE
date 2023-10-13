/**
 * FileName: SignUpRequest.java
 * FileType: Java File
 * Author: IT20140298 Shavinda W.A.P
 * Description: This class represents a request object for user signup and registration. It contains several fields
 *              that are used to provide user details for the registration process, including 'Nic,' 'Email,' 'Username,'
 *              'FullName,' 'Password,' 'ConfirmPassword,' 'Role,' and 'IsActive.'
 */

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

    /**
     * Constructs a SignUpRequest object with the specified user details.
     * @param Nic The NIC (National Identification Card) of the user.
     * @param Email The user's email for registration.
     * @param Username The user's username for registration.
     * @param FullName The user's full name.
     * @param Password The user's password for registration.
     * @param ConfirmPassword The confirmation of the user's password.
     * @param Role The role of the user.
     * @param IsActive True if the user is active; false otherwise.
     */
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
