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

    private Integer minDays;

    private Integer maxDays;

    private BigDecimal fixedFee;

    private BigDecimal taxPercentage;

}
