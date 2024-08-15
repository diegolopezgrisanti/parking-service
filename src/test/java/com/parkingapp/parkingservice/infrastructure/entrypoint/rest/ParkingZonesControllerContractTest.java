package com.parkingapp.parkingservice.infrastructure.entrypoint.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkingapp.parkingservice.application.getparkingzones.GetParkingZonesUseCase;
import com.parkingapp.parkingservice.domain.common.Amount;
import com.parkingapp.parkingservice.domain.common.IdGenerator;
import com.parkingapp.parkingservice.domain.common.Location;
import com.parkingapp.parkingservice.domain.parkingzone.ParkingZone;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.ParkingZoneDTO;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.ParkingZonesResponse;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.ContractTest;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.hamcrest.CoreMatchers;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.context.WebApplicationContext;

import javax.money.Monetary;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ContractTest
@WebMvcTest(controllers = ParkingZonesController.class)
class ParkingZonesControllerContractTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IdGenerator idGenerator;

    @MockBean
    private GetParkingZonesUseCase getParkingZonesUseCase;

    private final UUID cityId = UUID.randomUUID();
    private final List<ParkingZone> parkingZoneList = List.of(new ParkingZone(
            UUID.randomUUID(),
            "Test zone",
            UUID.randomUUID(),
            new Location(new BigDecimal("40.7128"), new BigDecimal("-74.0060")),
            new Amount(Monetary.getCurrency("EUR"), 100))
    );

    List<ParkingZoneDTO> parkingZoneDTOList = parkingZoneList.stream()
            .map(parkingZone -> new ParkingZoneDTO(
                    parkingZone.getId(),
                    parkingZone.getName(),
                    parkingZone.getLocation(),
                    parkingZone.getAmount()
            ))
            .collect(Collectors.toList());

    @Test
    void getParkingZonesByCityId() throws Exception {
        // GIVEN
        when(getParkingZonesUseCase.execute(cityId)).thenReturn(parkingZoneList);
        String expectedResponse = objectMapper.writeValueAsString(new ParkingZonesResponse(parkingZoneDTOList));

        // WHEN & THEN
        MockMvcResponse response = whenARequestToGetParkingZonesByCityIdIsReceived();

        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(CoreMatchers.equalTo(expectedResponse));

        verify(getParkingZonesUseCase).execute(cityId);
    }

    @Test
    void shouldReturn500WhenErrorOccurs() {
        // Given
        when(getParkingZonesUseCase.execute(cityId)).thenThrow(new RuntimeException("ops"));

        // When
        MockMvcResponse response = whenARequestToGetParkingZonesByCityIdIsReceived();

        // Then
        response.then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        verify(getParkingZonesUseCase).execute(cityId);
    }

    private MockMvcResponse whenARequestToGetParkingZonesByCityIdIsReceived() {
        return RestAssuredMockMvc
                .given()
                .webAppContextSetup(context)
                .contentType(ContentType.JSON)
                .param("city-id", cityId.toString())
                .when()
                .get("/parking-zones");
    }
}
