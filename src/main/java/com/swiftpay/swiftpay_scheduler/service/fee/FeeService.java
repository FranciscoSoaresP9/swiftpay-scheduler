package com.swiftpay.swiftpay_scheduler.service.fee;

import com.swiftpay.swiftpay_scheduler.entity.fee.FeeRange;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface FeeService {
    Optional<FeeRange> getFeeRange(BigDecimal amount, LocalDate date);
}
