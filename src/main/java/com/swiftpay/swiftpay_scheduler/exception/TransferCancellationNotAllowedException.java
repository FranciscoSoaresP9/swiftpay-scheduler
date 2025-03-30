package com.swiftpay.swiftpay_scheduler.exception;

public class TransferCancellationNotAllowedException extends RuntimeException {
    public TransferCancellationNotAllowedException(String message) {
        super(message);
    }
}
