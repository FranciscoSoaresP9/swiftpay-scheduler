package com.swiftpay.swiftpay_scheduler.service.validation;

import java.math.BigDecimal;

public record TransferBalanceValidationParams(BigDecimal amount, BigDecimal currentBalance) {
}
