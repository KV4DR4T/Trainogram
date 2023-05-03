package com.example.trainogram.exception;


public class LikeException extends ErrorCodeException {
    private static final int CODE = 458;

    public LikeException(String message) {
        super(CODE, message, "458");
    }
}
