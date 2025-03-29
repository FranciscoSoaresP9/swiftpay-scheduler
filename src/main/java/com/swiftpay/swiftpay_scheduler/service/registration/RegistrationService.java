package com.swiftpay.swiftpay_scheduler.service.registration;

import com.swiftpay.swiftpay_scheduler.dto.registration.RegistrationRequestDTO;
import com.swiftpay.swiftpay_scheduler.dto.registration.RegistrationResponseDTO;

public interface RegistrationService {
    RegistrationResponseDTO register(RegistrationRequestDTO write);
}