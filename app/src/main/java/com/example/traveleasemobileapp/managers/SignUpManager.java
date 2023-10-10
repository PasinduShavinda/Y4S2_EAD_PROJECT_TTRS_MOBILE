package com.example.traveleasemobileapp.managers;

import android.util.Log;

import com.example.traveleasemobileapp.models.SignUpRequest;
import com.example.traveleasemobileapp.models.SignUpResponse;
import com.example.traveleasemobileapp.models.SignUpService;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpManager {

    private static SignUpManager singleton;
    private SignUpService signUpService;

    //return SignUpManager singleton object
    public static SignUpManager getInstance() {
        if (singleton == null)
            singleton = new SignUpManager();

        return singleton;
    }

    private SignUpManager() {
        signUpService = NetworkManager.getInstance().createService(SignUpService.class);
    }

    //Calls backend API to create a new account
    public void signUp(
            String nic,
            String firstName,
            String lastName,
            String dateOfBirth,
            int phoneNo,
            String email,
            String password,
            Runnable onSuccess,
            Consumer<String> onError
    ) {
        if (!NetworkManager.getInstance().isNetworkAvailable()) {
            onError.accept("No internet connectivity");
            return;
        }

        SignUpRequest body = new SignUpRequest(nic, firstName, lastName, dateOfBirth, phoneNo, email, password);

        signUpService.signUp(body)
                .enqueue(new Callback<SignUpResponse>() {
                    @Override
                    public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                        Log.i("SIGNUPRES:", String.valueOf(response.code()));
                        Log.i("URL:", call.request().url().toString());


                        if (response.code() == 201) {
                            onSuccess.run();
                        } else {
                            onError.accept("An error occurred!");
                        }
                    }

                    @Override
                    public void onFailure(Call<SignUpResponse> call, Throwable t) {
                        Log.e("SIGNUPERR:", t.toString());
                        onError.accept("Unknown error occurred when signing up");

                    }
                });

    }

}
