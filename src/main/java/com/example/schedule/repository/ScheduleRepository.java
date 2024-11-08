package com.example.schedule.repository;

import com.example.schedule.dto.FindAllResponseDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {

    // 일정 생성
    ScheduleResponseDto saveSchedule(Schedule schedule);

    // 일정 전체 조회
    List<FindAllResponseDto> findAllSchedules(String updatedDate, String name);

    // 일정 선택 조회
    Schedule findScheduleByIdOrElseThrow(Long id);

    // 일정 선택 수정
    int updateSchedule(Long id, String name, String content, LocalDateTime updatedDate);

    // 일정 선택 삭제
    int deleteSchedule(Long id);
}
