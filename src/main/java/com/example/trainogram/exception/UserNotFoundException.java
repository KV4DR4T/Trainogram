package com.example.trainogram.exception;


public class UserNotFoundException extends ErrorCodeException {
    private static final int CODE = 404;

    public UserNotFoundException(String message) {
        super(CODE, message, "404");
    }
}
