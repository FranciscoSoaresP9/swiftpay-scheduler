package com.swiftpay.swiftpay_scheduler.entity.transfer;

import com.swiftpay.swiftpay_scheduler.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@With
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transfer_seq")
    @SequenceGenerator(name = "transfer_seq", sequenceName = "transfer_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private BigDecimal amount;

    private LocalDate scheduleDate;

    @OneToOne(mappedBy = "transfer")
    private TransferFees appliedTax;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private TransferStatus status;
}
