package com.example.todo.validator;

import com.example.todo.domain.Task;
import com.example.todo.domain.TaskDto;
import com.example.todo.domain.User;
import com.example.todo.service.TaskService;
import com.example.todo.service.UserService;
import com.example.todo.util.ValidationFieldErrorAsserter;
import com.example.todo.util.dataProvider.TaskDataProvider;
import com.example.todo.util.dataProvider.TaskDtoDataProvider;
import com.example.todo.util.dataProvider.UserDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskValidatorTest {

    @InjectMocks
    private TaskValidator taskValidator;

    @Mock
    private TaskService taskService;

    @Mock
    private UserService userService;

    private TaskDataProvider taskDataProvider;

    private TaskDtoDataProvider taskDtoDataProvider;

    private UserDataProvider userDataProvider;

    private ValidationFieldErrorAsserter validationFieldErrorAsserter;

    private TaskDto taskDto;

    public void initializeTaskServiceMockReturnEmpty(){
        when(taskService.getTaskById(any(Long.class))).thenReturn(Optional.empty());
    }

    public void initializeTaskServiceMockReturnTask(Task task){
        when(taskService.getTaskById(any(Long.class))).thenReturn(Optional.of(task));
    }

    public void initializeUserServiceMockReturnUser(User user){
        when(userService.getCurrentUser()).thenReturn(Optional.of(user));
    }

    @Before
    public void setup(){
        userDataProvider = new UserDataProvider();
        taskDataProvider = new TaskDataProvider(userDataProvider);
        taskDtoDataProvider = new TaskDtoDataProvider();
        taskDto = taskDtoDataProvider.createDefaultTaskDto();
        Errors errors = new BeanPropertyBindingResult(taskDto, "taskDto");
        validationFieldErrorAsserter = new ValidationFieldErrorAsserter(taskValidator, errors);
    }

    @Test
    public void testValidatorIdError(){
        initializeTaskServiceMockReturnEmpty();

        validationFieldErrorAsserter.assertFieldHasError(taskDto, "ID", "errors.task.id.invalid");
    }

    @Test
    public void testValidatorUserMismatchError(){
        initializeTaskServiceMockReturnTask(taskDataProvider.createDefaultTask());
        initializeUserServiceMockReturnUser(userDataProvider.createDefaultUser());

        validationFieldErrorAsserter.assertFieldHasError(taskDto, "ID", "errors.task.id.invalid");
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
        taskDto.setTitle("      ");

        validationFieldErrorAsserter.assertFieldHasError(taskDto, "description", "errors.task.description.required");
    }

    @Test
    public void testValidatorDescriptionNullError(){
        taskDto.setTitle(null);

        validationFieldErrorAsserter.assertFieldHasError(taskDto, "description", "errors.task.description.required");
    }

}
