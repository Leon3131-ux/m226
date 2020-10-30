package com.example.todo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;

@AllArgsConstructor
@MappedSuperclass
@EqualsAndHashCode
@Data
public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public AbstractEntity(){
        this.id = null;
    }

    public AbstractEntity(long id){
        this.id = id;
    }

}
