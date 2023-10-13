/**
 * FileName: SignUpService.java
 * FileType: Java Interface
 * Author: IT20140298 Shavinda W.A.P
 * Description: This interface defines the API endpoint for user signup and registration. It specifies a single POST request
 *              to the backend API for registration. The request body is of type SignUpRequest, and the response
 *              is expected to be of type SignUpResponse.
 */

package com.example.traveleasemobileapp.models;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpService {
    /**
     * Send a POST request to perform user signup and registration.
     * @param signUpRequest The request body containing user details for registration.
     * @return A Call object representing the registration request and expected response.
     */
    @POST("authenticate/register")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);

}
