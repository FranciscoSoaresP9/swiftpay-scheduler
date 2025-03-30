package com.swiftpay.swiftpay_scheduler.exception;

public class TransferModificationNotAllowedException extends RuntimeException {
    public TransferModificationNotAllowedException(String message) {
        super(message);
    }
}
