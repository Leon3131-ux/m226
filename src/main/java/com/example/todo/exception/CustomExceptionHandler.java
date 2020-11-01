package com.example.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<? extends ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request){
        List<String> validationErrors = ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getCode)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        ValidationErrorDetails errorDetails = new ValidationErrorDetails(new Date().getTime(), ex.getMessage(), validationErrors, ex.getClass(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpClientErrorException.class)
    public final ResponseEntity<? extends ErrorDetails> handleHttpClientErrorException(HttpClientErrorException ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date().getTime(), ex.getStatusText(), ex.getClass(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, ex.getStatusCode());
    }

}
