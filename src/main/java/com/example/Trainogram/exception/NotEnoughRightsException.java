package com.example.Trainogram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotEnoughRightsException extends ErrorCodeException{
    private static final int CODE=459;

    public NotEnoughRightsException(String message) {
        super(CODE,message,"459");
    }
}
