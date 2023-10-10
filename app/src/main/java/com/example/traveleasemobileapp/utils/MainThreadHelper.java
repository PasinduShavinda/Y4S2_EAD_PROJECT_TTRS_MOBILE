package com.example.traveleasemobileapp.utils;

import android.os.Handler;
import android.os.Looper;


public class MainThreadHelper {

    public static void onMainThread(Runnable runnable){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(runnable);
    }
}

