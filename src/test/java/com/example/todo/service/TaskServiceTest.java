package com.example.todo.service;

import com.example.todo.domain.Task;
import com.example.todo.domain.TaskDto;
import com.example.todo.domain.User;
import com.example.todo.repository.TaskRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    void initializeServiceMock(){
        when(taskRepository.save(any(Task.class))).then(returnsFirstArg());
    }

    @Test
    public void testTaskUpdate(){
        User user = new User("test", "test");
        Task originalTask = new Task("test", "test", false, new Date(), false, user);
        TaskDto updateTask = new TaskDto(1L, "1", "1", true, getDefaultDate(), true);

        taskService.updateTask(originalTask, updateTask);

        assertThat(originalTask.getTitle()).isSameAs(updateTask.getTitle());
        assertThat(originalTask.getDescription()).isSameAs(updateTask.getDescription());
        assertThat(originalTask.getDate()).isSameAs(updateTask.getDate());
        assertThat(originalTask.isDone()).isSameAs(updateTask.isDone());
        assertThat(originalTask.isDeleted()).isSameAs(updateTask.isDeleted());
        assertThat(originalTask.getUser()).isSameAs(user);
    }

    private Date getDefaultDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1990);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

}
