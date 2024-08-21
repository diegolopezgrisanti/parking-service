package com.parkingapp.parkingservice.infrastructure.client.payment;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.parkingapp.parkingservice.domain.common.Amount;
import com.parkingapp.parkingservice.domain.parkingclosure.ParkingClosure;
import com.parkingapp.parkingservice.domain.payment.Failure;
import com.parkingapp.parkingservice.domain.payment.ParkingPaymentResponse;
import com.parkingapp.parkingservice.domain.payment.Successful;
import com.parkingapp.parkingservice.infrastructure.config.ObjectMapperConfig;
import com.parkingapp.parkingservice.infrastructure.config.client.PaymentClientConfig;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.money.Monetary;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@IntegrationTest
@SpringBootTest(
        classes = {PaymentClientConfig.class, ObjectMapperConfig.class}
)
public class PaymentServiceIntegrationTest {
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

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
        wireMockServer.start();

        WireMock.configureFor("localhost", wireMockServer.port());

        PaymentApi paymentApi = new Retrofit.Builder()
                .baseUrl("http://localhost:" + wireMockServer.port())
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(PaymentApi.class);

        paymentService = new PaymentService(paymentApi);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void shouldReturnASuccessfulResponse() {
        wireMockServer.stubFor(WireMock.post(urlEqualTo("/payment"))
                .willReturn(aResponse().withStatus(202)));

        ParkingPaymentResponse response = paymentService.chargeFee(parkingClosure, amountInCents);

        assertThat(response).isInstanceOf(Successful.class);
    }

    @Test
    public void shouldReturnAFailureResponse() {
        wireMockServer.stubFor(WireMock.post(urlEqualTo("/payment"))
                .willReturn(aResponse().withStatus(500)));

        ParkingPaymentResponse response = paymentService.chargeFee(parkingClosure, amountInCents);

        assertThat(response).isInstanceOf(Failure.class);
    }

}