package com.example.todo.controller;

import com.example.todo.converter.TaskConverter;
import com.example.todo.domain.TaskDto;
import com.example.todo.domain.User;
import com.example.todo.service.TaskService;
import com.example.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskConverter taskConverter;
    private final UserService userService;

    @RequestMapping(value = "/api/getTasks", method = RequestMethod.GET)
    public ResponseEntity getTasksByUser(Principal principal){
        User user = userService.getUserOrThrowException(principal.getName());
        return new ResponseEntity(taskService.getAllTasksByUser(user).stream().map(taskConverter::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }
}
