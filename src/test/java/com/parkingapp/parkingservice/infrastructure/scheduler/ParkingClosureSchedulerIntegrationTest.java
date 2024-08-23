package com.parkingapp.parkingservice.infrastructure.scheduler;

import com.parkingapp.parkingservice.application.parkingclosure.ParkingClosureUseCase;
import com.parkingapp.parkingservice.infrastructure.config.SchedulersConfig;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verify;

@IntegrationTest
@SpringBootTest(
        classes = {SchedulersConfig.class},
        properties = {
                "scheduler.parking-closure.fixedDelay=1000",
                "scheduler.parking-closure.batchSize=1"
        }
)
class ParkingClosureSchedulerIntegrationTest {

    @MockBean
    ParkingClosureUseCase parkingClosureUseCase;

    @Test
    void shouldCallParkingClosureUseCase() {
        await()
                .atMost(1, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(parkingClosureUseCase).execute(1));
    }

}