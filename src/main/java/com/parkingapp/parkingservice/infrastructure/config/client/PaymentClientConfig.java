package com.parkingapp.parkingservice.infrastructure.config.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkingapp.parkingservice.domain.payment.ParkingPaymentService;
import com.parkingapp.parkingservice.infrastructure.client.payment.PaymentApi;
import com.parkingapp.parkingservice.infrastructure.client.payment.PaymentService;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.net.URI;
import java.time.Duration;
import java.util.function.Predicate;


@Configuration
public class PaymentClientConfig {

    @Value("${clients.payment.url}")
    URI baseUrl;

    @Value("${clients.payment.timeout.connect}")
    long connectTimeout;

    @Value("${clients.payment.timeout.read}")
    long readTimeout;

    @Bean
    public PaymentApi paymentApi(ObjectMapper objectMapper) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofMillis(connectTimeout))
                .readTimeout(Duration.ofMillis(readTimeout))
                .build();
        return new Retrofit.Builder()
                .baseUrl(baseUrl.toString())
                .client(httpClient)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build()
                .create(PaymentApi.class);
    }

    @Bean
    public ParkingPaymentService parkingPaymentService(PaymentApi paymentApi) {
        Predicate<Object> retryOnServerError = result -> {
            if (result instanceof Response<?> response) {
                return response.code() >= HttpStatus.INTERNAL_SERVER_ERROR.value();
            }
            return false;
        };
        Retry retry = Retry.of(
                "payment",
                RetryConfig.custom()
                        .maxAttempts(3)
                        .retryOnResult(retryOnServerError)
                        .build()
        );
        return new PaymentService(paymentApi, retry);
    }
}
