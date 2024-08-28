package com.parkingapp.parkingservice.domain.atomicity;

public interface AtomicOperation {
    <T> T invoke(TransactionCallbackWithReturn<T> callback);
    void invoke(TransactionCallback callback);

    @FunctionalInterface
    interface TransactionCallbackWithReturn<T> {
        T doInTransaction();
    }

    @FunctionalInterface
    interface TransactionCallback {
        void doInTransaction();
    }
}
