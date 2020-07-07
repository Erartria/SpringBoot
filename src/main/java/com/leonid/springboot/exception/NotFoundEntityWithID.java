package com.leonid.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity with this id is not exists")
public class NotFoundEntityWithID extends RuntimeException {
    public NotFoundEntityWithID(String message) {
        super(message);
    }
}
