package com.example.schedule.repository;

import com.example.schedule.dto.FindAllResponseDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<FindAllResponseDto> findAllSchedules(String updatedDate, String name);

//    Optional<Schedule> findScheduleById(Long id);

    Schedule findScheduleByIdOrElseThrow(Long id);

//    String validatePasswordById(Long id);

    int updateSchedule(Long id, String name, String content, LocalDateTime updatedDate);

//    int updateSchedule2(Long id, String name);

    int deleteSchedule(Long id);
}
