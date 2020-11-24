package com.example.todo.core.system;

import com.example.todo.core.dataProvider.UserDataProvider;
import com.example.todo.domain.User;
import com.example.todo.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserSystem {

    private UserDataProvider userDataProvider;

    private final UserRepository userRepository;

    public UserSystem (UserRepository userRepository){
        this.userRepository = userRepository;
        userDataProvider = new UserDataProvider();
    }

    public User createUserWithCredentialsInDb(String username, String password){
        return userRepository.save(new User(username, password));
    }

}
