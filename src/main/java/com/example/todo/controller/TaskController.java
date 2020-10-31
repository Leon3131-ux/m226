package com.example.todo.controller;

import com.example.todo.domain.TaskDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class TaskController {

    @RequestMapping(value = "/api/getTasks", method = RequestMethod.GET)
    public List<TaskDto> getTasks(){

    }
}
