// Kalansooriya S. H.
// TI20137700
package com.example.traveleasemobileapp.models;



public class ReservationRequest {
    Integer seatcount1;
    Integer seatcount2;
    String trainName;
    String trainId;
    String userId;
    String sheduleId;
    String date;


    public ReservationRequest(Integer seatcount1,
    Integer seatcount2,
    String trainName,
    String trainId,
    String userId,
    String sheduleId,
    String date) {
        this.seatcount1 = seatcount1;
        this.seatcount2 = seatcount2;
        this.trainName = trainName;
        this.trainId = trainId;
        this.userId = userId;
        this.sheduleId = sheduleId;
        this.date = date;

    }
}

