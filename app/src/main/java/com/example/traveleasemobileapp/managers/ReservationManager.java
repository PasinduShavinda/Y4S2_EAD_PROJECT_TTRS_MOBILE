package com.example.traveleasemobileapp.managers;

import android.util.Log;

import com.example.traveleasemobileapp.models.ReservationResponse;
import com.example.traveleasemobileapp.models.ReservationService;
import com.example.traveleasemobileapp.models.ReservationRequest;

import java.util.function.Consumer;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class ReservationManager {

    private static ReservationManager singleton;
    private ReservationService reservationService;

    //return SignUpManager singleton object
    public static ReservationManager getInstance() {
        if (singleton == null)
            singleton = new ReservationManager();

        return singleton;
    }

    private ReservationManager() {
        reservationService = NetworkManager.getInstance().createService(ReservationService.class);
    }

    //Calls backend API to create a new account
    public void createreservation(
            Integer seatcount1,
    Integer seatcount2,
    String trainName,
   String trainId,
  String userId,
    String sheduleId,
     String date,
            Runnable onSuccess,
            Consumer<String> onError
    ) {
        if (!NetworkManager.getInstance().isNetworkAvailable()) {
            onError.accept("No Internet Connectivity");
            return;
        }

        ReservationRequest body = new ReservationRequest(seatcount1, seatcount2, trainName, trainId, userId, sheduleId, date);

        reservationService.createreservation(body)
                .enqueue(new Callback<ReservationResponse>() {
                    @Override
                    public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {
                        Log.i("SIGNUPRES:", String.valueOf(response.code()));
                        Log.i("URL:", call.request().url().toString());

                        if (response.code() == 201) {
                            onSuccess.run();
                        } else {
                            onError.accept("An error occurred!");
                        }
                    }

                    @Override
                    public void onFailure(Call<ReservationResponse> call, Throwable t) {
                        Log.e("SIGNUPERR:", t.toString());
                        onError.accept("Unknown error occurred when signing up");

                    }
                });

    }

    public void getReservationsByUserId(
            String userId,
            Consumer<List<ReservationResponse>> onSuccess,
            Consumer<String> onError
    ) {
        if (!NetworkManager.getInstance().isNetworkAvailable()) {
            onError.accept("No Internet Connectivity");
            return;
        }

        Call<List<ReservationResponse>> call = reservationService.getReservationsByUserId(userId);
        call.enqueue(new Callback<List<ReservationResponse>>() {
            @Override
            public void onResponse(Call<List<ReservationResponse>> call, Response<List<ReservationResponse>> response) {
                Log.i("GETRES:", String.valueOf(response.code()));
                Log.i("URL:", call.request().url().toString());

                if (response.isSuccessful()) {
                    onSuccess.accept(response.body());
                    System.out.println(response.body());
                } else {
                    onError.accept("An error occurred!");
                }
            }

            @Override
            public void onFailure(Call<List<ReservationResponse>> call, Throwable t) {
                Log.e("GETRESERR:", t.toString());
                onError.accept("Unknown error occurred when getting reservations");
            }
        });
    }

    public void deleteReservation(
            String reservationId,
            Runnable onSuccess,
            Consumer<String> onError
    ) {
        if (!NetworkManager.getInstance().isNetworkAvailable()) {
            onError.accept("No Internet Connectivity");
            return;
        }

        reservationService.deleteReservation(reservationId)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.i("DELETERES:", String.valueOf(response.code()));
                        Log.i("URL:", call.request().url().toString());

                        if (response.code() == 204) {
                            onSuccess.run();
                        } else {
                            onError.accept("An error occurred while deleting the reservation");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("DELETERESERR:", t.toString());
                        onError.accept("Unknown error occurred when deleting the reservation");
                    }
                });
    }



}
