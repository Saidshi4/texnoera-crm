package com.example.texnoeracrm.exception;

import lombok.Getter;

@Getter
public class AlreadyExistException extends RuntimeException {
    private final String message;
    private final String log;
    public AlreadyExistException(String message, String log) {
        super(message);
        this.message = message;
        this.log = log;
    }
}
