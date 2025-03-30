package com.swiftpay.swiftpay_scheduler.entity.transfer;

import com.swiftpay.swiftpay_scheduler.entity.user.bank_account.BankAccount;
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
    @JoinColumn(name = "sender_account_id")
    private BankAccount senderAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_account_id")
    private BankAccount receiverAccount;

    private BigDecimal amount;

    private LocalDate scheduleDate;

    @OneToOne(mappedBy = "transfer")
    private TransferFees appliedTax;

    private BigDecimal amountIncludingFees;

    @Enumerated(EnumType.STRING)
    private TransferStatus status;
}
