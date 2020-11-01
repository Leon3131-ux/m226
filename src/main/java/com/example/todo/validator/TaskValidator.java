package com.example.todo.validator;

import com.example.todo.domain.TaskDto;
import com.example.todo.service.TaskService;
import com.example.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class TaskValidator implements Validator {

    private final TaskService taskService;
    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return TaskDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TaskDto taskDto = (TaskDto) target;

    }
}
