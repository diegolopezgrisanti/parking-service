package com.parkingapp.parkingservice.domain.payment;

import com.parkingapp.parkingservice.domain.parkingclosure.ParkingClosure;

public interface ParkingPaymentService {
    ParkingPaymentResponse chargeFee(ParkingClosure parking, int amount);
}
