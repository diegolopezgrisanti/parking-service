package com.parkingapp.parkingservice.infrastructure.entrypoint.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkingapp.parkingservice.application.createVehicle.CreateVehicleUseCase;
import com.parkingapp.parkingservice.domain.common.Country;
import com.parkingapp.parkingservice.domain.common.IdGenerator;
import com.parkingapp.parkingservice.domain.vehicle.Vehicle;
import com.parkingapp.parkingservice.domain.vehicle.VehicleColor;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.VehicleResponse;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.ContractTest;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContractTest
@WebMvcTest(controllers = VehiclesController.class)
class VehiclesControllerContractTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IdGenerator idGenerator;

    @MockBean
    private CreateVehicleUseCase createVehicleUseCase;

    private final UUID vehicleId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final String model = "model";
    private final String brand = "brand";
    private final VehicleColor color = VehicleColor.BLUE;
    private final String plate = "4632TFR";
    private final Country country = Country.ESP;

    Vehicle newVehicle = new Vehicle(
            vehicleId,
            brand,
            model,
            color,
            plate,
            country,
            userId
    );

    @Nested
    class CreateVehicle {
        @Test
        public void shouldCreateAVehicle() throws Exception {
            // GIVEN
            when(idGenerator.generate()).thenReturn(vehicleId);
            when(createVehicleUseCase.execute(newVehicle)).thenReturn(newVehicle);
            String expectedResponse = objectMapper.writeValueAsString(
                    new VehicleResponse(newVehicle)
            );

            String requestBody = String.format(
                    """
                        {
                            "brand": "%s",
                            "model": "%s",
                            "color": "%s",
                            "plate": "%s",
                            "country": "%s",
                            "user_id": "%s"
                        }
                    """,
                    brand,
                    model,
                    color,
                    plate,
                    country,
                    userId
            );

            // WHEN
            MockMvcResponse response = whenARequestToCreateAVehicleIsReceived(requestBody);

            // THEN
            response.then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body(CoreMatchers.equalTo(expectedResponse));

            verify(createVehicleUseCase).execute(newVehicle);

        }

        private MockMvcResponse whenARequestToCreateAVehicleIsReceived(String requestBody) {
            return RestAssuredMockMvc
                    .given()
                    .webAppContextSetup(context)
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .when()
                    .post("/vehicles");
        }
    }
}