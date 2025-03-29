package com.swiftpay.swiftpay_scheduler.dto.registration;

public record CognitoRegistrationRequestDTO(
        String externalId,
        String name,
        String username,
        String password
) {
}
