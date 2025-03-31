package com.swiftpay.swiftpay_scheduler.service.login;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.swiftpay.swiftpay_scheduler.dto.login.LoginRequestDTO;
import com.swiftpay.swiftpay_scheduler.dto.login.LoginResponseDTO;
import com.swiftpay.swiftpay_scheduler.dto.login.RefreshTokenRequestDTO;
import com.swiftpay.swiftpay_scheduler.utils.CognitoHmacSecretHashGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CognitoLoginService implements LoginService {

    private final CognitoHmacSecretHashGenerator secretHashGenerator;
    private final AWSCognitoIdentityProvider cognitoIdentityProvider;

    @Value("${aws.cognito.userPoolId}")
    private String userPoolId;

    @Value("${aws.cognito.clientId}")
    private String clientId;

    @Value("${aws.cognito.clientSecret}")
    private String clientSecret;

    @Override
    public LoginResponseDTO login(RefreshTokenRequestDTO refreshTokenRequestDto) {
        log.info("Login using refresh token");
        var authParams = createAuthParamsForRefreshToken(refreshTokenRequestDto.token());
        var authRequest = createAuthRequest(authParams, AuthFlowType.REFRESH_TOKEN_AUTH);
        var authResult = initiateAuthRequest(authRequest);
        log.info("Login successful using refresh token");
        return buildLoginResponse(authResult, refreshTokenRequestDto.token());
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDto) {
        log.info("Login attempt for user: {}", loginRequestDto.username());
        var authParams = createAuthParamsForLogin(loginRequestDto);
        var authRequest = createAuthRequest(authParams, AuthFlowType.ADMIN_NO_SRP_AUTH);

        try {
            var authResult = initiateAuthRequest(authRequest);
            log.info("Login successful for user: {}", loginRequestDto.username());
            return buildLoginResponse(authResult);
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
            throw new RuntimeException("Authentication failed", e);
        }
    }

    private Map<String, String> createAuthParamsForRefreshToken(String refreshToken) {
        return Map.of(
                "REFRESH_TOKEN", refreshToken,
                "SECRET_HASH", clientSecret
        );
    }

    private Map<String, String> createAuthParamsForLogin(LoginRequestDTO loginRequestDto) {
        return Map.of(
                "USERNAME", loginRequestDto.username(),
                "PASSWORD", loginRequestDto.password(),
                "SECRET_HASH", secretHashGenerator.calculateSecretHash(loginRequestDto.username())
        );
    }

    private AdminInitiateAuthRequest createAuthRequest(Map<String, String> authParams, AuthFlowType authFlowType) {
        return new AdminInitiateAuthRequest()
                .withAuthFlow(authFlowType)
                .withClientId(clientId)
                .withUserPoolId(userPoolId)
                .withAuthParameters(authParams);
    }

    private AuthenticationResultType initiateAuthRequest(AdminInitiateAuthRequest authRequest) {
        return cognitoIdentityProvider.adminInitiateAuth(authRequest).getAuthenticationResult();
    }

    private LoginResponseDTO buildLoginResponse(AuthenticationResultType authResult) {
        return buildLoginResponse(authResult, authResult.getRefreshToken());
    }

    private LoginResponseDTO buildLoginResponse(AuthenticationResultType authResult, String refreshToken) {
        return new LoginResponseDTO(
                authResult.getAccessToken(),
                refreshToken,
                authResult.getExpiresIn()
        );
    }

}
