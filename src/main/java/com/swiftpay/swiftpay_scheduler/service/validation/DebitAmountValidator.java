package com.swiftpay.swiftpay_scheduler.service.validation;

import com.swiftpay.swiftpay_scheduler.exception.InvalidTransferAmountException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DebitAmountValidator implements Validator<BigDecimal> {
    public void validate(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ONE) < 0) {
            throw new InvalidTransferAmountException("Debit cannot be processed: amount must be greater than 1");
        }
    }
}
