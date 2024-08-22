package com.parkingapp.parkingservice.infrastructure.client.payment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentApi {
    @POST("/payment")
    Call<Void> chargeFee(@Body PaymentRequest paymentRequest);
}
