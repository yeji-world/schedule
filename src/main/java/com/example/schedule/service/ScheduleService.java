package com.example.schedule.service;

import com.example.schedule.dto.FindAllResponseDto;
import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    // 일정 생성
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    // 일정 전체 조회
    List<FindAllResponseDto> findAllSchedules(String updatedDate, String name);

    // 일정 선택 조회
    ScheduleResponseDto findScheduleById(Long id);

    // 일정 선택 수정
    ScheduleResponseDto updateSchedule(Long id, String name, String password, String content);

    // 일정 삭제
    void deleteSchedule(Long id);
}
