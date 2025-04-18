package com.swiftpay.swiftpay_scheduler.dto.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateTransferDTO(BigDecimal amount,
                                @JsonFormat(pattern = "yyyy-MM-dd") LocalDate scheduleDate) {
}