package com.example.todo.util.dataProvider;

import com.example.todo.domain.Task;
import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class TaskDataProvider {

    private final UserDataProvider userDataProvider;

    public Task createDefaultTask(){
        return new Task("test", "test", false, new Date(), false, userDataProvider.createDefaultUser());
    }

}
