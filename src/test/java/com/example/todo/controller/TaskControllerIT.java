package com.example.todo.controller;

import com.example.todo.core.system.TaskSystem;
import com.example.todo.core.system.UserSystem;
import com.example.todo.core.util.MvcUtils;
import com.example.todo.domain.Task;
import com.example.todo.domain.TaskDto;
import com.example.todo.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
    public void getAllTasks() throws Exception {
        User user1 = userSystem.createUserWithCredentialsInDb("user1", "test");
        User user2 = userSystem.createUserWithCredentialsInDb("user2", "test");
        Task user1task1 = taskSystem.createTaskInDb("user1Task", "1. user1 task", false, false, new Date(), user1);
        Task user1task2 = taskSystem.createTaskInDb("user1Task", "2. user1 task", false, false, new Date(), user1);
        taskSystem.createTaskInDb("user2Task", "1. user2 task", false, false, new Date(), user2);

        MvcResult result = mockMvc.perform(get("/api/getTasks")).andExpect(status().isOk()).andReturn();

        List<TaskDto> taskDtos = MvcUtils.convertList(result, TaskDto.class);

        assertThat(taskDtos).extracting(TaskDto::getId).containsOnly(user1task1.getId(), user1task2.getId());
    }

    @Test
    @WithMockUser("user3")
    public void createValidTask() throws Exception {
        userSystem.createUserWithCredentialsInDb("user3", "test");
        TaskDto taskDto = new TaskDto(null, "user3Task", "1. user3 task", false, new Date(), false);
        String taskDtoString = new ObjectMapper().writeValueAsString(taskDto);

        mockMvc.perform(post("/api/saveTask").contentType(MediaType.APPLICATION_JSON)
                .content(taskDtoString)).andExpect(status().isCreated());

        MvcResult result = mockMvc.perform(get("/api/getTasks")).andExpect(status().isOk()).andReturn();

        List<TaskDto> taskDtos = MvcUtils.convertList(result, TaskDto.class);

        assertThat(taskDtos).hasSize(1);
        assertThat(taskDtos).extracting(TaskDto::getTitle).containsOnly(taskDto.getTitle());
    }

    @Test
    @WithMockUser("user6")
    public void updateValidTask() throws Exception {
        User user6 = userSystem.createUserWithCredentialsInDb("user6", "test");
        Task task = taskSystem.createTaskInDb("user6task", "1. user6 task", false, false, new Date(), user6);

        TaskDto taskDto = new TaskDto(task.getId(), "user6Task", "updated user6 task", false, new Date(), false);
        String taskDtoString = new ObjectMapper().writeValueAsString(taskDto);

        mockMvc.perform(post("/api/saveTask").contentType(MediaType.APPLICATION_JSON)
                .content(taskDtoString)).andExpect(status().isOk());

        MvcResult result = mockMvc.perform(get("/api/getTasks")).andExpect(status().isOk()).andReturn();

        List<TaskDto> taskDtos = MvcUtils.convertList(result, TaskDto.class);

        assertThat(taskDtos).hasSize(1);
        assertThat(taskDtos).extracting(TaskDto::getDescription).containsOnly(taskDto.getDescription());
    }

    @Test
    @WithMockUser("user7")
    public void createInvalidTask() throws Exception {
        userSystem.createUserWithCredentialsInDb("user7", "test");
        TaskDto taskDto = new TaskDto(null, null, "1. user7 task", false, new Date(), false);
        String taskDtoString = new ObjectMapper().writeValueAsString(taskDto);

        MvcResult result = mockMvc.perform(post("/api/saveTask").contentType(MediaType.APPLICATION_JSON)
                .content(taskDtoString)).andExpect(status().isBadRequest()).andReturn();

        MvcUtils.checkReturnedErrors(
                result,
                "errors.task.title.required"
        );
    }
    
    @Test
    @WithMockUser("user4")
    public void softDeleteTask() throws Exception {
        User user4 = userSystem.createUserWithCredentialsInDb("user4", "test");
        Task task = taskSystem.createTaskInDb("user4task", "1. user4 task", false, false, new Date(), user4);

        mockMvc.perform(delete("/api/deleteTask/" + task.getId())).andExpect(status().isOk());

        MvcResult result = mockMvc.perform(get("/api/getTasks")).andExpect(status().isOk()).andReturn();

        List<TaskDto> taskDtos = MvcUtils.convertList(result, TaskDto.class);

        assertThat(taskDtos).hasSize(1);
        assertThat(taskDtos).extracting(TaskDto::isDeleted).containsOnly(true);
    }

    @Test
    @WithMockUser("user5")
    public void hardDeleteTask() throws Exception {
        List<TaskDto> taskDtos;
        MvcResult result;

        User user5 = userSystem.createUserWithCredentialsInDb("user5", "test");
        Task task = taskSystem.createTaskInDb("user5task", "1. user5 task", false, false, new Date(), user5);

        mockMvc.perform(delete("/api/deleteTask/" + task.getId())).andExpect(status().isOk());

        result = mockMvc.perform(get("/api/getTasks")).andExpect(status().isOk()).andReturn();

        taskDtos = MvcUtils.convertList(result, TaskDto.class);

        assertThat(taskDtos).hasSize(1);
        assertThat(taskDtos).extracting(TaskDto::isDeleted).containsOnly(true);

        mockMvc.perform(delete("/api/deleteTask/" + task.getId())).andExpect(status().isOk());

        result = mockMvc.perform(get("/api/getTasks")).andExpect(status().isOk()).andReturn();

        taskDtos = MvcUtils.convertList(result, TaskDto.class);

        assertThat(taskDtos).hasSize(0);
    }


}
