package com.example.texnoeracrm.controller;


import com.example.texnoeracrm.exception.*;
import com.example.texnoeracrm.model.get.ExceptionGetDto;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionGetDto handle(NotFoundException e){
        log.error(e.getLog());
        return new ExceptionGetDto(e.getMessage());
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionGetDto handle(AlreadyExistException e){
        log.error(e.getLog());
        return new ExceptionGetDto(e.getMessage());
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionGetDto handle(IncorrectPasswordException e){
        log.error(e.getLog());
        return new ExceptionGetDto(e.getMessage());
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionGetDto handle(UserNotAuthorizedException e){
        log.error(e.getLog());
        return new ExceptionGetDto(e.getMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionGetDto handle(InvalidTokenException e){
        log.error(e.getLog());
        return new ExceptionGetDto(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionGetDto handle(Exception e){
        log.error(e.getMessage());
        return new ExceptionGetDto("UNEXPECTED_EXCEPTION");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionGetDto handle(ExpiredJwtException e){
        log.error(e.getMessage());
        return new ExceptionGetDto("JWT_TOKEN_EXPIRED");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation failed: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
