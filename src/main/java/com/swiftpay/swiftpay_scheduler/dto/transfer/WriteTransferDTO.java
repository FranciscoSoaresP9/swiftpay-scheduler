package com.swiftpay.swiftpay_scheduler.dto.transfer;

import java.math.BigDecimal;
import java.time.LocalDate;

public record WriteTransferDTO(String receiverIban, BigDecimal amount, LocalDate scheduleDate) {
}
