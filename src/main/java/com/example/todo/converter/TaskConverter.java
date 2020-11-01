package com.example.todo.converter;

import com.example.todo.domain.Task;
import com.example.todo.domain.TaskDto;
import com.example.todo.domain.User;
import org.springframework.stereotype.Component;

@Component
public class TaskConverter {

    public TaskDto toDto(Task task){
        return new TaskDto(task.getId(), task.getTitle(), task.getDescription(), task.isDone(), task.getDate(), task.isDeleted());
    }

    public Task toEntity(TaskDto taskDto, User user){
        return new Task(taskDto.getTitle(), taskDto.getDescription(), taskDto.isDone(), taskDto.getDate(), taskDto.isDeleted(), user);
    }

}
