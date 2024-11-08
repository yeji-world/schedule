package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id; //int보다 훨씬 크고, wrapper클래스라서 null포함
    private String name;
    private String password;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public Schedule(String name, String password, String content) {
        this.name = name;
        this.password = password;
        this.content = content;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    public void update1(String name, String content, LocalDateTime updatedDate) {
        this.name = name;
        this.content = content;
        this.updatedDate = LocalDateTime.now();
    }

    public void update(String name, String content) {
        this.name = name;
        this.content = content;
    }
}
