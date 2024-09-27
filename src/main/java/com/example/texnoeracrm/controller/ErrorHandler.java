package com.example.texnoeracrm.controller;


import com.example.texnoeracrm.exception.NotFoundException;
import com.example.texnoeracrm.exception.UserNotAuthorizedException;
import com.example.texnoeracrm.model.get.ExceptionGetDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionGetDto handle(NotFoundException e){
        log.error(e.getLog());
        return new ExceptionGetDto(e.getMessage());
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionGetDto handle(UserNotAuthorizedException e){
        log.error(e.getLog());
        return new ExceptionGetDto(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionGetDto handle(Exception e){
        log.error(e.getMessage());
        return new ExceptionGetDto("UNEXPECTED_EXCEPTION");
    }
}
