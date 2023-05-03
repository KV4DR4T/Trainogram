package com.example.trainogram.exception;


public class NotEnoughRightsException extends ErrorCodeException {
    private static final int CODE = 459;

    public NotEnoughRightsException(String message) {
        super(CODE, message, "459");
    }
}
