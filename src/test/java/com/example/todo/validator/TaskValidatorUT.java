package com.example.todo.validator;

import com.example.todo.domain.Task;
import com.example.todo.domain.TaskDto;
import com.example.todo.domain.User;
import com.example.todo.service.TaskService;
import com.example.todo.service.UserService;
import com.example.todo.core.util.ValidationFieldErrorAsserter;
import com.example.todo.core.dataProvider.TaskDataProvider;
import com.example.todo.core.dataProvider.TaskDtoDataProvider;
import com.example.todo.core.dataProvider.UserDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskValidatorUT {

    @InjectMocks
    private TaskValidator taskValidator;

    @Mock
    private TaskService taskService;

    @Mock
    private UserService userService;

    private ValidationFieldErrorAsserter validationFieldErrorAsserter;

    private TaskDto taskDto;

    public void initializeTaskServiceMockReturnEmpty(){
        when(taskService.getTaskById(any(Long.class))).thenReturn(Optional.empty());
    }

    public void taskServiceMockGetByIdReturn(Task task){
        when(taskService.getTaskById(any(Long.class))).thenReturn(Optional.of(task));
    }

    public void userServiceMockGetCurrentUserReturn(User user){
        when(userService.getCurrentUser()).thenReturn(Optional.of(user));
    }

    @Before
    public void setupWithNoErrors(){
        UserDataProvider userDataProvider = new UserDataProvider();
        TaskDataProvider taskDataProvider = new TaskDataProvider(userDataProvider);
        TaskDtoDataProvider taskDtoDataProvider = new TaskDtoDataProvider();
        taskDto = taskDtoDataProvider.createDefaultTaskDto();
        Task task = taskDataProvider.createDefaultTask();
        Errors errors = new BeanPropertyBindingResult(taskDto, "taskDto");
        validationFieldErrorAsserter = new ValidationFieldErrorAsserter(taskValidator, errors);
        taskServiceMockGetByIdReturn(task);
        userServiceMockGetCurrentUserReturn(task.getUser());
    }

    @Test
    public void testValidatorIdError(){
        initializeTaskServiceMockReturnEmpty();

        validationFieldErrorAsserter.assertFieldHasError(taskDto, "id", "errors.task.id.invalid");
    }

    @Test
    public void testValidatorUserMismatchError(){
        Task task = new Task("oldTask", "oldTask", false, new Date(), false, new User("taskUser", "taskUser"));
        taskServiceMockGetByIdReturn(task);
        userServiceMockGetCurrentUserReturn(new User("mismatchedUser", "mismatchedUser"));

        validationFieldErrorAsserter.assertFieldHasError(taskDto, "id", "errors.task.id.invalid");
    }

    @Test
    public void testValidatorTitleEmptyError(){
        taskDto.setTitle("");

        validationFieldErrorAsserter.assertFieldHasError(taskDto, "title", "errors.task.title.required");
    }

    @Test
    public void testValidatorTitleSpaceError(){
        taskDto.setTitle("     ");

        validationFieldErrorAsserter.assertFieldHasError(taskDto, "title", "errors.task.title.required");
    }

    @Test
    public void testValidatorTitleNullError(){
        taskDto.setTitle(null);

        validationFieldErrorAsserter.assertFieldHasError(taskDto, "title", "errors.task.title.required");
    }

    @Test
    public void testValidatorDescriptionEmptyError(){
        taskDto.setDescription("");

        validationFieldErrorAsserter.assertFieldHasError(taskDto, "description", "errors.task.description.required");
    }

    @Test
    public void testValidatorDescriptionSpaceError(){
        taskDto.setDescription("      ");

        validationFieldErrorAsserter.assertFieldHasError(taskDto, "description", "errors.task.description.required");
    }

    @Test
    public void testValidatorDescriptionNullError(){
        taskDto.setDescription(null);

        validationFieldErrorAsserter.assertFieldHasError(taskDto, "description", "errors.task.description.required");
    }

    @Test
    public void testValidatorDateNullError(){
        taskDto.setDate(null);

        validationFieldErrorAsserter.assertFieldHasError(taskDto, "date", "errors.task.date.required");
    }

}
