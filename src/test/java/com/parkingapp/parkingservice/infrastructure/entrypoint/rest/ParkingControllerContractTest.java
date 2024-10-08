package com.parkingapp.parkingservice.infrastructure.entrypoint.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkingapp.parkingservice.application.checkparkingstatus.CheckParkingStatusUseCase;
import com.parkingapp.parkingservice.application.createparking.CreateParkingUseCase;
import com.parkingapp.parkingservice.application.getparkingbyid.GetParkingByIdUseCase;
import com.parkingapp.parkingservice.domain.common.IdGenerator;
import com.parkingapp.parkingservice.domain.exceptions.VehicleNotFoundException;
import com.parkingapp.parkingservice.domain.parking.Parking;
import com.parkingapp.parkingservice.domain.parking.ParkingStatus;
import com.parkingapp.parkingservice.domain.parking.ParkingStatusCheck;
import com.parkingapp.parkingservice.domain.parking.PaymentStatus;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.ParkingCheckResponse;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.ParkingDetailsDTO;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.ParkingResponse;
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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static com.parkingapp.parkingservice.domain.parking.ParkingStatus.ACTIVE;
import static com.parkingapp.parkingservice.domain.parking.ParkingStatus.NOT_FOUND;
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
    String plate = "1234ABC";
    ParkingStatus parkingStatus = ACTIVE;


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

    ParkingStatusCheck parkingStatusCheck = new ParkingStatusCheck(parkingStatus, parking);

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

        @Test
        void shouldReturnVehicleNotFoundExceptionWhenVehicleDoesNotExist() {
            // GIVEN
            when(idGenerator.generate()).thenReturn(parkingId);
            when(createParkingUseCase.execute(parking)).thenThrow(new VehicleNotFoundException(vehicleId));

            // WHEN
            MockMvcResponse response = whenARequestToCreateAParkingIsReceived(requestBody);

            // THEN
            response.then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", CoreMatchers.equalTo(String.format("Vehicle with id %s not found", vehicleId)));

            verify(createParkingUseCase).execute(parking);
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
             // GIVEN
             when(getParkingByIdUseCase.execute(parkingId)).thenReturn(Optional.empty());
             
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


        @Test
        void shouldReturnParkingAndStatusWithParkingDetail() throws Exception {
            // GIVEN
            ParkingDetailsDTO details = new ParkingDetailsDTO(plate, parking.getEndDate());
            String expectedResponse = objectMapper.writeValueAsString(
                    new ParkingCheckResponse(parkingStatus, details)
            );
            when(checkParkingStatusUseCase.execute(plate, parkingZoneId)).thenReturn(parkingStatusCheck);

            // WHEN
            MockMvcResponse response = whenARequestToGetParkingStatusIsReceived();

            // THEN
            response.then()
                    .statusCode(HttpStatus.OK.value())
                    .body(CoreMatchers.equalTo(expectedResponse));

            verify(checkParkingStatusUseCase).execute(plate, parkingZoneId);
        }

        @Test
        void shouldReturnParkingAndStatusWithNoParkingDetail() throws  Exception {
             // GIVEN
            ParkingStatusCheck parkingStatusCheckNoDetails = new ParkingStatusCheck(NOT_FOUND, null);
            when(checkParkingStatusUseCase.execute(plate, parkingZoneId)).thenReturn(parkingStatusCheckNoDetails);

            // WHEN
            MockMvcResponse response = whenARequestToGetParkingStatusIsReceived();

            // THEN
            response.then()
                    .statusCode(HttpStatus.OK.value())
                    .body("check_result", CoreMatchers.equalTo("NOT_FOUND"))
                    .body("parking_details", CoreMatchers.nullValue());

            verify(checkParkingStatusUseCase).execute(plate, parkingZoneId);
        }

        @Test
        void shouldReturn500WhenErrorOccurs() {
            // GIVEN
            when(checkParkingStatusUseCase.execute(plate, parkingZoneId)).thenThrow(new RuntimeException("ops"));

            // WHEN
            MockMvcResponse response = whenARequestToGetParkingStatusIsReceived();

            // THEN
            response.then()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

            verify(checkParkingStatusUseCase).execute(plate, parkingZoneId);
        }

        private MockMvcResponse whenARequestToGetParkingStatusIsReceived() {
            return RestAssuredMockMvc
                    .given()
                    .webAppContextSetup(context)
                    .contentType(ContentType.JSON)
                    .param("plate", plate)
                    .param("parking_zone_id", parkingZoneId)
                    .when()
                    .get("/parkings/check");
        }
    }
}

