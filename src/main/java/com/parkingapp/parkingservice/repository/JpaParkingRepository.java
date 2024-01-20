package com.parkingapp.parkingservice.repository;

import com.parkingapp.parkingservice.entity.ParkingEntity;
import com.parkingapp.parkingservice.model.Parking;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;

public class JpaParkingRepository implements ParkingRepository {

    @Autowired
    private JpaParkingInterfaceRepository jpaParkingInterfaceRepository;

    @Override
    public void saveParking(Parking parking) {

        ParkingEntity parkingEntity = new ParkingEntity();
        parkingEntity.setId(UUID.randomUUID());
        parkingEntity.setPlate(parking.getPlate());
        parkingEntity.setCity_id(parking.getCity_id());
        parkingEntity.setParking_zone_id(parking.getParking_zone_id());
        parkingEntity.setExpiration(parking.getExpiration());
        parkingEntity.setEmail(parking.getEmail());

        jpaParkingInterfaceRepository.save(parkingEntity);
    }
}
