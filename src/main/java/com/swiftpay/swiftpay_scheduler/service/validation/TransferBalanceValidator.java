package com.swiftpay.swiftpay_scheduler.service.validation;

import com.swiftpay.swiftpay_scheduler.exception.InvalidBalanceException;
import com.swiftpay.swiftpay_scheduler.exception.InvalidTransferAmountException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransferBalanceValidator implements Validator<TransferBalanceValidationParams> {
    public void validate(TransferBalanceValidationParams params) {
        if (params.amount() == null || params.amount().equals(BigDecimal.ZERO)) {
            throw new InvalidTransferAmountException("Transfer cannot be processed: amount must be greater than zero");
        }

        if (params.currentBalance().compareTo(params.amount()) < 0) {
            throw new InvalidBalanceException("Transfer cannot be processed: balance must remain positive.");
        }
    }
}
