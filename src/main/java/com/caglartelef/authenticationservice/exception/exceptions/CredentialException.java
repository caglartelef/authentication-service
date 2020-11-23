package com.caglartelef.authenticationservice.exception.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CredentialException extends RuntimeException {
    public CredentialException(String message) {
        super(message);
        log.error("Credential Exception Message: {}", message);
    }
}
