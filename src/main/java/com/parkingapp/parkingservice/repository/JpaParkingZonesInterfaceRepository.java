package com.parkingapp.parkingservice.repository;

import com.parkingapp.parkingservice.entity.ParkingZonesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface JpaParkingZonesInterfaceRepository extends JpaRepository<ParkingZonesEntity, UUID> {
}