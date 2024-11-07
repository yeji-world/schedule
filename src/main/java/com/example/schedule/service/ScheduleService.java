package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    List<ScheduleResponseDto> readAllSchedules();

    ScheduleResponseDto readScheduleById(Long id);

    ScheduleResponseDto updateScheduleById(Long id, String title, String content);

    ScheduleResponseDto updateSchedule(Long id, String title, String content);

    void deleteSchedule(Long id);
}
