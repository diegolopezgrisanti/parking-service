package com.parkingapp.parkingservice.application.checkparkingstatus;

import com.parkingapp.parkingservice.domain.parking.Parking;
import com.parkingapp.parkingservice.domain.parking.ParkingStatus;
import com.parkingapp.parkingservice.domain.parking.ParkingStatusCheck;
import com.parkingapp.parkingservice.domain.parking.ParkingRepository;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CheckParkingStatusUseCase {

    private final ParkingRepository parkingRepository;

    public CheckParkingStatusUseCase(
            ParkingRepository parkingRepository
    ) {
        this.parkingRepository = parkingRepository;
    }

    public ParkingStatusCheck execute(String plate, UUID parkingZoneId) {
        List<Parking> parkings = this.parkingRepository.getTodayParkingsByPlateAndZone(plate, parkingZoneId);
        Optional<Parking> lastParking = parkings.stream().max(Comparator.comparing(Parking::getEndDate));
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
        return lastParking.get().getEndDate().isAfter(Instant.now()) ? ParkingStatus.ACTIVE : ParkingStatus.EXPIRED;
    }
}
