package com.example.todo.core.dataProvider;

import com.example.todo.domain.User;

public class UserDataProvider {

    public User createDefaultUser(){
        return new User("test", "test");
    }

}
