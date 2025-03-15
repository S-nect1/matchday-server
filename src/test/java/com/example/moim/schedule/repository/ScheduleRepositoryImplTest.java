package com.example.moim.schedule.repository;

import com.example.moim.club.entity.Club;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.schedule.entity.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@SqlGroup({
        @Sql(value = "/sql/comment-repository-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS ),
        @Sql(value = "/sql/comment-repository-test-data-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
})
class ScheduleRepositoryImplTest {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ClubRepository clubRepository;

    @Test
    @DisplayName("동아리 별로 시작 날짜와 끝 날짜를 지정해서 스케줄을 조회할 수 있다")
    void findByClubAndTime() {
        //given
        Club club = clubRepository.findById(3L).get();
        LocalDateTime startTime = LocalDateTime.of(2024,3,10,9, 0,0);
        LocalDateTime endTime = LocalDateTime.now();
        String search = "title";
        String category = "category";
        //when
        List<Schedule> result = scheduleRepository.findByClubAndTime(club, startTime, endTime, search, category);
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);
    }

    @Test
    void findScheduleById() {
        //given
        Long scheduleId = 1L;
        //when
        Schedule result = scheduleRepository.findScheduleById(scheduleId);
        //then
        assertThat(result.getTitle()).isEqualTo("운동 매치 스케줄");
        assertThat(result.getComment().get(0).getContents()).isEqualTo("회사 면접이 잡혀있어서 못 갑니다");
    }
}