package com.example.todo.core.system;

import com.example.todo.core.dataProvider.TaskDataProvider;
import com.example.todo.core.dataProvider.UserDataProvider;
import com.example.todo.domain.Task;
import com.example.todo.domain.User;
import com.example.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TaskSystem {

    private final TaskRepository taskRepository;

    public Task createTaskInDb(String title, String description, boolean done, boolean deleted, Date date, User user){
        return taskRepository.save(new Task(title, description, done, date, deleted, user));
    }

}
