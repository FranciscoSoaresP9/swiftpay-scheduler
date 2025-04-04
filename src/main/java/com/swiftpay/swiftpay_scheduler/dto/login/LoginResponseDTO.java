package com.swiftpay.swiftpay_scheduler.dto.login;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LoginResponseDTO(
        String accessToken,
        String refreshToken,
        Integer expiresIn
) {
}