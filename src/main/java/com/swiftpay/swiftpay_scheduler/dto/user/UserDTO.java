package com.swiftpay.swiftpay_scheduler.dto.user;

public record UserDTO(
        long id,
        String name,
        String email,
        String iban
) {
}
