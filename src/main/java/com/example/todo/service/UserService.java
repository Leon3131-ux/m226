package com.example.todo.service;

import com.example.todo.domain.User;
import com.example.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserOrThrowException(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Optional<User> getCurrentUser(){
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

}
