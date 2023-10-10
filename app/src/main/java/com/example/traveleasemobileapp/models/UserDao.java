package com.example.traveleasemobileapp.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM UserEntity")
    List<UserEntity> getAll();

    @Insert
    void insert(UserEntity user);

    @Update
    void update(UserEntity user);

    @Query("DELETE FROM UserEntity")
    void removeAll();

}
