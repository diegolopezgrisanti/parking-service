package com.parkingapp.parkingservice.controller;

import com.parkingapp.parkingservice.dto.ParkingZoneDTO;
import com.parkingapp.parkingservice.dto.ParkingZonesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-zones/{cityId}")
public class ParkingZonesController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    // Mock response
    public ParkingZonesResponse getParkingZoneById() {
        // Mock response
       List<ParkingZoneDTO> dummyParkingZones = List.of(new ParkingZoneDTO(1234, "Sa Conca"), new ParkingZoneDTO(253, "Port"));
       return new ParkingZonesResponse(dummyParkingZones);
       //return parkingZonesService.findParkingZoneById(id);

    }
}