package com.swiftpay.swiftpay_scheduler.service.schedule_transfer;

import com.swiftpay.swiftpay_scheduler.entity.transfer.Transfer;
import com.swiftpay.swiftpay_scheduler.exception.InvalidBalanceException;
import com.swiftpay.swiftpay_scheduler.exception.InvalidTransferAmountException;
import com.swiftpay.swiftpay_scheduler.exception.InvalidTransferDateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ScheduleTransferValidationService {

    public void validate(Transfer transfer, BigDecimal currentBalance) {
        validateBalance(transfer.getAmountIncludingFees(), currentBalance);
        validateDate(transfer.getScheduleDate());
    }

    private void validateBalance(BigDecimal amount, BigDecimal currentBalance) {
        if (amount == null || amount.equals(BigDecimal.ZERO)) {
            throw new InvalidTransferAmountException("Transfer cannot be processed: amount must be greater than zero");
        }

        if (currentBalance.compareTo(amount) < 0) {
            throw new InvalidBalanceException("Transfer cannot be processed: balance must remain positive.");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new InvalidTransferDateException("Transfer cannot be processed: date cannot be in the past.");
        }
    }

}
