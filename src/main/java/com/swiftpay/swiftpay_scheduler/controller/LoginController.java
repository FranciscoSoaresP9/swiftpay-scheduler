package com.swiftpay.swiftpay_scheduler.controller;

import com.swiftpay.swiftpay_scheduler.dto.LoginRequestDTO;
import com.swiftpay.swiftpay_scheduler.dto.LoginResponseDTO;
import com.swiftpay.swiftpay_scheduler.dto.RefreshTokenRequestDTO;
import com.swiftpay.swiftpay_scheduler.service.LoginService;
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

    private final LoginService loginService;

    @PostMapping
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginDto) {
        return loginService.login(loginDto);
    }

    @PostMapping(REFRESH_TOKEN)
    public LoginResponseDTO login(@RequestBody RefreshTokenRequestDTO loginDto) {
        return loginService.login(loginDto);
    }

}
