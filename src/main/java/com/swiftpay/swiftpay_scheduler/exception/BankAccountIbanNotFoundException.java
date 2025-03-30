package com.swiftpay.swiftpay_scheduler.exception;

public class BankAccountIbanNotFoundException extends RuntimeException {
    public BankAccountIbanNotFoundException(String message) {
        super(message);
    }

}
