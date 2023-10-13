/**
 * FileName: UserDao.java
 * FileType: Java Interface
 * Author: IT20140298 Shavinda W.A.P
 * Description: This interface defines the Data Access Object (DAO) for user entities. It provides a set of methods
 *              for interacting with the UserEntity table in the database, including querying for all users,
 *              inserting a new user, updating an existing user, and removing all users.
 */

package com.example.traveleasemobileapp.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    /**
     * Query to retrieve all user entities from the database.
     * @return A list of UserEntity objects representing all users in the database.
     */
    @Query("SELECT * FROM UserEntity")
    List<UserEntity> getAll();
    /**
     * Insert a new user entity into the database.
     * @param user The UserEntity object to insert.
     */

    @Insert
    void insert(UserEntity user);
    /**
     * Update an existing user entity in the database.
     * @param user The UserEntity object to update.
     */
    @Update
    void update(UserEntity user);
    /**
     * Query to remove all user entities from the database.
     */
    @Query("DELETE FROM UserEntity")
    void removeAll();

}
