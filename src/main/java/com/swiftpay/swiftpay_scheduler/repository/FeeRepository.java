package com.swiftpay.swiftpay_scheduler.repository;

import com.swiftpay.swiftpay_scheduler.entity.fee.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Long> {

    @Query("SELECT fe FROM Fee fe " +
            "WHERE :amount BETWEEN fe.minAmount AND fe.maxAmount " +
            "AND ( (fe.feeRanges.minDays <= :numberOfDays AND fe.feeRanges.maxDays >= :numberOfDays) " +
            "OR (fe.feeRanges.minDays <= :numberOfDays))")
    Optional<Fee> findFeeByAmountAndDateRange(BigDecimal amount, long numberOfDays);
}
