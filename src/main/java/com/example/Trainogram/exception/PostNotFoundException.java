package com.example.Trainogram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends ErrorCodeException{
    private static final int CODE=453;

    public PostNotFoundException(String message) {
        super(CODE,message,"453");
    }
}
