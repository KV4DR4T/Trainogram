package com.example.trainogram.exception;


public class CommentNotFoundException extends ErrorCodeException {
    private static final int CODE = 404;

    public CommentNotFoundException(String message) {
        super(CODE, message, "404");
    }
}
