package com.example.traveleasemobileapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class UserEntity {
    @PrimaryKey()
    @NotNull
    public String nic;

    public String id;

    public String firstName;

    public String lastName;

    public String dateOfBirth;

    public int phoneNo;

    public String email;

    public String password;

    public boolean isActive;


    public static UserEntity fromDto(UserDto dto){
        UserEntity entity = new UserEntity();
        entity.id = dto.id;
        entity.nic = dto.nic;
        entity.firstName = dto.firstName;
        entity.lastName = dto.lastName;
        entity.dateOfBirth = dto.dateOfBirth;
        entity.email = dto.email;
        entity.password = dto.password;
        entity.phoneNo = dto.phoneNo;
        return entity;
    }

}
