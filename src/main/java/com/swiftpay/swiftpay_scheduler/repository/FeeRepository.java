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
            "INNER JOIN fe.feeRanges fr " +
            "WHERE :amount BETWEEN fe.minAmount AND fe.maxAmount " +
            "AND (fr.minDays <= :numberOfDays " +
            "AND (fr.maxDays >= :numberOfDays OR fr.maxDays IS NULL))")
    Optional<Fee> findFeeByAmountAndDateRange(BigDecimal amount, long numberOfDays);

}
