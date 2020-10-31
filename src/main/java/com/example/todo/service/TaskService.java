package com.example.todo.service;

import com.example.todo.domain.Task;
import com.example.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
}
