package com.swiftpay.swiftpay_scheduler.dto.bank_account;

import java.math.BigDecimal;

public record BankAccountDTO(Long id,
                             String iban,
                             BigDecimal balance) {
}
