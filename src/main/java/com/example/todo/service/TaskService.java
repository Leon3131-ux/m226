package com.example.todo.service;

import com.example.todo.domain.Task;
import com.example.todo.domain.User;
import com.example.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public List<Task> getAllTasksByUser(User user){
        return taskRepository.findAllByUser(user);
    }

    public Optional<Task> getTaskById(Long id){ return taskRepository.findById(id);}
}
