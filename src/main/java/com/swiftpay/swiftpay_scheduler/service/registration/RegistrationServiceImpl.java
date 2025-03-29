package com.swiftpay.swiftpay_scheduler.service.registration;

import com.swiftpay.swiftpay_scheduler.dto.registration.CognitoRegistrationRequestDTO;
import com.swiftpay.swiftpay_scheduler.dto.registration.CognitoRegistrationResponseDTO;
import com.swiftpay.swiftpay_scheduler.dto.registration.RegistrationRequestDTO;
import com.swiftpay.swiftpay_scheduler.dto.registration.RegistrationResponseDTO;
import com.swiftpay.swiftpay_scheduler.service.identity_provider.IdentityProviderService;
import com.swiftpay.swiftpay_scheduler.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;
    private final IdentityProviderService<CognitoRegistrationResponseDTO, CognitoRegistrationRequestDTO> identityProviderService;

    @Transactional
    @Override
    public RegistrationResponseDTO register(RegistrationRequestDTO write) {
        var user = userService.create(write);

        var cognitoUserWrite = new CognitoRegistrationRequestDTO(
                user.getId().toString(),
                user.getName(),
                user.getEmail(), write.password()
        );

        var cognitoUser = identityProviderService.register(cognitoUserWrite);

        userService.patchExternalId(user.getId(), cognitoUser.id());

        return new RegistrationResponseDTO(
                user.getId(),
                user.getName(),
                user.getBankAccount().getIban(),
                user.getBankAccount().getId(),
                user.getBankAccount().getBalance()
        );
    }
}