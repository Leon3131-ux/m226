package com.example.todo.exception;

public class CustomUsernameNotFoundException extends RuntimeException{

    public CustomUsernameNotFoundException(String message){
        super(message);
    }

}
