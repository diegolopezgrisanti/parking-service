package com.parkingapp.parkingservice.infrastructure.entrypoint.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkingapp.parkingservice.application.createVehicle.CreateVehicleUseCase;
import com.parkingapp.parkingservice.application.getuservehicles.GetUserVehiclesUseCase;
import com.parkingapp.parkingservice.domain.common.Country;
import com.parkingapp.parkingservice.domain.common.IdGenerator;
import com.parkingapp.parkingservice.domain.exceptions.VehicleAlreadyExistsException;
import com.parkingapp.parkingservice.domain.vehicle.Vehicle;
import com.parkingapp.parkingservice.domain.vehicle.Color;
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

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.never;
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

    @MockBean
    private GetUserVehiclesUseCase getUserVehiclesUseCase;

    private final UUID vehicleId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final String model = "model";
    private final String brand = "brand";
    private final Color color = Color.BLUE;
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

            // WHEN
            MockMvcResponse response = whenARequestToCreateAVehicleIsReceived(requestBody);

            // THEN
            response.then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body(CoreMatchers.equalTo(expectedResponse));

            verify(createVehicleUseCase).execute(newVehicle);
        }

        @Test
        public void shouldReturn500WhenErrorOccurs() {
            // GIVEN
            when(idGenerator.generate()).thenReturn(vehicleId);
            when(createVehicleUseCase.execute(newVehicle)).thenThrow(new RuntimeException("ops"));

            // WHEN
            MockMvcResponse response = whenARequestToCreateAVehicleIsReceived(requestBody);

            // THEN
            response.then()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

            verify(createVehicleUseCase).execute(newVehicle);
        }

        @Test
        public void shouldReturn400WhenBodyIsIncorrect() {
            // GIVEN
            String incorrectRequestBody = String.format(
                    """
                        {
                            "invalid_field": "%s",
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
            MockMvcResponse response = whenARequestToCreateAVehicleIsReceived(incorrectRequestBody);

            // THEN
            response.then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            verify(createVehicleUseCase, never()).execute(newVehicle);
        }

        @Test
        public void shouldReturn409WhenVehicleAlreadyExists() {
            // GIVEN
            when(idGenerator.generate()).thenReturn(vehicleId);
            when(createVehicleUseCase.execute(newVehicle))
                    .thenThrow(new VehicleAlreadyExistsException());

            // WHEN
            MockMvcResponse response = whenARequestToCreateAVehicleIsReceived(requestBody);

            // THEN
            response.then()
                    .statusCode(HttpStatus.CONFLICT.value())
                    .body("message", CoreMatchers.equalTo("The combination of vehicle_id and user_id already exists."));

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

    @Nested
    class GetUserVehicles {
        @Test
        void shouldGetUserVehicles() throws Exception {
            // GIVEN
            List<Vehicle> vehicleList =  List.of(newVehicle);
            when(idGenerator.generate()).thenReturn(userId);
            when(getUserVehiclesUseCase.execute(userId)).thenReturn(vehicleList);
            String expectedResponse = objectMapper.writeValueAsString(vehicleList);

            // WHEN
            MockMvcResponse response = whenARequestToGetUserVehicleIsReceived(userId);

            // THEN
            response.then()
                    .statusCode(HttpStatus.OK.value())
                    .body(CoreMatchers.equalTo(expectedResponse));

            verify(getUserVehiclesUseCase).execute(userId);
        }

        private MockMvcResponse whenARequestToGetUserVehicleIsReceived(UUID userId) {
            return RestAssuredMockMvc
                    .given()
                    .webAppContextSetup(context)
                    .contentType(ContentType.JSON)
                    .pathParam("userId", userId.toString())
                    .when()
                    .get("/vehicles/{userId}");
        }
    }
}