package com.example.todo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Task extends AbstractEntity{

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean done;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private boolean deleted;

    @ManyToOne
    private User user;

}
