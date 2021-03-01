package com.example.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<? extends ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request){
        HashMap<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> validationErrors.put(fieldError.getField(), fieldError.getCode()));
        ValidationErrorDetails errorDetails = new ValidationErrorDetails(new Date().getTime(), ex.getMessage(), validationErrors, ex.getClass(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpClientErrorException.class)
    public final ResponseEntity<? extends ErrorDetails> handleHttpClientErrorException(HttpClientErrorException ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date().getTime(), ex.getStatusText(), ex.getClass(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, ex.getStatusCode());
    }

}
