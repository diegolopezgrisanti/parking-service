package com.parkingapp.parkingservice.infrastructure.config.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkingapp.parkingservice.domain.payment.ParkingPaymentService;
import com.parkingapp.parkingservice.infrastructure.client.payment.PaymentApi;
import com.parkingapp.parkingservice.infrastructure.client.payment.PaymentService;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.net.URI;
import java.time.Duration;


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
        return new PaymentService(paymentApi);
    }
}
