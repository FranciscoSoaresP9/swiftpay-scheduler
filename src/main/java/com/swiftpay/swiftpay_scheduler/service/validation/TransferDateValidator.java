package com.swiftpay.swiftpay_scheduler.service.validation;

import com.swiftpay.swiftpay_scheduler.exception.InvalidTransferDateException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TransferDateValidator implements Validator<LocalDate> {
    public void validate(LocalDate date) {
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new InvalidTransferDateException("Transfer cannot be processed: date cannot be in the past.");
        }
    }
}
