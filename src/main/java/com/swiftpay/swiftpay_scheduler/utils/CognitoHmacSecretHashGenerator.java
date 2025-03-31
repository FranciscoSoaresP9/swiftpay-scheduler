package com.swiftpay.swiftpay_scheduler.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Slf4j
public class CognitoHmacSecretHashGenerator {

    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    @Value("${aws.cognito.clientId}")
    private String clientId;

    @Value("${aws.cognito.clientSecret}")
    private String clientSecret;

    public String calculateSecretHash(String username) {
        var login = new SecretKeySpec(clientSecret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256_ALGORITHM);

        try {
            var mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(login);
            mac.update(username.getBytes(StandardCharsets.UTF_8));
            var rawHmac = mac.doFinal(clientId.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            log.error("Error calculating secret hash: {}", e.getMessage());
            throw new RuntimeException("Error while calculating Secret Hash", e);
        }
    }
}