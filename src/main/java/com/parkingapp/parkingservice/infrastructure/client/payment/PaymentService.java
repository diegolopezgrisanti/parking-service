package com.parkingapp.parkingservice.infrastructure.client.payment;

import com.parkingapp.parkingservice.domain.parkingclosure.ParkingClosure;
import com.parkingapp.parkingservice.domain.payment.Failure;
import com.parkingapp.parkingservice.domain.payment.ParkingPaymentResponse;
import com.parkingapp.parkingservice.domain.payment.ParkingPaymentService;
import com.parkingapp.parkingservice.domain.payment.Successful;
import io.github.resilience4j.retry.Retry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Response;

public class PaymentService implements ParkingPaymentService {
    private final PaymentApi paymentApi;
    private final Retry retry;
    private final Logger log = LogManager.getLogger(getClass());

    public PaymentService(PaymentApi paymentApi, Retry retry) {
        this.paymentApi = paymentApi;
        this.retry = retry;
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
            Response<Void> response = Retry.decorateCheckedSupplier(
                    retry,
                    () -> paymentApi.chargeFee(paymentRequest).execute()
            ).get();

            if (response.isSuccessful()) {
                return new Successful();
            } else {
                return new Failure();
            }
        } catch (Throwable e) {
            log.error(
                    "Error while processing payment for parking with ID: {} - detail: {}",
                    parking.getParkingId(),
                    e
            );
            return new Failure();
        }
    }
}