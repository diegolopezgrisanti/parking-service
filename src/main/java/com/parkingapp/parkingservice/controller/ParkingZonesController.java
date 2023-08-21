package com.parkingapp.parkingservice.controller;

import com.parkingapp.parkingservice.dto.ParkingZoneDTO;
import com.parkingapp.parkingservice.dto.ParkingZonesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parking-zones")
public class ParkingZonesController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ParkingZonesResponse getParkingZoneById(@RequestParam("city-id") String cityId) {
        // Mock response
        System.out.println(String.format("City Id requested is: %s", cityId));
       List<ParkingZoneDTO> dummyParkingZones = List.of(new ParkingZoneDTO(1234, "Sa Conca"), new ParkingZoneDTO(253, "Port"));
       return new ParkingZonesResponse(dummyParkingZones);
    }
}
