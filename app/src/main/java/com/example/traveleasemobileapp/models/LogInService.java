/**
 * FileName: LogInService.java
 * FileType: Java Interface
 * Author: IT20140298 Shavinda W.A.P
 * Description: This interface defines the API endpoint for user login. It specifies a single POST request
 *              to the backend API for login. The request body is of type LogInRequest, and the response
 *              is expected to be of type LogInResponse.
 */

package com.example.traveleasemobileapp.models;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LogInService {
    /**
     * Send a POST request to perform user login.
     * @param logInRequest The request body containing user credentials for login.
     * @return A Call object representing the login request and expected response.
     */
    @POST("authenticate/login")
    Call<LogInResponse> login(@Body LogInRequest logInRequest);
}
