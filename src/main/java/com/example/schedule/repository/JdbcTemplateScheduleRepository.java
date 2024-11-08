package com.example.schedule.repository;

import com.example.schedule.dto.FindAllResponseDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 일정 생성
    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        // INSERT Query 직접 작성 필요 X
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", schedule.getName());
        parameters.put("password", schedule.getPassword());
        parameters.put("content", schedule.getContent());
        parameters.put("createdDate", schedule.getCreatedDate());
        parameters.put("updatedDate", schedule.getUpdatedDate());

        // 저장 후 생성된 key값 -> Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(), schedule.getName(), schedule.getContent(), schedule.getCreatedDate(), schedule.getUpdatedDate());
    }

    // 일정 전체 조회
    @Override
    public List<FindAllResponseDto> findAllSchedules(String updatedDate, String name) {
        if(name==null && updatedDate !=null){
            String sql = "select * from schedule where date(updatedDate) = ? order by updatedDate desc";
            return jdbcTemplate.query(sql, scheduleRowMapper(), updatedDate);
        } else if (name !=null && updatedDate == null){
            String sql = "select * from schedule where name = ? order by updatedDate desc";
            return jdbcTemplate.query(sql, scheduleRowMapper(), name);
        } else if (name ==null && updatedDate == null){
            String sql = "select * from schedule order by updatedDate desc";
            return jdbcTemplate.query(sql, scheduleRowMapper());
        } else if (name != null && updatedDate != null) {
            String sql = "select * from schedule where name = ? and date(updatedDate) = ? order by updatedDate desc";
            return jdbcTemplate.query(sql, scheduleRowMapper(), name, updatedDate);
        }

        return jdbcTemplate.query("select * from schedule order by updatedDate desc", scheduleRowMapper());
    }

    // 일정 선택 조회
    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapperV2(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    // 일정 선택 수정
    @Override
    public int updateSchedule(Long id, String name, String content, LocalDateTime updatedDate) {
        return jdbcTemplate.update("update schedule set name = ?, content = ?, updatedDate = ? where id = ? ", name, content, updatedDate, id);
    }

    // 일정 선택 삭제
    @Override
    public int deleteSchedule(Long id) {
       return jdbcTemplate.update("delete from schedule where id = ?", id);
    }

    private RowMapper<FindAllResponseDto> scheduleRowMapper() {

        return new RowMapper<FindAllResponseDto>() {
            @Override
            public FindAllResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new FindAllResponseDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("content"),
                        rs.getTimestamp("createdDate").toLocalDateTime(),
                        rs.getTimestamp("updatedDate").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("content"),
                        rs.getTimestamp("createdDate").toLocalDateTime(),
                        rs.getTimestamp("updatedDate").toLocalDateTime()
                );
            }
        };
    }
}
