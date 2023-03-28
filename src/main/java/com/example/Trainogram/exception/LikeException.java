package com.example.Trainogram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LikeException extends ErrorCodeException{
    private static final int CODE=458;

    public LikeException(String message) {
        super(CODE,message,"458");
    }
}
