/**
 * FileName: DatabaseManager.java
 * FileType: Java File
 * Author: IT20140298 Shavinda W.A.P
 * Description: This class manages the application's Room database. It is implemented as a singleton
 *              to ensure there is only one instance of the database manager.
 */

package com.example.traveleasemobileapp.managers;

import androidx.room.Room;

import com.example.traveleasemobileapp.models.database.AppDatabase;

public class DatabaseManager {
    // Singleton instance of the DatabaseManager
    private static DatabaseManager singleton;
    // Name of the database
    private final String databaseName = "eadmobiledb";
    // Reference to the ContextManager for managing application context
    private ContextManager contextManager;
    // The Room database instance
    private AppDatabase database;
    /**
     * Get the instance of the DatabaseManager. If an instance does not exist, a new one is created.
     * @return The DatabaseManager instance.
     */
    //Returns DatabaseManager singleton object
    public static DatabaseManager getInstance(){
        if (singleton == null)
            singleton = new DatabaseManager();
        return singleton;
    }

    /**
     * Private constructor to prevent external instantiation. Initializes the database and context manager.
     */

    private DatabaseManager(){
        contextManager = ContextManager.getInstance();
        database = Room.databaseBuilder(
                contextManager.getApplicationContext(),
                AppDatabase.class,
                databaseName).build();
    }
    /**
     * Get the Room database instance.
     * @return The Room database instance for the application.
     */

    public AppDatabase db(){
        return database;
    }

}
