package com.example.traveleasemobileapp.models;



import com.google.gson.annotations.SerializedName;

public class ScheduleResponse {
    @SerializedName("id")
    public String _id; // Rename field

    @SerializedName("trainId")
    public String trainId;

    @SerializedName("trainName")
    public String trainName;

    @SerializedName("departureCity")
    public String departureCity;

    @SerializedName("arrivalCity")
    public String arrivalCity;

    @SerializedName("departuretime")
    public String departuretime;

    @SerializedName("arrivaltime")
    public String arrivaltime;

    @SerializedName("class1reservation")
    public String class1reservation;

    @SerializedName("class2reservation")
    public String class2reservation;

    @SerializedName("reserved1seates")
    public Integer reserved1seates;

    @SerializedName("reserved2seates")
    public Integer reserved2seates;

    @SerializedName("stopStations")
    public String[] stopStations;

    @SerializedName("date")
    public String date;
}
