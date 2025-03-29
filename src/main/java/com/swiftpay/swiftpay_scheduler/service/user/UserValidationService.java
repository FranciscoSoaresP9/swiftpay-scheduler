package com.swiftpay.swiftpay_scheduler.service.user;

import com.swiftpay.swiftpay_scheduler.dto.registration.RegistrationRequestDTO;
import com.swiftpay.swiftpay_scheduler.exception.InvalidBalanceException;
import com.swiftpay.swiftpay_scheduler.exception.InvalidPasswordException;
import com.swiftpay.swiftpay_scheduler.exception.InvalidUsernameException;
import com.swiftpay.swiftpay_scheduler.exception.UsernameAlreadyExistsException;
import com.swiftpay.swiftpay_scheduler.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.swiftpay.swiftpay_scheduler.utils.RegexPattern.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserValidationService {

    private final UserRepository repository;

    public void validateUser(RegistrationRequestDTO write) {
        validateEmail(write.email());
        validatePassword(write.password());
        validateBalance(write.balance());
    }

    private void validateEmail(String email) {
        var emailExists = repository.existsByEmail(email);
        var emailIsValid = emailPattern().matcher(email).matches();

        if (emailExists) {
            throw new UsernameAlreadyExistsException("The email " + email + " already exists");
        }

        if (!emailIsValid) {
            throw new InvalidUsernameException("The email " + email + " is invalid");
        }
    }

    private void validatePassword(String password) {
        int MIN_PASSWORD_LENGTH = 8;
        var hasEnoughCharacters = password.length() >= MIN_PASSWORD_LENGTH;
        var hasSpecialCharacter = atLeastOneSpecialCharacterPattern().matcher(password).find();
        var hasUpperCase = atLeastOneUperCasePattern().matcher(password).find();
        var hasLowerCase = atLeastOneLowerCasePattern().matcher(password).find();
        var hasNumber = atLeastOneNumberPattern().matcher(password).find();
        if (!hasEnoughCharacters || !hasSpecialCharacter || !hasUpperCase || !hasLowerCase || !hasNumber) {
            throw new InvalidPasswordException("Invalid password");
        }
    }

    private void validateBalance(BigDecimal balance) {
        if (balance == null || balance.compareTo(BigDecimal.valueOf(200)) <= 0) {
            throw new InvalidBalanceException("Balance must be greater than 200 and not null");
        }
    }
}
