package com.swiftpay.swiftpay_scheduler.repository;

import com.swiftpay.swiftpay_scheduler.entity.user.bank_account.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByIban(String iban);

    Optional<BankAccount> findByUserId(Long userId);
}
