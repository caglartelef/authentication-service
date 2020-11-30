package com.caglartelef.authenticationservice.exception.handler;

import com.caglartelef.authenticationservice.exception.exceptions.CredentialException;
import com.caglartelef.authenticationservice.exception.exceptions.ResourceAlreadyExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(CredentialException.class)
    public ResponseEntity<String> handleCredentialException(CredentialException exception) {
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<String> handleResourceAlreadyExistException(ResourceAlreadyExistException exception) {
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }
}
