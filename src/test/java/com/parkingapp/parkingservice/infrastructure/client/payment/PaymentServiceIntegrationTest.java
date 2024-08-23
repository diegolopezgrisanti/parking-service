package com.parkingapp.parkingservice.infrastructure.client.payment;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.parkingapp.parkingservice.domain.common.Amount;
import com.parkingapp.parkingservice.domain.parkingclosure.ParkingClosure;
import com.parkingapp.parkingservice.domain.payment.Failure;
import com.parkingapp.parkingservice.domain.payment.ParkingPaymentResponse;
import com.parkingapp.parkingservice.domain.payment.Successful;
import com.parkingapp.parkingservice.infrastructure.config.ObjectMapperConfig;
import com.parkingapp.parkingservice.infrastructure.config.client.PaymentClientConfig;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.IntegrationTest;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.WithWireMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.money.Monetary;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@WithWireMock
@SpringBootTest(
        classes = {PaymentClientConfig.class, ObjectMapperConfig.class}
)
@IntegrationTest
public class PaymentServiceIntegrationTest {
    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private PaymentService paymentService;

    private final UUID parkingId = UUID.randomUUID();
    private final int amountInCents = 1500;
    private final UUID userId = UUID.randomUUID();
    private final UUID paymentMethodId = UUID.randomUUID();
    private final Instant startDate = Instant.now().minus(60, ChronoUnit.MINUTES);
    private final Instant endDate = Instant.now().minus(1, ChronoUnit.MINUTES);

    ParkingClosure parkingClosure = new ParkingClosure(
            new Amount(Monetary.getCurrency("EUR"), 100),
            startDate,
            endDate,
            parkingId,
            userId,
            paymentMethodId
    );

    String expectedRequestBody = String.format(
            """
                {
                    "payment_type_id": "%s",
                    "payment_id": "%s",
                    "amount_in_cents": %d,
                    "currency": "%s",
                    "user_id": "%s",
                    "payment_method_id": "%s"
                }
            """,
            "PARKING",
            parkingId,
            amountInCents,
            "EUR",
            userId,
            paymentMethodId
    );

    MappingBuilder baseResponse = post(urlPathEqualTo("/payment"))
            .withRequestBody(WireMock.equalToJson(expectedRequestBody));

    @Test
    public void shouldReturnASuccessfulResponse() {
        System.out.println(expectedRequestBody);
        wireMockServer.givenThat(
                    baseResponse.willReturn(aResponse().withStatus(202))
        );

        ParkingPaymentResponse response = paymentService.chargeFee(parkingClosure, amountInCents);

        assertThat(response).isInstanceOf(Successful.class);
    }

    @Test
    public void shouldReturnAFailureResponse() {
        wireMockServer.givenThat(
                baseResponse.willReturn(aResponse().withStatus(500))
        );

        ParkingPaymentResponse response = paymentService.chargeFee(parkingClosure, amountInCents);

        assertThat(response).isInstanceOf(Failure.class);
    }

    @Test
    public void shouldHandleIOExceptionAsFailureResponse() {
        wireMockServer.givenThat(
                baseResponse.willReturn(aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER))
        );

        ParkingPaymentResponse response = paymentService.chargeFee(parkingClosure, amountInCents);

        assertThat(response).isInstanceOf(Failure.class);
    }

}