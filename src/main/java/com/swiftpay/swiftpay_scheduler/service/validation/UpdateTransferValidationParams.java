package com.swiftpay.swiftpay_scheduler.service.validation;

import com.swiftpay.swiftpay_scheduler.entity.transfer.Transfer;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateTransferValidationParams(Transfer transfer,
                                             BigDecimal amount,
                                             BigDecimal currentBalance,
                                             LocalDate date) {
}
