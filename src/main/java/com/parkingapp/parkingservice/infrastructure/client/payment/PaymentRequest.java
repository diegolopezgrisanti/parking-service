package com.parkingapp.parkingservice.infrastructure.client.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private String paymentTypeId;
    private UUID paymentId;
    private int amountInCents;
    private String currency;
    private UUID userId;
    private UUID paymentMethodId;
}
