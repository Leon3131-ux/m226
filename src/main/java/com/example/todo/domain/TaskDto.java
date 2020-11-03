package com.example.todo.domain;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private Long id;

    private String title;

    private String description;

    private boolean done;

    private Date date;

    private boolean deleted;

}
