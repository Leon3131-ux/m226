package com.example.todo.core.dataProvider;

import com.example.todo.domain.Task;
import com.example.todo.domain.User;
import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class TaskDataProvider {

    private final UserDataProvider userDataProvider;

    public Task createDefaultTask(){
        return new Task("test", "test", false, new Date(), false, userDataProvider.createDefaultUser());
    }

    public Task createDefaultTaskWithUser(User user){
        return new Task("test", "test", false, new Date(), false, user);
    }

}
