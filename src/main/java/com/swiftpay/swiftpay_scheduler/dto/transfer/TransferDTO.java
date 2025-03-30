package com.swiftpay.swiftpay_scheduler.dto.transfer;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransferDTO(String receiverIban,
                          BigDecimal amount,
                          LocalDate scheduleDate,
                          BigDecimal taxPercentage,
                          BigDecimal fixedFee,
                          BigDecimal amountIncludingFees) {
}
