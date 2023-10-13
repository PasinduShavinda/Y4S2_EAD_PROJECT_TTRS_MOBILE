/**
 * FileName: NetworkManager.java
 * FileType: Java File
 * Author: IT20140298 Shavinda W.A.P
 * Description: This class manages network-related operations, including creating Retrofit services,
 *              checking internet connectivity, and displaying network status messages through Toast.
 *              It is implemented as a singleton to ensure there is only one instance of the manager.
 */
package com.example.traveleasemobileapp.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {
    // Singleton instance of the NetworkManager
    private static NetworkManager singleton;
    // Retrofit instance for making network requests
    private Retrofit retrofit;
    // Base URL for network requests
    private final String baseUrl = "http://10.0.2.2:5239/api/v1/";

    // ConnectivityManager for checking network connectivity
    private ConnectivityManager connectivityManager;

    /**
     * Get the instance of the NetworkManager. If an instance does not exist, a new one is created.
     * @return The NetworkManager instance.
     */
    public static NetworkManager getInstance(){
        if (singleton == null)
            singleton = new NetworkManager();
        return singleton;
    }
    /**
     * Private constructor to prevent external instantiation. Initializes the Retrofit instance.
     */
    private NetworkManager(){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    /**
     * Create a service of the specified type using Retrofit.
     * @param serviceClass The class type of the service to be created.
     * @param <T> The generic type of the service.
     * @return The created service instance.
     */

    public <T> T createService(Class<T> serviceClass)
    {
        return retrofit.create(serviceClass);
    }

    /**
     * Check whether the device is connected to the internet or not.
     * @return True if the device is connected to the internet; false otherwise.
     */
    public boolean isNetworkAvailable(){
        Context context = ContextManager.getInstance().getApplicationContext();

        if (connectivityManager == null) {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }

        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        boolean available = info != null && info.isConnectedOrConnecting();

        if (!available){
            Toast.makeText(context, "Internet Connection Unavailable", Toast.LENGTH_LONG).show();
        }

        return available;
    }


}
