package com.example.todo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {

    private Long timestamp;
    private String message;
    private Class<? extends Exception> exceptionClass;
    private String details;

}
