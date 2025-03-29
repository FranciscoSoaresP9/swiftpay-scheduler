package com.swiftpay.swiftpay_scheduler.service.registration;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpRequest;
import com.swiftpay.swiftpay_scheduler.dto.registration.CognitoConfirmRegistrationRequestDto;
import com.swiftpay.swiftpay_scheduler.exception.BadRequestException;
import com.swiftpay.swiftpay_scheduler.utils.CognitoHmacSecretHashGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CognitoConfirmRegistrationService implements ConfirmRegistrationService<CognitoConfirmRegistrationRequestDto> {

    private final AWSCognitoIdentityProvider identityProvider;
    private final CognitoHmacSecretHashGenerator secretHashGenerator;

    @Value(value = "${aws.cognito.clientId}")
    private String clientId;

    @Override
    public void confirm(CognitoConfirmRegistrationRequestDto write) {
        log.info("Confirming account with username: {}", write.email());
        try {
            var request = new ConfirmSignUpRequest()
                    .withUsername(write.email())
                    .withSecretHash(secretHashGenerator.calculateSecretHash(write.email()))
                    .withConfirmationCode(write.code())
                    .withClientId(clientId);

            identityProvider.confirmSignUp(request);

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        log.info("Account with username: {} confirmed", write.email());
    }
}
