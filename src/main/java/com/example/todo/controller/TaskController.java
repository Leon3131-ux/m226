package com.example.todo.controller;

import com.example.todo.converter.TaskConverter;
import com.example.todo.domain.Task;
import com.example.todo.domain.TaskDto;
import com.example.todo.domain.User;
import com.example.todo.service.TaskService;
import com.example.todo.service.UserService;
import com.example.todo.validator.TaskValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskConverter taskConverter;
    private final UserService userService;
    private final TaskValidator taskValidator;

    @InitBinder("taskDto")
    public void initSaveProductBinder(WebDataBinder binder) {
        binder.setValidator(taskValidator);
    }

    @RequestMapping(value = "/api/getTasks", method = RequestMethod.GET)
    public ResponseEntity getTasksByUser(Principal principal){
        User user = userService.getUserOrThrowException(principal.getName());
        return new ResponseEntity(taskService.getAllTasksByUser(user).stream().map(taskConverter::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/saveTask", method = RequestMethod.POST)
    public ResponseEntity saveTask(Principal principal, @RequestBody @Validated TaskDto taskDto){
        User user = userService.getUserOrThrowException(principal.getName());
        if(taskDto.getId() == 0 || taskDto.getId() == null){
            Task task = taskConverter.toEntity(taskDto, user);
            return new ResponseEntity(taskConverter.toDto(taskService.saveTask(task)), HttpStatus.CREATED);
        }else {
            Optional<Task> oldTask = taskService.getTaskById(taskDto.getId());
            if(oldTask.isPresent()){
                return new ResponseEntity(taskConverter.toDto(taskService.updateTask(oldTask.get(), taskDto)), HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/api/deleteTask/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTask(Principal principal, @PathVariable("id") Task task){
        User user = userService.getUserOrThrowException(principal.getName());
        if (task.getUser().equals(user)) {
            if (task.isDeleted()) {
                taskService.deleteTask(task);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                task.setDeleted(true);
                return new ResponseEntity(taskConverter.toDto(taskService.saveTask(task)), HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
