package com.swiftpay.swiftpay_scheduler.service.auth;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.GetUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AWSCognitoIdentityProvider cognitoClient;

    @Override
    public Long getCurrentId() {
        var userAttributes = getUserAttributes();
        return Long.parseLong(userAttributes.get("custom:external_id"));
    }

    private Jwt getToken() {
        return (Jwt) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }

    private HashMap<String, String> getUserAttributes() {
        var token = getToken();
        var getUserRequest = new GetUserRequest()
                .withAccessToken(token.getTokenValue());
        var user = cognitoClient.getUser(getUserRequest);

        var attributes = new HashMap<String, String>();
        user.getUserAttributes().forEach(at -> attributes.put(at.getName(), at.getValue()));

        return attributes;
    }

}