package com.example.todo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@MappedSuperclass
@EqualsAndHashCode
@Data
public class AbstractEntity {

    @Id
    @JsonIgnore
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    AbstractEntity() {
        this.id = null;
    }

    AbstractEntity(Integer id) {
        this.id = id;
    }

}
