package com.parkingapp.parkingservice.infrastructure.entrypoint.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkingapp.parkingservice.application.findallcities.FindAllCitiesUseCase;
import com.parkingapp.parkingservice.domain.city.City;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.CitiesResponse;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.CityDTO;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.ContractTest;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContractTest
@WebMvcTest(controllers = CitiesController.class)
public class CitiesControllerContractTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private FindAllCitiesUseCase findAllCitiesUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldGetAllCities() throws Exception {
        // Given
        City city = new City(UUID.randomUUID(), "Barcelona");
        when(findAllCitiesUseCase.execute()).thenReturn(List.of(city));
        String expectedResponse = objectMapper.writeValueAsString(
                new CitiesResponse(
                        List.of(new CityDTO(city.getId(), city.getName()))
                )
        );

        // When
        MockMvcResponse response = whenARequestToGetAllCitiesIsReceived();

        // Then
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(CoreMatchers.equalTo(expectedResponse));

        verify(findAllCitiesUseCase).execute();
    }

    @Test
    public void shouldReturn500WhenErrorOccurs() {
        // Given
        when(findAllCitiesUseCase.execute()).thenThrow(new RuntimeException("ops"));

        // When
        MockMvcResponse response = whenARequestToGetAllCitiesIsReceived();

        // Then
        response.then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        verify(findAllCitiesUseCase).execute();
    }

    private MockMvcResponse whenARequestToGetAllCitiesIsReceived() {
        return RestAssuredMockMvc
                .given()
                .webAppContextSetup(context)
                .contentType(ContentType.JSON)
                .when()
                .get("/cities");
    }

}
