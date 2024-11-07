package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> readAllSchedules();

    Optional<Schedule> readScheduleById(Long id);

    Schedule readScheduleByIdOrElseThrow(Long id);

    int updateSchedule(Long id, String title, String content);

    int updateSchedule2(Long id, String title);

    int deleteSchedule(Long id);
}
