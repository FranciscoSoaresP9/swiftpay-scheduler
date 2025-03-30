package com.swiftpay.swiftpay_scheduler.service.schedule_transfer;

import com.swiftpay.swiftpay_scheduler.dto.transfer.WriteTransferDTO;
import com.swiftpay.swiftpay_scheduler.exception.InvalidTransferAmountException;
import com.swiftpay.swiftpay_scheduler.exception.InvalidTransferDateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ScheduleTransferValidationService {

    public void validate(WriteTransferDTO write) {
        validateBalance(write.amount());
        validateDate(write.scheduleDate());
    }

    private void validateBalance(BigDecimal amount) {
        if (amount.equals(BigDecimal.ZERO)) {
            throw new InvalidTransferAmountException("Amount must be greater than zero");
        }
    }

    private void validateDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new InvalidTransferDateException("Transfer date cannot be in the past.");
        }
    }

}
