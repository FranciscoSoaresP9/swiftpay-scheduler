package com.swiftpay.swiftpay_scheduler.exception;

import lombok.Data;

@Data
public class Error {
    private String message;
    private int status;
    private Long timestamp;
    private String apiError;
}
