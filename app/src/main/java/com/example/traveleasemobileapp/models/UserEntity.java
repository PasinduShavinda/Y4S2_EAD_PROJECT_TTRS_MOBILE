/**
 * FileName: UserEntity.java
 * FileType: Java File
 * Author: IT20140298 Shavinda W.A.P
 * Description: This class represents an entity in the database for user records. It is annotated with the Room
 *              annotations to define the structure of the UserEntity table. The fields in this entity correspond
 *              to user attributes, including 'Nic' (National Identification Card), 'id,' 'Email,' 'UserName,'
 *              'FullName,' 'Role,' and 'IsActive.' It also includes a static method 'fromDto' for converting
 *              a UserDto object to a UserEntity object.
 */

package com.example.traveleasemobileapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class UserEntity {
    @PrimaryKey()
    @NotNull
    public String Nic;

    public String id;

    public String Email;

    public String UserName;

    public String FullName;

    public String Role;

    public Boolean IsActive;
    /**
     * Static method to convert a UserDto object to a UserEntity object.
     * @param dto The UserDto object to be converted.
     * @return A UserEntity object representing the converted user data.
     */

    public static UserEntity fromDto(UserDto dto){
        UserEntity entity = new UserEntity();
        entity.id = dto.id;
        entity.Nic = dto.Nic;
        entity.UserName = dto.Username;
        entity.FullName = dto.FullName;
        entity.Email = dto.Email;
        entity.Role = dto.Role;
        entity.IsActive = dto.IsActive;
        return entity;
    }

}
