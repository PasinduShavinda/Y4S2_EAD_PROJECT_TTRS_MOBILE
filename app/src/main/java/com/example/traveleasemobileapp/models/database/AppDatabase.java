package com.example.traveleasemobileapp.models.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.traveleasemobileapp.models.UserDao;
import com.example.traveleasemobileapp.models.UserEntity;

@Database(entities = {UserEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
