/**
 * FileName: ContextManager.java
 * FileType: Java File
 * Author: IT20140298 Shavinda W.A.P
 * Description: This class manages the application context to provide a single point of access
 *              for the application's context throughout the mobile app. It is implemented as a singleton
 *              to ensure that there is only one instance of the context manager.
 */

package com.example.traveleasemobileapp.managers;

import android.content.Context;

public class ContextManager {
    // Singleton instance of the ContextManager
    private static ContextManager singleton;

    /**
     * Private constructor to prevent external instantiation.
     */
    private Context applicationContext;

    /**
     * Get the instance of the ContextManager. If an instance does not exist, a new one is created.
     * @return The ContextManager instance.
     */
    public static ContextManager getInstance() {
        if (singleton == null)
            singleton = new ContextManager();
        return singleton;
    }

    /**
     * Set the application context to be managed.
     * @param applicationContext The application context to be set.
     */
    public void setApplicationContext(Context applicationContext){
        this.applicationContext = applicationContext;
    }

    /**
     * Get the application context.
     * @return The application context that is being managed.
     */

    public Context getApplicationContext(){
        return applicationContext;
    }
}

