package com.swiftpay.swiftpay_scheduler.dto.registration;

import java.math.BigDecimal;

public record RegistrationRequestDTO(
        String name,
        String email,
        String password,
        BigDecimal balance
) {
}
