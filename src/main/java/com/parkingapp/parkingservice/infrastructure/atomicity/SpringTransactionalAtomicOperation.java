package com.parkingapp.parkingservice.infrastructure.atomicity;

import com.parkingapp.parkingservice.domain.atomicity.AtomicOperation;
import org.springframework.transaction.annotation.Transactional;

public class SpringTransactionalAtomicOperation implements AtomicOperation {

    @Override
    @Transactional
    public <T> T invoke(TransactionCallbackWithReturn<T> callback) {
        return callback.doInTransaction();
    }

    @Override
    @Transactional
    public void invoke(TransactionCallback callback) {
        callback.doInTransaction();
    }
}
