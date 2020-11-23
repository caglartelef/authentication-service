package com.caglartelef.authenticationservice.exception.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException(String message) {
        super(message);
        log.error("Resource Already Exist Exception Message: {}", message);
    }
}
