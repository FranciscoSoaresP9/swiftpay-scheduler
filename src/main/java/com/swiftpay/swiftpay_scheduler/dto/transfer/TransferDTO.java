package com.swiftpay.swiftpay_scheduler.dto.transfer;

import com.swiftpay.swiftpay_scheduler.entity.transfer.TransferStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransferDTO(Long id,
                          String receiverName,
                          String receiverIban,
                          BigDecimal amount,
                          LocalDate scheduleDate,
                          BigDecimal taxPercentage,
                          BigDecimal fixedFee,
                          BigDecimal amountIncludingFees,
                          TransferStatus status) {
}
