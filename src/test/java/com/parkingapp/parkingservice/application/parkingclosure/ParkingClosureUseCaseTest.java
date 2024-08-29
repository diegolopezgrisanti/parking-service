package com.parkingapp.parkingservice.application.parkingclosure;

import com.parkingapp.parkingservice.domain.atomicity.AtomicOperation;
import com.parkingapp.parkingservice.domain.common.Amount;
import com.parkingapp.parkingservice.domain.parkingclosure.ParkingClosure;
import com.parkingapp.parkingservice.domain.parkingclosure.ParkingClosureRepository;
import com.parkingapp.parkingservice.domain.payment.Failure;
import com.parkingapp.parkingservice.domain.payment.ParkingPaymentService;
import com.parkingapp.parkingservice.domain.payment.Successful;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;


import javax.money.Monetary;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ParkingClosureUseCaseTest {
    private final ParkingClosureRepository parkingClosureRepository = mock(ParkingClosureRepository.class);
    private final ParkingPaymentService parkingPaymentService = mock(ParkingPaymentService.class);
    private final AtomicOperation atomicOperation = mock(AtomicOperation.class);
    private final Clock clock = mock(Clock.class);
    private final ParkingClosureUseCase parkingClosureUseCase = new ParkingClosureUseCase(
            parkingClosureRepository,
            parkingPaymentService,
            atomicOperation,
            clock
    );

    private final Instant now = Instant.now();
    private final int batchSize = 1;
    private final Instant parkingStartDate = Instant.now();
    private final Instant parkingEndDate = parkingStartDate.plus(45, ChronoUnit.MINUTES);
    private final int parkingZoneFeePerMinute = 10;
    private final int expectedFeeAmount = 450;
    private final UUID parkingId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final UUID paymentMethodId = UUID.randomUUID();
    private final ParkingClosure parkingClosure = new ParkingClosure(
            new Amount(Monetary.getCurrency("EUR"), parkingZoneFeePerMinute),
            parkingStartDate,
            parkingEndDate,
            parkingId,
            userId,
            paymentMethodId

    );

    @BeforeEach
    void setUp() {
        ArgumentCaptor<AtomicOperation.TransactionCallback> captor = ArgumentCaptor.forClass(AtomicOperation.TransactionCallback.class);

        doAnswer(invocation -> {
            invocation.<AtomicOperation.TransactionCallback>getArgument(0).doInTransaction();
            return  null;
        }).when(atomicOperation).invoke(captor.capture());
    }

    @Test
    void shouldProcessPendingParking() {
        // Given
        List<ParkingClosure> parkingList = List.of(parkingClosure);
        when(clock.instant()).thenReturn(now);
        when(parkingClosureRepository.getParkingsWithPendingPayment(batchSize, now))
                .thenReturn(parkingList);
        when(parkingPaymentService.chargeFee(parkingClosure, expectedFeeAmount)).thenReturn(new Successful());

        // When
        parkingClosureUseCase.execute(batchSize);

        // Then
        verify(atomicOperation).invoke((AtomicOperation.TransactionCallback) any());
        verify(parkingClosureRepository).getParkingsWithPendingPayment(batchSize, now);
        verify(parkingPaymentService).chargeFee(parkingClosure, expectedFeeAmount);
        verify(parkingClosureRepository).markAsProcessed(parkingClosure.getParkingId(), now);
    }

    @Test
    void shouldMarkPendingParkingAsFailedWhenPaymentFails() {
        // Given
        List<ParkingClosure> parkingList = List.of(parkingClosure);
        when(clock.instant()).thenReturn(now);
        when(parkingClosureRepository.getParkingsWithPendingPayment(batchSize, now))
                .thenReturn(parkingList);
        when(parkingPaymentService.chargeFee(parkingClosure, expectedFeeAmount)).thenReturn(new Failure());

        // When
        parkingClosureUseCase.execute(batchSize);

        // Then
        verify(atomicOperation).invoke((AtomicOperation.TransactionCallback) any());
        verify(parkingClosureRepository).getParkingsWithPendingPayment(batchSize, now);
        verify(parkingPaymentService).chargeFee(parkingClosure, expectedFeeAmount);
        verify(parkingClosureRepository).markAsFailed(parkingClosure.getParkingId(), now);
    }

}