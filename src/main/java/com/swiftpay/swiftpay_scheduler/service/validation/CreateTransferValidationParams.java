package com.swiftpay.swiftpay_scheduler.service.validation;

import com.swiftpay.swiftpay_scheduler.entity.transfer.Transfer;

import java.math.BigDecimal;

public record CreateTransferValidationParams(Transfer transfer, BigDecimal currentBalance) {
}
