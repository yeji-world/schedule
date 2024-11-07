package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {

        //요청받은 데이터로 메모 객체 생성 식별자 없음
        Schedule schedule = new Schedule(dto.getTitle(), dto.getContent());

        //DB 저장
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> readAllSchedules() {

        return scheduleRepository.readAllSchedules();
    }

    @Override
    public ScheduleResponseDto readScheduleById(Long id) {

        Schedule schedule = scheduleRepository.readScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateScheduleById(Long id, String title, String content) {

        if(title == null || content == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and content are required values.");
        }

       int updatedRow = scheduleRepository.updateSchedule(id, title, content);

       if (updatedRow == 0) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
       }

        Schedule schedule = scheduleRepository.readScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String title, String content) {

        if(title == null || content != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title is required value.");
        }

        int updatedRow = scheduleRepository.updateSchedule2(id, title);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Schedule schedule = scheduleRepository.readScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);

    }

    @Override
    public void deleteSchedule(Long id) {

        int deletedRow = scheduleRepository.deleteSchedule(id);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}
