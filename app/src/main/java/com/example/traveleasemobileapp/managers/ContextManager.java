package com.example.traveleasemobileapp.managers;

import android.content.Context;

public class ContextManager {
    private static ContextManager singleton;
    private Context applicationContext;

    //Returns ContextManager singleton object
    public static ContextManager getInstance() {
        if (singleton == null)
            singleton = new ContextManager();
        return singleton;
    }

    public void setApplicationContext(Context applicationContext){
        this.applicationContext = applicationContext;
    }

    public Context getApplicationContext(){
        return applicationContext;
    }
}

