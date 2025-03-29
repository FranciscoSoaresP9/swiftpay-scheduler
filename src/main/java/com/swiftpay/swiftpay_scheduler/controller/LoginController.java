package com.swiftpay.swiftpay_scheduler.controller;

import com.swiftpay.swiftpay_scheduler.dto.login.LoginRequestDTO;
import com.swiftpay.swiftpay_scheduler.dto.login.LoginResponseDTO;
import com.swiftpay.swiftpay_scheduler.dto.login.RefreshTokenRequestDTO;
import com.swiftpay.swiftpay_scheduler.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.swiftpay.swiftpay_scheduler.constants.ApiPaths.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = API_PATH + AUTH)
public class LoginController {

    private final LoginService service;

    @PostMapping
    public LoginResponseDTO login(@RequestBody LoginRequestDTO write) {
        return service.login(write);
    }

    @PostMapping(REFRESH_TOKEN)
    public LoginResponseDTO login(@RequestBody RefreshTokenRequestDTO write) {
        return service.login(write);
    }

}
