package com.swiftpay.swiftpay_scheduler.entity.fee;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@With
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class FeeRange {

    private int minDays;

    private int maxDays;

    private BigDecimal fixedFee;

    private BigDecimal taxPercentage;

}
