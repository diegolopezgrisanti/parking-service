package com.parkingapp.parkingservice.service;

import com.parkingapp.parkingservice.exceptions.ParkingZoneNotFoundException;
import com.parkingapp.parkingservice.model.Parking;
import com.parkingapp.parkingservice.model.ParkingStatus;
import com.parkingapp.parkingservice.model.ParkingStatusCheck;
import com.parkingapp.parkingservice.repository.ParkingRepository;
import com.parkingapp.parkingservice.repository.ParkingZonesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final ParkingRepository parkingRepository;

    private final ParkingZonesRepository parkingZonesRepository;

    @Autowired
    public ParkingServiceImpl(
            ParkingRepository parkingRepository,
            ParkingZonesRepository parkingZonesRepository
    ) {
        this.parkingRepository = parkingRepository;
        this.parkingZonesRepository = parkingZonesRepository;
    }

    public Parking createParking(Parking parking) {
        if (this.isParkingZoneNonExistent(parking.getParkingZoneId())) {
            throw new ParkingZoneNotFoundException(parking.getParkingZoneId());
        }

        parkingRepository.saveParking(parking);
        return parking;
    }

    @Override
    public ParkingStatusCheck getParkingStatusCheck(String plate, UUID parkingZoneId) {
        List<Parking> parkings = this.parkingRepository.getTodayParkingsByPlateAndZone(plate, parkingZoneId);
        Optional<Parking> lastParking = parkings.stream().max(Comparator.comparing(Parking::getExpiration));
        ParkingStatus status = this.resolveParkingStatus(lastParking);
        return new ParkingStatusCheck(
                status,
                lastParking.orElse(null)
        );
    }

    private ParkingStatus resolveParkingStatus(Optional<Parking> lastParking) {
        if (lastParking.isEmpty()) {
            return ParkingStatus.NOT_FOUND;
        }
        return lastParking.get().getExpiration().isAfter(Instant.now()) ? ParkingStatus.ACTIVE : ParkingStatus.EXPIRED;
    }

    private boolean isParkingZoneNonExistent(UUID parkingZoneId) {
        return !this.parkingZonesRepository.isParkingZoneIdValid(parkingZoneId);
    }
}
