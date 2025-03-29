package com.swiftpay.swiftpay_scheduler.entity.user;

import com.swiftpay.swiftpay_scheduler.entity.user.bank_account.BankAccount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@With
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private BankAccount bankAccount;

    private String name;

    private String email;

    private String externalId;

    private LocalDateTime createdAt = LocalDateTime.now();

}
