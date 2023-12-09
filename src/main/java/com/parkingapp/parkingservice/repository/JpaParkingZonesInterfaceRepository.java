package com.parkingapp.parkingservice.repository;

import com.parkingapp.parkingservice.entity.ParkingZonesEntity;
import com.parkingapp.parkingservice.model.ParkingZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
interface JpaParkingZonesInterfaceRepository extends JpaRepository<ParkingZonesEntity, UUID> {
   List<ParkingZone> findByCityId(UUID citiId);
}