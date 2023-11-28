package com.parkingapp.parkingservice.repository;

import com.parkingapp.parkingservice.model.ParkingZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaParkingZonesRepository implements ParkingZonesRepository {

    @Autowired
    private JpaParkingZonesInterfaceRepository jpaParkingZonesInterfaceRepository;

    @Override
    public List<ParkingZone> getParkingZonesById(UUID cityId) {
        return jpaParkingZonesInterfaceRepository.findById(cityId).stream()
                .map(parkingZonesEntity -> new ParkingZone(parkingZonesEntity.getId(), parkingZonesEntity.getName()))
                .collect(Collectors.toList());
    }

}