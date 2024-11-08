package com.example.schedule.service;

import com.example.schedule.dto.FindAllResponseDto;
import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    List<FindAllResponseDto> findAllSchedules(String updatedDate, String name);

    ScheduleResponseDto findScheduleById(Long id);

    ScheduleResponseDto updateSchedule(Long id, String name, String password, String content);

//    ScheduleResponseDto updateSchedule(Long id, String name, String password, String content);

    void deleteSchedule(Long id);
}
