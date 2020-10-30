package com.example.todo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class TaskDto {

    private Long id;

    private String title;

    private String description;

    private boolean done;

    private Date date;

    private boolean deleted;

}
