/**
 * FileName: AppDatabase.java
 * FileType: Java File
 * Author: IT20140298 Shavinda W.A.P
 * Description: This class defines the Room database for the mobile application, specifying the
 *              entities and database version. It provides an abstract UserDao for database operations.
 */

package com.example.traveleasemobileapp.models.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.traveleasemobileapp.models.UserDao;
import com.example.traveleasemobileapp.models.UserEntity;

@Database(entities = {UserEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    /**
     * Get an instance of the UserDao for database operations.
     * @return The UserDao instance.
     */
    public abstract UserDao userDao();
}
