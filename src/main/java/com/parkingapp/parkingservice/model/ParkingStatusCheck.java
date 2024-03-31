package com.parkingapp.parkingservice.model;

public class ParkingStatusCheck {

    private ParkingStatus parkingStatus;
    private Parking parking;

    public ParkingStatusCheck(ParkingStatus parkingStatus, Parking parking) {
        this.parkingStatus = parkingStatus;
        this.parking = parking;
    }

    public ParkingStatus getParkingStatus() {
        return parkingStatus;
    }

    public void setParkingStatus(ParkingStatus parkingStatus) {
        this.parkingStatus = parkingStatus;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

}
