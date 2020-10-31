package com.example.todo.converter;

import com.example.todo.domain.Task;
import com.example.todo.domain.TaskDto;
import org.springframework.stereotype.Component;

@Component
public class TaskConverter {

    public TaskDto toDto(Task task){
        return new TaskDto(task.getId(), task.getTitle(), task.getDescription(), task.isDone(), task.getDate(), task.isDeleted());
    }

}
