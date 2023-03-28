package com.example.Trainogram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubscriptionNotFoundException extends ErrorCodeException{
    private static final int CODE=457;

    public SubscriptionNotFoundException(String message) {
        super(CODE,message,"457");
    }
}
