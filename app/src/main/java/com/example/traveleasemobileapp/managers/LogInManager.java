/**
 * FileName: LogInManager.java
 * FileType: Java File
 * Author: IT20140298 Shavinda W.A.P
 * Description: This class manages user login, authentication, and related operations, including
 *              validation of login credentials, making login requests, and storing user information.
 *              It is implemented as a singleton to ensure there is only one instance of the manager.
 */

package com.example.traveleasemobileapp.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.traveleasemobileapp.models.LogInRequest;
import com.example.traveleasemobileapp.models.LogInResponse;
import com.example.traveleasemobileapp.models.LogInService;
import com.example.traveleasemobileapp.models.UserDto;
import com.example.traveleasemobileapp.models.UserEntity;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInManager {
    // Singleton instance of the LogInManager
    private static LogInManager singleton;
    // Reference to the DatabaseManager for database operations
    private DatabaseManager databaseManager;
    // Reference to the LogInService for login API calls
    private LogInService logInService;

    // File and key names for storing login state
    private final String loginStateFile = "loginstate";
    private final String isLoggedInKey = "logged_in";
    private final String userNicKey = "user_nic";
    private final String userAccessToken = "access_token";

    /**
     * Get the instance of the LogInManager. If an instance does not exist, a new one is created.
     * @return The LogInManager instance.
     */
    public static LogInManager getInstance() {
        if (singleton == null)
            singleton = new LogInManager();

        return singleton;
    }
    /**
     * Private constructor to prevent external instantiation. Initializes the LogInService and DatabaseManager.
     */
    private LogInManager() {
        logInService = NetworkManager.getInstance().createService(LogInService.class);
        databaseManager = DatabaseManager.getInstance();
    }
    /**
     * Validate login credentials.
     * @param email The user's email.
     * @param password The user's password.
     * @return True if both email and password are valid; false otherwise.
     */
    public Boolean validateCredentials(String email, String password) {
        if (email == null || email.length() == 0)
            return false;

        if (password == null || password.length() == 0)
            return false;

        return true;
    }
    /**
     * Perform user login.
     * @param email The user's email.
     * @param password The user's password.
     * @param onSuccess A Runnable to be executed on successful login.
     * @param onError A Consumer function to handle errors and provide error messages.
     */
    public void login(
            String email,
            String password,
            Runnable onSuccess,
            Consumer<String> onError
    ) {
        if (!NetworkManager.getInstance().isNetworkAvailable()) {
            onError.accept("No internet connectivity");
            return;
        }

        LogInRequest body = new LogInRequest(email, password);
        logInService.login(body)
                .enqueue(new Callback<LogInResponse>() {
                    @Override
                    public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {

                        Log.d("Response", "Response code: " + response.code());

                        if (response.isSuccessful() && response.body() != null) {

                            LogInResponse logInResponse = response.body();


                            if (logInResponse.isActive) {

                                String AccessToken = logInResponse.accessToken;
                                String Nic = logInResponse.nic;
                                String Email = logInResponse.email;
                                String UserId = logInResponse.userId;
                                String FullName = logInResponse.fullName;
                                String UserName = logInResponse.userName;
                                String Message = logInResponse.message;
                                boolean IsActive = logInResponse.isActive;

                                UserDto userDto = new UserDto();
                                userDto.id = UserId;
                                userDto.Nic = Nic;
                                userDto.FullName = FullName;
                                userDto.Username = UserName;
                                userDto.Email = Email;
                                userDto.IsActive = IsActive;

                                setLoggedInState(true, Nic, AccessToken);
                                saveUserDetails(userDto);
                                onSuccess.run();
                            } else {
                                onError.accept("Login Failed, Your Account was Deactivated!");
                            }
                        } else {
                            onError.accept("Incorrect Username or Password");
                        }
                    }

                    @Override
                    public void onFailure(Call<LogInResponse> call, Throwable t) {
                        Log.e("LOGINERR:", t.toString());
                        onError.accept("Unknown error occurred when trying to log in");

                    }
                });

    }
    /**
     * Set the user's login state.
     * @param isLoggedIn True if the user is logged in; false otherwise.
     * @param Nic The NIC (National Identification Card) of the user.
     */
    public void setLoggedInState(boolean isLoggedIn, String Nic, String AccessToken){
        Context context = ContextManager.getInstance().getApplicationContext();
        SharedPreferences.Editor editor = context.getSharedPreferences(loginStateFile, Context.MODE_PRIVATE).edit();
        editor.putBoolean(isLoggedInKey, isLoggedIn);
        editor.putString(userNicKey, Nic);
        editor.putString(userAccessToken, AccessToken);
        editor.apply();
    }
    /**
     * Get the user's login state.
     * @return True if the user is logged in; false otherwise.
     */
    public boolean getIsLoggedIn(){
        Context context = ContextManager.getInstance().getApplicationContext();
        SharedPreferences prefs = context.getSharedPreferences(loginStateFile, Context.MODE_PRIVATE);
        return prefs.getBoolean(isLoggedInKey, false);
    }
    /**
     * Save user details to the local database.
     * @param userDto The user's data to be saved.
     */
    public void saveUserDetails(UserDto userDto) {
        new Thread(() -> {
            databaseManager.db().userDao().removeAll();
            databaseManager.db().userDao().insert(UserEntity.fromDto(userDto));
        }).start();
    }
    /**
     * Perform user logout by clearing login state and removing user details from the database.
     */
    public void logout() {
        new Thread(() -> {
            Context context = ContextManager.getInstance().getApplicationContext();
            SharedPreferences.Editor editor = context.getSharedPreferences(loginStateFile, Context.MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();
            databaseManager.db().userDao().removeAll();
        }).start();
    }
}
