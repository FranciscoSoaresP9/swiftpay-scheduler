package com.swiftpay.swiftpay_scheduler.entity.user.bank_account;

import com.swiftpay.swiftpay_scheduler.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@With
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_account_seq")
    @SequenceGenerator(name = "bank_account_seq", sequenceName = "bank_account_sequence", allocationSize = 1)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String iban;

    private BigDecimal balance;

    private LocalDateTime createdAt = LocalDateTime.now();

}
