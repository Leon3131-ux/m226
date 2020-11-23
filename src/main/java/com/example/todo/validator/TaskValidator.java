package com.example.todo.validator;

import com.example.todo.domain.Task;
import com.example.todo.domain.TaskDto;
import com.example.todo.domain.User;
import com.example.todo.service.TaskService;
import com.example.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

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

        if(taskDto.getId() != null && taskDto.getId() != 0){
            Optional<Task> oldTask = taskService.getTaskById(taskDto.getId());
            if(oldTask.isPresent()){
                Optional<User> currentUser = userService.getCurrentUser();
                if(currentUser.isPresent()){
                    if(oldTask.get().getUser() != (currentUser.get())){
                        errors.reject("errors.task.id.invalid");
                    }
                }else {
                    errors.reject("errors.task.id.invalid");
                }
            }else {
                errors.reject("errors.task.id.invalid");
            }
        }

        if(taskDto.getTitle() == null || taskDto.getTitle().isBlank()){
            errors.reject("errors.task.title.required");
        }
        if(taskDto.getDescription() == null || taskDto.getDescription().isBlank()){
            errors.reject("errors.task.description.required");
        }
        if(taskDto.getDate() == null){
            errors.reject("errors.task.date.required");
        }
    }
}
