/**
 * FileName: LogInRequest.java
 * FileType: Java File
 * Author: IT20140298 Shavinda W.A.P
 * Description: This class represents a request object for user login. It contains two fields, 'email'
 *              and 'password,' which are used to provide user credentials for the login operation.
 */

package com.example.traveleasemobileapp.models;

public class LogInRequest {
    public String email;
    public String password;
    /**
     * Constructs a LogInRequest object with the specified email and password.
     * @param email The user's email for login.
     * @param password The user's password for login.
     */
    public LogInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
