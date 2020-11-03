package com.example.todo;

import com.example.todo.domain.User;
import com.example.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class TodoApplication {

    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    private void initUser(){
        userService.saveUser(new User("admin", bCryptPasswordEncoder().encode("admin")));
    }

}
