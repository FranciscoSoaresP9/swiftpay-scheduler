package com.swiftpay.swiftpay_scheduler.entity.transfer;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@With
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class TransferFees {

    @Id
    @OneToOne
    @JoinColumn(name = "transfer_id")
    private Transfer transfer;

    private BigDecimal fixedFee;

    private BigDecimal taxPercentage;

}