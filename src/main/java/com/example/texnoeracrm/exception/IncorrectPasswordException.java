package com.example.texnoeracrm.exception;

import lombok.Getter;

@Getter
public class IncorrectPasswordException extends RuntimeException{

    private final String message;
    private final String log;
    public IncorrectPasswordException(String message, String log) {
        super(message);
        this.message = message;
        this.log = log;
    }
}
