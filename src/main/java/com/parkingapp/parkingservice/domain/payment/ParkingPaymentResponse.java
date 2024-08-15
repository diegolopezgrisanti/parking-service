package com.parkingapp.parkingservice.domain.payment;

public abstract sealed class ParkingPaymentResponse permits Successful, Failure {
}

