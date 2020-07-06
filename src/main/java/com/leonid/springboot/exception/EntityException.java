package com.leonid.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityException extends EntityNotFoundException {
    public EntityException(String message) {
        super(message);
    }
}
