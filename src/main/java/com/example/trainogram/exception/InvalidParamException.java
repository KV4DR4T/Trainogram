package com.example.trainogram.exception;


public class InvalidParamException extends ErrorCodeException {
    private static final int CODE = 456;

    public InvalidParamException(String message) {
        super(CODE, message, "456");
    }
}
