package com.swiftpay.swiftpay_scheduler.exception;

public class TransferDeletionNotAllowedException extends RuntimeException {
    public TransferDeletionNotAllowedException(String message) {
        super(message);
    }
}
