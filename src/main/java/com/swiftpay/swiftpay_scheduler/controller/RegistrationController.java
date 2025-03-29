package com.swiftpay.swiftpay_scheduler.controller;

import com.swiftpay.swiftpay_scheduler.dto.registration.RegistrationRequestDTO;
import com.swiftpay.swiftpay_scheduler.dto.registration.RegistrationResponseDTO;
import com.swiftpay.swiftpay_scheduler.service.registration.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.swiftpay.swiftpay_scheduler.constants.ApiPaths.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = API_PATH + AUTH)
public class RegistrationController {

    private final RegistrationService service;

    @PostMapping(REGISTER)
    public RegistrationResponseDTO register(@RequestBody RegistrationRequestDTO write) {
        return service.register(write);
    }

}
