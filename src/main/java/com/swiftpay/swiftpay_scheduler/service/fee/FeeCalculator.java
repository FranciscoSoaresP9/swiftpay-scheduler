package com.swiftpay.swiftpay_scheduler.service.fee;


import java.math.BigDecimal;
import java.time.LocalDate;

public interface FeeCalculator {
    BigDecimal calculateTotalAmountWithFee(BigDecimal amount, LocalDate date);
}
