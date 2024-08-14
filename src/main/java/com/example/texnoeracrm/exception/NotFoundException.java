package com.example.texnoeracrm.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final String message;
    private final String log;
    public NotFoundException(String message, String log) {
        super(message);
        this.message = message;
        this.log = log;
    }
}
