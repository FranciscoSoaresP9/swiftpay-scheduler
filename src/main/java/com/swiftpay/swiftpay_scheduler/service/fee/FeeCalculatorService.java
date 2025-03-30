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
        return amount.add(calculateFeeForAmountOnDate(amount, date));
    }

    private BigDecimal calculateFeeForAmountOnDate(BigDecimal amount, LocalDate date) {
        var feeRange = feeService.getFeeRange(amount, date);
        return feeRange.map(el -> {
            var taxPercentage = feeRange.get().getTaxPercentage();
            var fixedFee = feeRange.get().getFixedFee();
            return taxPercentage
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                    .multiply(amount)
                    .add(fixedFee);
        }).orElse(BigDecimal.ZERO);
    }
}
