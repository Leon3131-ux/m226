package com.example.todo.core.dataProvider;

import com.example.todo.domain.TaskDto;

import java.util.Date;

public class TaskDtoDataProvider {

    public TaskDto createDefaultTaskDto(){
        return new TaskDto(1L, "test", "test", false, new Date(), false);
    }

}
