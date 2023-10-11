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

    public String Password;

    public String ConfirmPassword;

    public String Role;

    public Boolean IsActive;


    public static UserEntity fromDto(UserDto dto){
        UserEntity entity = new UserEntity();
        entity.id = dto.id;
        entity.Nic = dto.Nic;
        entity.UserName = dto.Username;
        entity.FullName = dto.FullName;
        entity.Email = dto.Email;
        entity.Password = dto.Password;
        entity.ConfirmPassword = dto.ConfirmPassword;
        entity.Role = dto.Role;
        entity.IsActive = dto.IsActive;
        return entity;
    }

}
