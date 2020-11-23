package com.example.todo.util.dataProvider;

import com.example.todo.domain.User;

public class UserDataProvider {

    public User createDefaultUser(){
        return new User("test", "test");
    }

}
