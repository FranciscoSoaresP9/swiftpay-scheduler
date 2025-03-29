package com.swiftpay.swiftpay_scheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        var errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<Object> userNotFound(InvalidBalanceException ex) {
        var error = new Error();
        error.setMessage(ex.getMessage());
        error.setApiError(InvalidBalanceException.class.getSimpleName());
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
    }

}
