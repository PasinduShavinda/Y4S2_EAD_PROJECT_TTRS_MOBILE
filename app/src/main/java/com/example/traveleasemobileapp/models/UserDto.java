/**
 * FileName: UserDto.java
 * FileType: Java File
 * Author: IT20140298 Shavinda W.A.P
 * Description: This class represents a Data Transfer Object (DTO) for user data. It contains various fields
 *              that encapsulate user information, including 'id,' 'Nic' (National Identification Card), 'Email,'
 *              'Username,' 'FullName,' 'Role,' and 'IsActive.'
 */
package com.example.traveleasemobileapp.models;

public class UserDto {
    public String id;
    public String Nic;

    public String Email;
    public String Username;
    public String FullName;
    public String Role;
    public Boolean IsActive;
}
