package com.example.schedule.service;

import com.example.schedule.dto.FindAllResponseDto;
import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;


    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 일정 생성
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {

        //요청받은 데이터로 메모 객체 생성 식별자 없음
        Schedule schedule = new Schedule(dto.getName(), dto.getPassword(), dto.getContent());

        //DB 저장
        return scheduleRepository.saveSchedule(schedule);
    }

    // 일정 전체 조회
    @Override
    public List<FindAllResponseDto> findAllSchedules(String updatedDate, String name) {

        return scheduleRepository.findAllSchedules(updatedDate, name);
    }

    // 일정 선택 조회
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);
    }

    // 일정 선택 수정
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String name, String password, String content) {

        LocalDateTime updatedDate = LocalDateTime.now();

        if(name == null || content == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The name and content are required values.");
        }

       int updatedRow = scheduleRepository.updateSchedule(id, name, content, updatedDate);

       if (updatedRow == 0) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
       }

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        if(!password.equals(schedule.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user");
        }

        return new ScheduleResponseDto(schedule);
    }

    // 일정 선택 삭제
    @Override
    public void deleteSchedule(Long id) {

        int deletedRow = scheduleRepository.deleteSchedule(id);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}
