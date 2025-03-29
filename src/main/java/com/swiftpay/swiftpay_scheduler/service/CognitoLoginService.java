package com.swiftpay.swiftpay_scheduler.service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.swiftpay.swiftpay_scheduler.dto.LoginRequestDTO;
import com.swiftpay.swiftpay_scheduler.dto.LoginResponseDTO;
import com.swiftpay.swiftpay_scheduler.dto.RefreshTokenRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CognitoLoginService implements LoginService {

    private final AWSCognitoIdentityProvider cognitoIdentityProvider;

    @Value("${aws.cognito.userPoolId}")
    private String userPoolId;

    @Value("${aws.cognito.clientId}")
    private String clientId;

    @Value("${aws.cognito.clientSecret}")
    private String clientSecret;

    @Override
    public LoginResponseDTO login(RefreshTokenRequestDTO refreshTokenRequestDto) {
        var authParams = createAuthParamsForRefreshToken(refreshTokenRequestDto.token());
        var authRequest = createAuthRequest(authParams, AuthFlowType.REFRESH_TOKEN_AUTH);
        var authResult = initiateAuthRequest(authRequest);

        return buildLoginResponse(authResult, refreshTokenRequestDto.token());
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDto) {
        var authParams = createAuthParamsForLogin(loginRequestDto);
        var authRequest = createAuthRequest(authParams, AuthFlowType.ADMIN_NO_SRP_AUTH);

        try {
            var authResult = initiateAuthRequest(authRequest);
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
                "SECRET_HASH", calculateSecretHash(loginRequestDto.username())
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

    private String calculateSecretHash(String userName) {
        final var HMAC_SHA256_ALGORITHM = "HmacSHA256";
        var signingKey = new SecretKeySpec(clientSecret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256_ALGORITHM);

        try {
            var mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signingKey);
            mac.update(userName.getBytes(StandardCharsets.UTF_8));
            var rawHmac = mac.doFinal(clientId.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            log.error("Error calculating secret hash: {}", e.getMessage());
            throw new RuntimeException("Error while calculating Secret Hash", e);
        }
    }
}
