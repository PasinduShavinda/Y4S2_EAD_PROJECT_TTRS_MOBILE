/**
 * FileName: SignUpManager.java
 * FileType: Java File
 * Author: IT20140298 Shavinda W.A.P
 * Description: This class manages user signup and registration through backend API calls.
 *              It is implemented as a singleton to ensure there is only one instance of the manager.
 */

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
    // Singleton instance of the SignUpManager
    private static SignUpManager singleton;
    // Reference to the SignUpService for backend API calls
    private SignUpService signUpService;
    /**
     * Get the instance of the SignUpManager. If an instance does not exist, a new one is created.
     * @return The SignUpManager instance.
     */
    public static SignUpManager getInstance() {
        if (singleton == null)
            singleton = new SignUpManager();

        return singleton;
    }
    /**
     * Private constructor to prevent external instantiation. Initializes the SignUpService.
     */
    private SignUpManager() {
        signUpService = NetworkManager.getInstance().createService(SignUpService.class);
    }
    /**
     * Perform user signup and registration.
     * @param Nic The NIC (National Identification Card) of the user.
     * @param Email The user's email.
     * @param Username The user's username.
     * @param FullName The user's full name.
     * @param Password The user's password.
     * @param ConfirmPassword The confirmation of the user's password.
     * @param Role The role of the user.
     * @param IsActive True if the user is active; false otherwise.
     * @param onSuccess A Runnable to be executed on successful signup.
     * @param onError A Consumer function to handle errors and provide error messages.
     */
    public void signUp(
            String Nic,
            String Email,
            String Username,
            String FullName,
            String Password,
            String ConfirmPassword,
            String Role,
            Boolean IsActive,
            Runnable onSuccess,
            Consumer<String> onError
    ) {
        if (!NetworkManager.getInstance().isNetworkAvailable()) {
            onError.accept("No Internet Connectivity");
            return;
        }

        SignUpRequest body = new SignUpRequest(Nic, Email, Username, FullName, Password, ConfirmPassword, Role, IsActive);

        signUpService.signUp(body)
                .enqueue(new Callback<SignUpResponse>() {
                    @Override
                    public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                        Log.i("SIGNUPRES:", String.valueOf(response.code()));
                        Log.i("URL:", call.request().url().toString());

                        if (response.code() == 200) {
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
