package com.parkingapp.parkingservice.infrastructure.client.payment;

import com.parkingapp.parkingservice.domain.parkingclosure.ParkingClosure;
import com.parkingapp.parkingservice.domain.payment.Failure;
import com.parkingapp.parkingservice.domain.payment.ParkingPaymentResponse;
import com.parkingapp.parkingservice.domain.payment.ParkingPaymentService;
import com.parkingapp.parkingservice.domain.payment.Successful;
import retrofit2.Response;

import java.io.IOException;

public class PaymentService implements ParkingPaymentService {
    private final PaymentApi paymentApi;

    public PaymentService(PaymentApi paymentApi) {
        this.paymentApi = paymentApi;
    }

    @Override
    public ParkingPaymentResponse chargeFee(ParkingClosure parking, int amount) {
        PaymentRequest paymentRequest = new PaymentRequest(
            "PARKING",
            parking.getParkingId(),
            amount,
            parking.getFeePerMinute().getCurrency().getCurrencyCode(),
            parking.getUserId(),
            parking.getPaymentMethodId()
        );

        try {
            Response<Void> response = paymentApi.chargeFee(paymentRequest).execute();

            if (response.isSuccessful()) {
                return new Successful();
            } else {
                return new Failure();
            }
        } catch (IOException e) {
            return new Failure();
        }
    }
}