package com.leonid.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity with this id is not exists")
public class EntityException extends RuntimeException {
    public EntityException(String message) {
        super(message);
    }
}
