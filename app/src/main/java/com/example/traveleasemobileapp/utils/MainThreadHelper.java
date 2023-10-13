/**
 * FileName: MainThreadHelper.java
 * FileType: Java File
 * Author: [Author's Name]
 * Description: This utility class provides a method for executing a given Runnable on the main thread (UI thread).
 *              It uses a Handler to post the Runnable on the main thread's message queue, ensuring that the
 *              provided task is executed on the main thread.
 */

package com.example.traveleasemobileapp.utils;

import android.os.Handler;
import android.os.Looper;


public class MainThreadHelper {
    /**
     * Execute a Runnable on the main thread (UI thread).
     * @param runnable The Runnable to be executed on the main thread.
     */
    public static void onMainThread(Runnable runnable){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(runnable);
    }
}

