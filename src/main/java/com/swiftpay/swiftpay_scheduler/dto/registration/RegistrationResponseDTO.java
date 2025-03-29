package com.swiftpay.swiftpay_scheduler.dto.registration;

import java.math.BigDecimal;

public record RegistrationResponseDTO(
        long id,
        String name,
        String iban,
        long accountId,
        BigDecimal balance
) {
}
