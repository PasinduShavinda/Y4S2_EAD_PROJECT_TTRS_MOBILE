// Kalansooriya S. H.
// TI20137700
package com.example.traveleasemobileapp.models;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.GET;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import java.util.List;
public interface ReservationService {
    @POST("Reservation")
    Call<ReservationResponse>  createreservation(@Body ReservationRequest reservationSearchRequest);

    @GET("Reservation/byUserId/{userId}")
    Call<List<ReservationResponse>> getReservationsByUserId(@Path("userId") String userId);

    @DELETE("Reservation/{id}")
    Call<Void> deleteReservation(@Path("id") String id);

    @PUT("Reservation/{id}")
    Call<ReservationResponse> editReservation(
            @Path("id") String reservationId,
            @Body ReserationUpdateRequest request
    );


}