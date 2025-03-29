package com.swiftpay.swiftpay_scheduler.service.login;

import com.swiftpay.swiftpay_scheduler.dto.login.LoginRequestDTO;
import com.swiftpay.swiftpay_scheduler.dto.login.LoginResponseDTO;
import com.swiftpay.swiftpay_scheduler.dto.login.RefreshTokenRequestDTO;

public interface LoginService {

    LoginResponseDTO login(LoginRequestDTO write);

    LoginResponseDTO login(RefreshTokenRequestDTO write);

}