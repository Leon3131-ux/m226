package com.example.todo.controller;

import com.example.todo.core.system.TaskSystem;
import com.example.todo.core.system.UserSystem;
import com.example.todo.core.util.MvcUtils;
import com.example.todo.domain.Task;
import com.example.todo.domain.TaskDto;
import com.example.todo.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class TaskControllerIT {

    @Autowired
    private TaskSystem taskSystem;

    @Autowired
    private UserSystem userSystem;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup(){
    }

    @Test
    @WithMockUser("user1")
    public void getAllTasksBelongToCorrectUser() throws Exception {
        User user1 = userSystem.createUserWithCredentialsInDb("user1", "test");
        User user2 = userSystem.createUserWithCredentialsInDb("user2", "test");
        Task task1 = taskSystem.createTaskInDb("user1Task", "test", false, false, new Date(), user1);
        Task task2 = taskSystem.createTaskInDb("user1Task", "test", false, false, new Date(), user1);
        taskSystem.createTaskInDb("user2Task", "test", false, false, new Date(), user2);

        MvcResult result = mockMvc.perform(get("/api/getTasks")).andExpect(status().isOk()).andReturn();

        List<TaskDto> taskDtos = MvcUtils.convertList(result, TaskDto.class);

        assertThat(taskDtos).extracting(TaskDto::getId).containsOnly(task1.getId(), task2.getId());
    }


}
