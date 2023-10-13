// Kalansooriya S. H.
// TI20137700
package com.example.traveleasemobileapp.models;
import java.util.List;
import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ScheduleService {

    @GET("Schedule/FilterSchedules")
    Call<List<ScheduleResponse>> filter(
            @Query("date") String date,
            @Query("station1") String station1,
            @Query("station2") String station2

    );




}