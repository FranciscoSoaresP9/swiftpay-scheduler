package com.swiftpay.swiftpay_scheduler.controller;

import com.swiftpay.swiftpay_scheduler.dto.registration.CognitoConfirmRegistrationRequestDto;
import com.swiftpay.swiftpay_scheduler.service.registration.ConfirmRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.swiftpay.swiftpay_scheduler.constants.ApiPaths.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = API_PATH + AUTH)
public class ConfirmRegistrationController {

    private final ConfirmRegistrationService<CognitoConfirmRegistrationRequestDto> service;

    @PostMapping(CONFIRM_REGISTRATION)
    public void confirm(@RequestBody CognitoConfirmRegistrationRequestDto write) {
        service.confirm(write);
    }

}
