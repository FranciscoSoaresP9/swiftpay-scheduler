package com.swiftpay.swiftpay_scheduler.entity.transfer;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@With
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class TransferFee {

    private BigDecimal fixedFee;

    private BigDecimal taxPercentage;

}