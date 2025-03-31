package com.swiftpay.swiftpay_scheduler.controller;

import com.swiftpay.swiftpay_scheduler.dto.bank_account.BankAccountDTO;
import com.swiftpay.swiftpay_scheduler.dto.bank_account.DepositDTO;
import com.swiftpay.swiftpay_scheduler.service.bank_account.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.swiftpay.swiftpay_scheduler.constants.ApiPaths.API_PATH;
import static com.swiftpay.swiftpay_scheduler.constants.ApiPaths.BANK_ACCOUNT;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = API_PATH + BANK_ACCOUNT)
public class BankAccountController {

    private final BankAccountService service;

    @GetMapping
    public BankAccountDTO getCurrentUserBankAccount() {
        return service.getCurrentDTO();
    }

    @PatchMapping()
    public void deposit(@RequestBody DepositDTO write) {
        service.deposit(write.amount());
    }
}
