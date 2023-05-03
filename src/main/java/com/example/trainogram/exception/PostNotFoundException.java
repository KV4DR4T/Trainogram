package com.example.trainogram.exception;


public class PostNotFoundException extends ErrorCodeException {
    private static final int CODE = 404;

    public PostNotFoundException(String message) {
        super(CODE, message, "404");
    }
}
