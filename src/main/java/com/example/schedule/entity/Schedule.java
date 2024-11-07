package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id; //int보다 훨씬 크고, wrapper클래스라서 null포함
    private String title;
    private String content;

    public Schedule(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update1(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
