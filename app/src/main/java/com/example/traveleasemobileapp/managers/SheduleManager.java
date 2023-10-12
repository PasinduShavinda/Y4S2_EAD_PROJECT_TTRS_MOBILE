package com.example.traveleasemobileapp.managers;

import android.util.Log;
import com.example.traveleasemobileapp.models.ScheduleResponse;
import com.example.traveleasemobileapp.models.ScheduleService;
import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SheduleManager {

    private static SheduleManager singleton;

    private List<ScheduleResponse> filteredSchedules;
    private ScheduleService scheduleService;

    //return SignUpManager singleton object
    public static SheduleManager getInstance() {
        if (singleton == null)
            singleton = new SheduleManager();

        return singleton;
    }

    private SheduleManager() {
        scheduleService = NetworkManager.getInstance().createService(ScheduleService.class);
    }

    //Calls backend API to create a new account
    public void filterSchedules(String date, String station1, String station2, Runnable onSuccess, Consumer<String> onError) {
        if (!NetworkManager.getInstance().isNetworkAvailable()) {
            onError.accept("No Internet Connectivity");
            return;
        }

        scheduleService.filter(date, station1, station2)
                .enqueue(new Callback<List<ScheduleResponse>>() {
                    @Override
                    public void onResponse(Call<List<ScheduleResponse> >call, Response<List<ScheduleResponse>> response) {
                        Log.i("FILTER_RESPONSE_CODE:", String.valueOf(response.code()));
                        Log.i("URL:", call.request().url().toString());

                        if (response.isSuccessful()) {
                            filteredSchedules = response.body(); // Assign the response data to filteredSchedules
                            onSuccess.run();
                        } else {
                            onError.accept("An error occurred!");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ScheduleResponse>> call, Throwable t) {
                        Log.e("FILTER_ERROR:", t.toString());
                        onError.accept("Unknown error occurred when filtering schedules");
                    }
                });
    }

    public List<ScheduleResponse> getFilteredSchedules() {
        return filteredSchedules;
    }

}


