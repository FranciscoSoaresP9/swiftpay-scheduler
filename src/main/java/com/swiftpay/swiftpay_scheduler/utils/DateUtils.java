package com.swiftpay.swiftpay_scheduler.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class DateUtils {
    public long calculateDaysBetween(LocalDate targetDate) {
        LocalDate currentDate = LocalDate.now();
        return ChronoUnit.DAYS.between(currentDate, targetDate);
    }
}
