package com.parkingapp.parkingservice.domain.vehicle;

import com.parkingapp.parkingservice.domain.common.Country;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    private final String plateUppercase = "4632TFR";
    private final String plateLowercase = "4632tfr";

    @Test
    void shouldCreateAVehicleWithPlateInUppercase() {
           Vehicle newVehicle = new Vehicle(
                   UUID.randomUUID(),
                   "brand",
                   "model",
                   Color.BLUE,
                   plateLowercase,
                   Country.ESP,
                   UUID.randomUUID()
           );

           assert(newVehicle.getPlate()).equals(plateUppercase);
    }

    @Test
    void shouldSavePlateInUppercaseWhenUseSetter() {
        Vehicle newVehicle = new Vehicle(
                UUID.randomUUID(),
                "brand",
                "model",
                Color.BLUE,
                "1234aaa",
                Country.ESP,
                UUID.randomUUID()
        );

        newVehicle.setPlate(plateLowercase);
        assert(newVehicle.getPlate()).equals(plateUppercase);
    }

}