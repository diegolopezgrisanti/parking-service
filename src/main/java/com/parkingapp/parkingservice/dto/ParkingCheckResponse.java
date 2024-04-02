package com.parkingapp.parkingservice.dto;

import com.parkingapp.parkingservice.model.ParkingStatus;

public class ParkingCheckResponse {

    private ParkingStatus checkResult;

    private ParkingDetailsDTO parkingDetails;

    public ParkingCheckResponse(ParkingStatus checkResult, ParkingDetailsDTO parkingDetails) {
        this.checkResult = checkResult;
        this.parkingDetails = parkingDetails;
    }

    public ParkingStatus getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(ParkingStatus checkResult) {
        this.checkResult = checkResult;
    }

    public ParkingDetailsDTO getParkingDetails() {
        return parkingDetails;
    }

    public void setParkingDetails(ParkingDetailsDTO parkingDetails) {
        this.parkingDetails = parkingDetails;
    }
}
