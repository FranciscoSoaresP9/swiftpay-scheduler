package com.swiftpay.swiftpay_scheduler.dto.transfer;

import com.swiftpay.swiftpay_scheduler.entity.transfer.TransferStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransferSmallDTO(String receiverName,
                               BigDecimal amount,
                               LocalDate scheduleDate,
                               BigDecimal amountIncludingFees,
                               TransferStatus status) {
}
