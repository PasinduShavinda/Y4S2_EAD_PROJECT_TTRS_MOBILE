package com.example.traveleasemobileapp.models;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpService {
    @POST("travelers")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);

}
