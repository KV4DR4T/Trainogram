package com.example.trainogram.exception;

import io.jsonwebtoken.JwtException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.SignatureException;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(ErrorCodeException.class)
    public ResponseEntity handleAlreadyExistsException(ErrorCodeException ex) {
        return ResponseEntity.status(ex.getCode()).body(ex.getMessage());
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity handleAlreadyExistsException(JwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
//    public ResponseEntity handleException(Exception ex) {
//        return new ResponseEntity(ExceptionUtils.getStackTrace(ex), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
