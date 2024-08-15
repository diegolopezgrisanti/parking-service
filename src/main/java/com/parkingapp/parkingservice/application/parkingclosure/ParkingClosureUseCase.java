package com.parkingapp.parkingservice.application.parkingclosure;

import com.parkingapp.parkingservice.domain.parkingclosure.ParkingClosure;
import com.parkingapp.parkingservice.domain.parkingclosure.ParkingClosureRepository;
import com.parkingapp.parkingservice.domain.payment.ParkingPaymentResponse;
import com.parkingapp.parkingservice.domain.payment.ParkingPaymentService;
import com.parkingapp.parkingservice.domain.payment.Failure;
import com.parkingapp.parkingservice.domain.payment.Successful;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ParkingClosureUseCase {
    private final ParkingClosureRepository parkingClosureRepository;
    private final ParkingPaymentService parkingPaymentService;
    private final Clock clock;
    private final Logger log = LogManager.getLogger(getClass());

    public ParkingClosureUseCase(
            ParkingClosureRepository parkingClosureRepository,
            ParkingPaymentService parkingPaymentService,
            Clock clock
    ) {
        this.parkingClosureRepository = parkingClosureRepository;
        this.parkingPaymentService = parkingPaymentService;
        this.clock = clock;
    }

    public void execute(int batchSize) {
        Instant now = clock.instant();
        List<ParkingClosure> pendingParkings = parkingClosureRepository.getParkingsWithPendingPayment(batchSize, now);

        for (ParkingClosure parkingClosure : pendingParkings) {
            int feeAmount = calculateFeeAmount(parkingClosure);
            ParkingPaymentResponse response = parkingPaymentService.chargeFee(parkingClosure, feeAmount);

            switch (response) {
                case Successful s -> markAsProcessed(parkingClosure.getParkingId(), now);
                case Failure f -> markAsFailed(parkingClosure.getParkingId(), now);
            }
        }
    }

    private void markAsFailed(UUID parkingId, Instant now) {
        parkingClosureRepository.markAsFailed(parkingId, now);
        log.error(String.format("Failed to process payment for parking with ID: %s", parkingId));
    }

    private void markAsProcessed(UUID parkingId, Instant now) {
        parkingClosureRepository.markAsProcessed(parkingId, now);
        log.info((String.format("Payment processed for parking with ID: %s", parkingId)));
    }

    private int calculateFeeAmount(ParkingClosure parkingClosure) {
        int feePerMinute = parkingClosure.getFeePerMinute().getCents();
        long minutesParked = Duration.between(parkingClosure.getStartDate(), parkingClosure.getEndDate()).toMinutes();
        return (int) (minutesParked * feePerMinute);
    }
}
