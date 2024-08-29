package com.parkingapp.parkingservice.infrastructure.atomicity;

import com.parkingapp.parkingservice.domain.atomicity.AtomicOperation;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.IntegrationTest;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.WithPostgreSql;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@IntegrationTest
@WithPostgreSql
class SpringTransactionalAtomicOperationIntegrationTest {

    @Autowired
    AtomicOperation atomicOperation;

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void shouldCreateANewTransaction() {
        assertThatExceptionOfType(NoTransactionException.class)
                .isThrownBy(TransactionAspectSupport::currentTransactionStatus);

        boolean status = atomicOperation.invoke(
                () -> TransactionAspectSupport.currentTransactionStatus().isNewTransaction()
        );

        assertThat(status).isTrue();
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void shouldCreateANewTransactionWithNoReturn() {
        assertThatExceptionOfType(NoTransactionException.class)
                .isThrownBy(TransactionAspectSupport::currentTransactionStatus);

        atomicOperation.invoke(() -> {
            boolean status = TransactionAspectSupport.currentTransactionStatus().isNewTransaction();
            assertThat(status).isTrue();
        });
    }



}