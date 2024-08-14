package com.parkingapp.parkingservice.application.parkingclosure;

import com.parkingapp.parkingservice.domain.parkingclosure.ParkingClosure;
import com.parkingapp.parkingservice.domain.parkingclosure.ParkingClosureRepository;
import com.parkingapp.parkingservice.domain.payment.ParkingPaymentResponse;
import com.parkingapp.parkingservice.domain.payment.ParkingPaymentService;
import com.parkingapp.parkingservice.domain.payment.Failure;
import com.parkingapp.parkingservice.domain.payment.Successful;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class ParkingClosureUseCase {
    private final ParkingClosureRepository parkingClosureRepository;
    private final ParkingPaymentService parkingPaymentService;
    private final Clock clock;

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
                case Successful s -> parkingClosureRepository.markAsProcessed(parkingClosure.getParkingId(), now);
                case Failure f -> parkingClosureRepository.markAsFailed(parkingClosure.getParkingId(), now);
            }
        }
    }

    private int calculateFeeAmount(ParkingClosure parkingClosure) {
        int feePerMinute = parkingClosure.getFeePerMinute().getCents();
        long minutesParked = Duration.between(parkingClosure.getStartDate(), parkingClosure.getEndDate()).toMinutes();
        return (int) (minutesParked * feePerMinute);
    }

}
