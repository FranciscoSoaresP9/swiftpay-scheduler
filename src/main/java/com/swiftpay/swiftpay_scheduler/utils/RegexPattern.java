package com.swiftpay.swiftpay_scheduler.utils;

import java.util.regex.Pattern;

public class RegexPattern {

    public static Pattern emailPattern() {
        return Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    }

    public static Pattern atLeastOneSpecialCharacterPattern() {
        return Pattern.compile(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");
    }

    public static Pattern atLeastOneLowerCasePattern() {
        return Pattern.compile("[a-z]");
    }

    public static Pattern atLeastOneUperCasePattern() {
        return Pattern.compile("[A-Z]");
    }

    public static Pattern atLeastOneNumberPattern() {
        return Pattern.compile("[0-9]");
    }
}
