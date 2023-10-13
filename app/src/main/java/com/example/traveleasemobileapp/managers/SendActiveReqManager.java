/**
 * FileName: SendActiveReqManager.java
 * FileType: Java File
 * Author: IT20140298 Shavinda W.A.P
 * Description: This class manages sending activation requests to a backend officer through the
 *              backend API. It is implemented as a singleton to ensure there is only one instance of the manager.
 */

package com.example.traveleasemobileapp.managers;

import android.util.Log;

import com.example.traveleasemobileapp.models.SendActivateResponse;
import com.example.traveleasemobileapp.models.SendActivateService;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendActiveReqManager {
    // Singleton instance of the SendActiveReqManager
    private static SendActiveReqManager singleton;
    // Reference to the SendActivateService for backend API calls
    private SendActivateService sendActivateService;
    /**
     * Get the instance of the SendActiveReqManager. If an instance does not exist, a new one is created.
     * @return The SendActiveReqManager instance.
     */

    public static SendActiveReqManager getInstance() {
        if (singleton == null)
            singleton = new SendActiveReqManager();

        return singleton;
    }
    /**
     * Private constructor to prevent external instantiation. Initializes the SendActivateService.
     */
    private SendActiveReqManager() {
        sendActivateService = NetworkManager.getInstance().createService(SendActivateService.class);
    }
    /**
     * Calls the backend API to send an activation request to a backend officer.
     * @param nic The NIC (National Identification Card) of the user sending the request.
     * @param onSuccess A Runnable to be executed on successful request sending.
     * @param onError A Consumer function to handle errors and provide error messages.
     */
    //Calls backend API to Send Request to Back Officer
    public void sendActivateReq(
            String nic,
            Runnable onSuccess,
            Consumer<String> onError
    ) {
        if (!NetworkManager.getInstance().isNetworkAvailable()) {
            onError.accept("No internet connectivity");
            return;
        }
        sendActivateService.sendRequest(nic)
                .enqueue(new Callback<SendActivateResponse>() {
                    @Override
                    public void onResponse(Call<SendActivateResponse> call, Response<SendActivateResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            SendActivateResponse sendActivateResponse = response.body();
                            Log.i("UPDATE_RESPONSE:", String.valueOf(response.code()));
                            if (response.code() == 200) {
                                onSuccess.run();
                            } else {
                                onError.accept("An error occurred! " + sendActivateResponse.message);
                            }
                        } else {
                            onError.accept("Incorrect User Credentials");
                        }
                    }

                    @Override
                    public void onFailure(Call<SendActivateResponse> call, Throwable t) {
                        Log.e("UPDATE_ERROR:", t.toString());
                        Log.e("URL:", call.request().url().toString());
                        onError.accept("Error Occured While Sending Request");
                    }
                });
    }
}