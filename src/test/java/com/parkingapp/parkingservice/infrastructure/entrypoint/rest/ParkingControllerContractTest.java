package com.parkingapp.parkingservice.infrastructure.entrypoint.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkingapp.parkingservice.application.checkparkingstatus.CheckParkingStatusUseCase;
import com.parkingapp.parkingservice.application.createparking.CreateParkingUseCase;
import com.parkingapp.parkingservice.application.getparkingbyid.GetParkingByIdUseCase;
import com.parkingapp.parkingservice.domain.common.IdGenerator;
import com.parkingapp.parkingservice.domain.parking.Parking;
import com.parkingapp.parkingservice.domain.parking.ParkingStatusCheck;
import com.parkingapp.parkingservice.domain.parking.PaymentStatus;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.ParkingCheckResponse;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.ParkingDetailsDTO;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.ParkingResponse;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.ParkingZonesResponse;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.error.ErrorResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContractTest
@WebMvcTest(controllers = ParkingController.class)
class ParkingControllerContractTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateParkingUseCase createParkingUseCase;

    @MockBean
    private GetParkingByIdUseCase getParkingByIdUseCase;

    @MockBean
    private IdGenerator idGenerator;

    @MockBean
    private CheckParkingStatusUseCase checkParkingStatusUseCase;

    UUID parkingId = UUID.randomUUID();
    UUID parkingZoneId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    UUID vehicleId = UUID.randomUUID();
    UUID paymentMethodId = UUID.randomUUID();

    Parking parking = new Parking(
            parkingId,
            parkingZoneId,
            userId,
            vehicleId,
            paymentMethodId,
            Instant.now(),
            Instant.now().plus(1, ChronoUnit.HOURS),
            PaymentStatus.PENDING
    );

    String requestBody = String.format(
            """
                {
                    "parking_zone_id": "%s",
                    "vehicle_id": "%s",
                    "payment_method_id": "%s",
                    "start_date": "%s",
                    "end_date": "%s",
                    "payment_status": "%s"
                }
            """,
            parkingZoneId,
            vehicleId,
            paymentMethodId,
            parking.getStartDate(),
            parking.getEndDate(),
            "PENDING"
    );

    @Nested
    class CreateParking {

        @Test
        public void shouldCreateAParking() throws Exception {
            // GIVEN
            when(idGenerator.generate()).thenReturn(parkingId);
            when(createParkingUseCase.execute(parking)).thenReturn(parking);
            String expectedResponse = objectMapper.writeValueAsString(
                    new ParkingResponse(parking)
            );

            // WHEN
            MockMvcResponse response = whenARequestToCreateAParkingIsReceived(requestBody);

            // THEN
            response.then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body(CoreMatchers.equalTo(expectedResponse));

            verify(createParkingUseCase).execute(parking);
        }

        @Test
        void shouldReturn500WhenErrorOccurs() {
            // GIVEN
            when(idGenerator.generate()).thenReturn(parkingId);
            when(createParkingUseCase.execute(parking)).thenThrow(new RuntimeException("ops"));

            // WHEN
            MockMvcResponse response = whenARequestToCreateAParkingIsReceived(requestBody);

            // THEN
            response.then()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

            verify(createParkingUseCase).execute(parking);
        }

        @Test
        void shouldReturn400WhenBodyIsIncorrect() {
            // Given
            String incorrectRequestBody = String.format(
                    """
                        {
                        "InvalidParkingZone": "%s",
                        "vehicle_id": "%s",
                        "payment_method_id": "%s",
                        "start_date": "%s",
                        "end_date": "%s",
                        "payment_status": "%s"
                        }
                    """,
                    parkingZoneId,
                    vehicleId,
                    paymentMethodId,
                    parking.getStartDate(),
                    parking.getEndDate(),
                    "PENDING"
            );

            // When
            MockMvcResponse response = whenARequestToCreateAParkingIsReceived(incorrectRequestBody);

            // Then
            response.then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            verify(createParkingUseCase, never()).execute(parking);
        }

        private MockMvcResponse whenARequestToCreateAParkingIsReceived(String requestBody) {
            return RestAssuredMockMvc
                    .given()
                    .webAppContextSetup(context)
                    .contentType(ContentType.JSON)
                    .header("USER_ID", userId)
                    .body(requestBody)
                    .when()
                    .post("/parkings");
        }

    }

    @Nested
    class GetParkingById {

        @Test
        void ShouldReturnParkingById() throws Exception {
            // GIVEN
            when(getParkingByIdUseCase.execute(parkingId)).thenReturn(Optional.of(parking));
            String expectedResponse = objectMapper.writeValueAsString(new ParkingResponse(parking));

            // WHEN & THEN
            MockMvcResponse response = whenARequestToGetParkingByIdIsReceived();

            response.then()
                    .statusCode(HttpStatus.OK.value())
                    .body(CoreMatchers.equalTo(expectedResponse));

            verify(getParkingByIdUseCase).execute(parkingId);
        }

        @Test
        void shouldReturn404WhenParkingDoesNotExist() {
            // WHEN
            MockMvcResponse response = whenARequestToGetParkingByIdIsReceived();

            // THEN
            response.then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        void shouldReturn500WhenErrorOccurs() {
            // GIVEN
            when(getParkingByIdUseCase.execute(parkingId)).thenThrow(new RuntimeException("ops"));

            // WHEN
            MockMvcResponse response = whenARequestToGetParkingByIdIsReceived();

            // THEN
            response.then()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

            verify(getParkingByIdUseCase).execute(parkingId);
        }

        private MockMvcResponse whenARequestToGetParkingByIdIsReceived() {
            return RestAssuredMockMvc
                    .given()
                    .webAppContextSetup(context)
                    .contentType(ContentType.JSON)
                    .pathParam("parkingId", parkingId.toString())
                    .when()
                    .get("/parkings/{parkingId}");
        }
    }

    @Nested
    class CheckParking {

    }

}