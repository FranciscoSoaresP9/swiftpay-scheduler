package com.swiftpay.swiftpay_scheduler.service.bank_account;

import com.swiftpay.swiftpay_scheduler.entity.user.bank_account.BankAccount;
import com.swiftpay.swiftpay_scheduler.exception.BankAccountIbanNotFoundException;
import com.swiftpay.swiftpay_scheduler.exception.BankAccountIdNotFoundException;
import com.swiftpay.swiftpay_scheduler.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository repository;

    public BankAccount findBankAccountByIban(String iban) {
        return repository.findByIban(iban)
                .orElseThrow(() -> new BankAccountIbanNotFoundException("Bank account iban not found"));
    }

    public BankAccount findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BankAccountIdNotFoundException("Bank account id not found"));
    }

    public void debitAccountBalance(Long id, BigDecimal amount) {
        var account = findById(id);
        account.setBalance(account.getBalance().subtract(amount));
        repository.save(account);
    }

}
