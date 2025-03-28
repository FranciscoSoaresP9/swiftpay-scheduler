package com.swiftpay.swiftpay_scheduler.entity.fee;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@With
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "fees")
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fee_seq")
    @SequenceGenerator(name = "fee_seq", sequenceName = "fee_sequence", allocationSize = 1)
    private Long id;

    private String name;

    private BigDecimal minAmount;

    private BigDecimal maxAmount;

    @ElementCollection
    @CollectionTable(name = "fee_ranges", joinColumns = @JoinColumn(name = "fee_id"))
    private Set<FeeRange> feeRanges;

}
