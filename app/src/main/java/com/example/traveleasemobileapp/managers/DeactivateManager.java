/**
 * FileName: DeactivateManager.java
 * FileType: Java File
 * Author: IT20140298 Shavinda W.A.P
 * Description: This class manages the deactivation of user accounts through backend API calls.
 *              It is implemented as a singleton to ensure there is only one instance of the manager.
 */

package com.example.traveleasemobileapp.managers;

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
        deactivateService.deactivate(nic)
                .enqueue(new Callback<DeactivateResponse>() {
                    @Override
                    public void onResponse(Call<DeactivateResponse> call, Response<DeactivateResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            DeactivateResponse deactivateResponse = response.body();
                            Log.i("UPTRES:", String.valueOf(response.code()));
                                if (response.code() == 200) {
                                    onSuccess.run();
                                } else {
                                    onError.accept("An error occurred! " + deactivateResponse.message);
                                 }
                        } else {
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
