package com.swiftpay.swiftpay_scheduler.service.identity_provider;

import com.amazonaws.services.accessanalyzer.model.ValidationException;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.swiftpay.swiftpay_scheduler.dto.registration.CognitoRegistrationRequestDTO;
import com.swiftpay.swiftpay_scheduler.dto.registration.CognitoRegistrationResponseDTO;
import com.swiftpay.swiftpay_scheduler.utils.CognitoHmacSecretHashGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CognitoIdentityProviderService implements IdentityProviderService<CognitoRegistrationResponseDTO, CognitoRegistrationRequestDTO> {

    private final AWSCognitoIdentityProvider identityProvider;
    private final CognitoHmacSecretHashGenerator secretHashGenerator;

    @Value(value = "${aws.cognito.clientId}")
    private String clientId;

    @Override
    public CognitoRegistrationResponseDTO register(CognitoRegistrationRequestDTO write) {
        try {

            var request = new SignUpRequest()
                    .withUsername(write.username())
                    .withPassword(write.password())
                    .withSecretHash(secretHashGenerator.calculateSecretHash(write.username()))
                    .withClientId(clientId);

            var userId = new AttributeType()
                    .withName("custom:external_id")
                    .withValue(write.externalId());

            request.withUserAttributes(userId);

            var res = identityProvider.signUp(request);

            return new CognitoRegistrationResponseDTO(res.getUserSub());

        } catch (Exception e) {
            throw new ValidationException("Error during sign up : " + e.getMessage());
        }
    }
}