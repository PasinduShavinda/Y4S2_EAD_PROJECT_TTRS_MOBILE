/**
 * FileName: LogInResponse.java
 * FileType: Java File
 * Author: IT20140298 Shavinda W.A.P
 * Description: This class represents the response object for the user login operation. It contains
 *              various fields, including 'success,' 'accessToken,' 'userId,' 'message,' 'nic,' 'fullName,'
 *              'userName,' 'email,' and 'isActive,' which provide information about the login result and user details.
 */

package com.example.traveleasemobileapp.models;

public class LogInResponse {
    public boolean success;
    public String accessToken;
    public String userId;
    public String message;
    public String nic;
    public String fullName;
    public String userName;
    public String email;
    public boolean isActive;
}

