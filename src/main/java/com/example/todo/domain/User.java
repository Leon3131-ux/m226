package com.example.todo.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User extends AbstractEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

}
