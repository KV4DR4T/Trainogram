package com.example.Trainogram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidParamException extends ErrorCodeException{
    private static final int CODE= 456;

    public InvalidParamException(String message) {
        super(CODE,message,"456");
    }
}
