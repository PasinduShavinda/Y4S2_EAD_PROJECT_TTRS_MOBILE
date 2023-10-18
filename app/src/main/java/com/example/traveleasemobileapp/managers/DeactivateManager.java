/**
 * FileName: DeactivateManager.java
 * FileType: Java File
 * Author: IT20140298 Shavinda W.A.P
 * Description: This class manages the deactivation of user accounts through backend API calls.
 *              It is implemented as a singleton to ensure there is only one instance of the manager.
 */

package com.example.traveleasemobileapp.managers;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.traveleasemobileapp.models.DeactivateResponse;
import com.example.traveleasemobileapp.models.DeactivateService;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeactivateManager {
    // Singleton instance of the DeactivateManager
    private static DeactivateManager singleton;
    // Reference to the DeactivateService for backend API calls
    private DeactivateService deactivateService;

    private SharedPreferences prf;

    private final String loginStateFile = "loginstate";

    private final String userAccessToken = "access_token";

    /**
     * Get the instance of the DeactivateManager. If an instance does not exist, a new one is created.
     * @return The DeactivateManager instance.
     */
    public static DeactivateManager getInstance() {
        if (singleton == null)
            singleton = new DeactivateManager();

        return singleton;
    }
    /**
     * Private constructor to prevent external instantiation. Initializes the DeactivateService.
     */
    private DeactivateManager() {
        deactivateService = NetworkManager.getInstance().createService(DeactivateService.class);
        Context context = ContextManager.getInstance().getApplicationContext();
        prf = context.getSharedPreferences(loginStateFile, Context.MODE_PRIVATE);
    }

    /**
     * Calls the backend API to deactivate a user account.
     * @param nic The NIC (National Identification Card) of the user to be deactivated.
     * @param onSuccess A Runnable to be executed on successful deactivation.
     * @param onError A Consumer function to handle errors and provide error messages.
     */
    public void deactivateProfile(
            String nic,
            Runnable onSuccess,
            Consumer<String> onError
    ) {
        if (!NetworkManager.getInstance().isNetworkAvailable()) {
            onError.accept("No internet connectivity");
            return;
        }

        // Retrieve the token from SharedPreferences
        String token = prf.getString(userAccessToken, "");

        if (token.isEmpty()) {
            onError.accept("Token not available");
            return;
        }

                // Create a Retrofit request with the "Authorization" header
                Call<DeactivateResponse> call = deactivateService.deactivate("Bearer " + token, nic);
                call.enqueue(new Callback<DeactivateResponse>() {
                    @Override
                    public void onResponse(Call<DeactivateResponse> call, Response<DeactivateResponse> response) {

                        Log.i("Request URL:", call.request().url().toString());

                        if (response.isSuccessful() && response.body() != null) {
                            DeactivateResponse deactivateResponse = response.body();
                            Log.i("UPTRES:", String.valueOf(response.code()));
                                if (response.code() == 200) {
                                    onSuccess.run();
                                } else {
                                    Log.e("Error Response:", deactivateResponse.message);
                                    onError.accept("An error occurred! " + deactivateResponse.message);
                                 }
                        } else {
                            Log.e("Error Response Code:", String.valueOf(response.code()));
                            Log.e("Error Response Body:", response.errorBody() != null ? response.errorBody().toString() : "Empty");
                            onError.accept("Incorrect Credentials");
                        }
                    }

                    @Override
                    public void onFailure(Call<DeactivateResponse> call, Throwable t) {
                        Log.e("UPDATEERR:", t.toString());
                        Log.e("URL:", call.request().url().toString());
                        onError.accept("Unknown error occurred when deactivating the profile");
                    }
                });
    }
}
