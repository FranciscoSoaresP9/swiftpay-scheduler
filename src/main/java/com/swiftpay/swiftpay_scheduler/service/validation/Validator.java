package com.swiftpay.swiftpay_scheduler.service.validation;

public interface Validator<T> {
    void validate(T params);
}
