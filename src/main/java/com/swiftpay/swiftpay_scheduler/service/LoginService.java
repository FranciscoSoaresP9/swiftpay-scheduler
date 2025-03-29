package com.swiftpay.swiftpay_scheduler.service;

import com.swiftpay.swiftpay_scheduler.dto.LoginRequestDTO;
import com.swiftpay.swiftpay_scheduler.dto.LoginResponseDTO;
import com.swiftpay.swiftpay_scheduler.dto.RefreshTokenRequestDTO;

public interface LoginService {

    LoginResponseDTO login(LoginRequestDTO write);

    LoginResponseDTO login(RefreshTokenRequestDTO write);

}