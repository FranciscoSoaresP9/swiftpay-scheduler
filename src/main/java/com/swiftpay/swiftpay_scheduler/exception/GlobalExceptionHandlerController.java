package com.swiftpay.swiftpay_scheduler.exception;

import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> badRequestException(BadRequestException ex) {
        var error = new Error();
        error.setMessage(ex.getMessage());
        error.setTimestamp(new Date().getTime());
        error.setApiError(BadRequestException.class.getSimpleName());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUsernameException.class)
    public ResponseEntity<Object> invalidUsername(InvalidUsernameException ex) {
        var error = new Error();
        error.setMessage(ex.getMessage());
        error.setApiError(InvalidUsernameException.class.getSimpleName());
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Object> invalidPassword(InvalidPasswordException ex) {
        var error = new Error();
        error.setMessage(ex.getMessage());
        error.setApiError(InvalidPasswordException.class.getSimpleName());
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Object> userNameAlreadyExists(UsernameAlreadyExistsException ex) {
        var error = new Error();
        error.setMessage(ex.getMessage());
        error.setApiError(UsernameAlreadyExistsException.class.getSimpleName());
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFound(UserNotFoundException ex) {
        var error = new Error();
        error.setMessage(ex.getMessage());
        error.setApiError(UserNotFoundException.class.getSimpleName());
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBalanceException.class)
    public ResponseEntity<Object> invalidBalanceException(InvalidBalanceException ex) {
        var error = new Error();
        error.setMessage(ex.getMessage());
        error.setApiError(InvalidBalanceException.class.getSimpleName());
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTransferAmountException.class)
    public ResponseEntity<Object> invalidTransferAmount(InvalidTransferAmountException ex) {
        var error = new Error();
        error.setMessage(ex.getMessage());
        error.setApiError(InvalidTransferAmountException.class.getSimpleName());
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTransferDateException.class)
    public ResponseEntity<Object> invalidTransferDate(InvalidTransferDateException ex) {
        var error = new Error();
        error.setMessage(ex.getMessage());
        error.setApiError(InvalidTransferDateException.class.getSimpleName());
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BankAccountIbanNotFoundException.class)
    public ResponseEntity<Object> bankAccountIbanNotFoundDate(BankAccountIbanNotFoundException ex) {
        var error = new Error();
        error.setMessage(ex.getMessage());
        error.setApiError(BankAccountIbanNotFoundException.class.getSimpleName());
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BankAccountIdNotFoundException.class)
    public ResponseEntity<Object> bankAccountIdNotFoundDate(BankAccountIdNotFoundException ex) {
        var error = new Error();
        error.setMessage(ex.getMessage());
        error.setApiError(BankAccountIdNotFoundException.class.getSimpleName());
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Object> dateTimeParseException() {
        var error = new Error();
        error.setMessage("Invalid date. Please ensure the date is in the correct format (yyyy-mm-dd), for example, 2025-10-10, and try again.");
        error.setApiError(DateTimeParseException.class.getSimpleName());
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<Object> notAuthorizedException(NotAuthorizedException ex) {
        var error = new Error();
        error.setMessage(ex.getMessage());
        error.setApiError(NotAuthorizedException.class.getSimpleName());
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(error, null, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TransferCancellationNotAllowedException.class)
    public ResponseEntity<Object> transferCancellationNotAllowed(TransferCancellationNotAllowedException ex) {
        var error = new Error();
        error.setMessage(ex.getMessage());
        error.setApiError(TransferCancellationNotAllowedException.class.getSimpleName());
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransferDeletionNotAllowedException.class)
    public ResponseEntity<Object> transferDeletionNotAllowed(TransferDeletionNotAllowedException ex) {
        var error = new Error();
        error.setMessage(ex.getMessage());
        error.setApiError(TransferDeletionNotAllowedException.class.getSimpleName());
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransferModificationNotAllowedException.class)
    public ResponseEntity<Object> transferModificationNotAllowed(TransferModificationNotAllowedException ex) {
        var error = new Error();
        error.setMessage(ex.getMessage());
        error.setApiError(TransferModificationNotAllowedException.class.getSimpleName());
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransferNotFoundException.class)
    public ResponseEntity<Object> transferNotFound(TransferNotFoundException ex) {
        var error = new Error();
        error.setMessage(ex.getMessage());
        error.setApiError(TransferNotFoundException.class.getSimpleName());
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
    }

}
