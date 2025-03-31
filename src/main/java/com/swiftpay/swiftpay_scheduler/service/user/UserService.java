package com.swiftpay.swiftpay_scheduler.service.user;

import com.swiftpay.swiftpay_scheduler.dto.registration.RegistrationRequestDTO;
import com.swiftpay.swiftpay_scheduler.dto.user.UserDTO;
import com.swiftpay.swiftpay_scheduler.entity.user.User;
import com.swiftpay.swiftpay_scheduler.entity.user.bank_account.BankAccount;
import com.swiftpay.swiftpay_scheduler.exception.UserNotFoundException;
import com.swiftpay.swiftpay_scheduler.repository.UserRepository;
import com.swiftpay.swiftpay_scheduler.service.auth.AuthService;
import com.swiftpay.swiftpay_scheduler.service.validation.ValidatorFactory;
import com.swiftpay.swiftpay_scheduler.service.validation.ValidatorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final ValidatorFactory validatorFactory;
    private final AuthService authService;

    public User create(RegistrationRequestDTO write) {

        validatorFactory.getValidator(ValidatorType.CREATE_USER_VALIDATOR).validate(write);

        var bankAccount = new BankAccount()
                .withBalance(write.balance())
                .withIban(UUID.nameUUIDFromBytes(write.email().getBytes()).toString());

        var user = new User()
                .withName(write.name())
                .withEmail(write.email())
                .withBankAccount(bankAccount);

        bankAccount.setUser(user);

        return repository.save(user);
    }

    public void patchExternalId(Long id, String externalId) {
        var user = getUserById(id);
        user.setExternalId(externalId);
        repository.save(user);
    }

    public User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    public User getCurrentUser() {
        return getUserById(authService.getCurrentId());
    }

    public UserDTO getCurrentDTO() {
        var current = getCurrentUser();
        return new UserDTO(
                current.getId(),
                current.getName(),
                current.getEmail(),
                current.getBankAccount().getIban()
        );
    }

    @Transactional
    public void patchName(String name) {
        log.info("Updating name for current user to: {}", name);
        var currentUser = getCurrentUser();
        currentUser.setName(name);
        repository.save(currentUser);
        log.info("Successfully updated name for user ID: {} to: {}", currentUser.getId(), name);
    }
}
