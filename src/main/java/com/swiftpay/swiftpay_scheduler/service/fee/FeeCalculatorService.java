package com.swiftpay.swiftpay_scheduler.service.fee;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FeeCalculatorService implements FeeCalculator {

    private final FeeService feeService;

    @Override
    public BigDecimal calculateTotalAmountWithFee(BigDecimal amount, LocalDate date) {
        var tax = calculateFeeForAmountOnDate(amount, date);
        return amount.add(tax).setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calculateFeeForAmountOnDate(BigDecimal amount, LocalDate date) {
        var feeRange = feeService.getFeeRange(amount, date);
        return feeRange.map(el -> {
            var taxPercentage = feeRange.get().getTaxPercentage();
            var fixedFee = feeRange.get().getFixedFee();
            return (taxPercentage
                    .divide(BigDecimal.valueOf(100))
                    .multiply(amount))
                    .add(fixedFee);
        }).orElse(BigDecimal.ZERO);
    }
}
