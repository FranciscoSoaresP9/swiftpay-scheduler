package com.swiftpay.swiftpay_scheduler.service.fee;


import com.swiftpay.swiftpay_scheduler.entity.fee.Fee;
import com.swiftpay.swiftpay_scheduler.entity.fee.FeeRange;
import com.swiftpay.swiftpay_scheduler.repository.FeeRepository;
import com.swiftpay.swiftpay_scheduler.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeeServiceImpl implements FeeService {

    private final FeeRepository feeRepository;
    private final DateUtils dateUtils;

    @Override
    public Optional<FeeRange> getFeeRange(BigDecimal amount, LocalDate date) {
        var days = dateUtils.calculateDaysBetween(date);
        var fee = getFeeByAmountAndDateRange(amount, date);
        return fee.flatMap(value -> value
                .getFeeRanges()
                .stream()
                .filter(el -> el.getMinDays() <= days && (el.getMaxDays() == null || el.getMaxDays() >= days))
                .findAny());
    }

    public Optional<Fee> getFeeByAmountAndDateRange(BigDecimal amount, LocalDate date) {
        var days = dateUtils.calculateDaysBetween(date);
        return feeRepository.findFeeByAmountAndDateRange(amount, days);
    }
}
