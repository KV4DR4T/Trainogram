package com.example.trainogram.exception;


public class SubscriptionNotFoundException extends ErrorCodeException {
    private static final int CODE = 404;

    public SubscriptionNotFoundException(String message) {
        super(CODE, message, "404");
    }
}
