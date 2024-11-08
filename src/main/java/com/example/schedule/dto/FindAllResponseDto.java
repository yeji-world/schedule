package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor

public class FindAllResponseDto {
    // 응답 데이터 처리
    // 일정 전체 조회 시 작성일, 수정일 형식 yyyy-MM-dd

    private Long id;
    private String name;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updatedDate;

    public FindAllResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.name = schedule.getName();
        this.content = schedule.getContent();
        this.createdDate = schedule.getCreatedDate();
        this.updatedDate = schedule.getUpdatedDate();
    }

}
