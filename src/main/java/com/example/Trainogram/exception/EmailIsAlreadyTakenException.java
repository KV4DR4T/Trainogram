package com.example.Trainogram.exception;

public class EmailIsAlreadyTakenException extends ErrorCodeException {
    private static final int CODE = 458;
    public EmailIsAlreadyTakenException(String message) {
        super(CODE,message,"458");
    }
}
