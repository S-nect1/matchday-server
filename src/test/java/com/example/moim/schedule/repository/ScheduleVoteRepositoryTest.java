package com.example.moim.schedule.repository;

import com.example.moim.schedule.entity.Schedule;
import com.example.moim.schedule.entity.ScheduleVote;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@SqlGroup({
        @Sql(value = "/sql/schedule-vote-repository-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS),
        @Sql(value = "/sql/schedule-vote-repository-test-data-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
})
class ScheduleVoteRepositoryTest {

    @Autowired
    private ScheduleVoteRepository scheduleVoteRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    @DisplayName("스케줄 객체로 투표 현황을 조회할 수 있다")
    void findBySchedule() {
        //given
        Schedule schedule = scheduleRepository.findScheduleById(1L);
        //when
        List<ScheduleVote> result = scheduleVoteRepository.findBySchedule(schedule);
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getUser().getId()).isEqualTo(3);
        assertThat(result.get(1).getUser().getId()).isEqualTo(4);
    }

    @Test
    @DisplayName("스케줄 아이디로 투표 현황을 조회할 수 있다")
    void findByScheduleId() {
        //given
        Long scheduleId = 1L;
        //when
        List<ScheduleVote> result = scheduleVoteRepository.findByScheduleId(scheduleId);
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getUser().getId()).isEqualTo(3);
        assertThat(result.get(1).getUser().getId()).isEqualTo(4);
    }
}