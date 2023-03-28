package com.example.Trainogram.exception;


public class UserNotFoundException extends ErrorCodeException{
    private static final int CODE=452;

    public UserNotFoundException(String message) {
        super(CODE,message,"452");
    }
}
