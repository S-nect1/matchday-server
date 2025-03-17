package com.example.moim.schedule.repository;

import com.example.moim.schedule.entity.Comment;
import com.example.moim.schedule.entity.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@SqlGroup({
        @Sql(value = "/sql/comment-repository-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS ),
        @Sql(value = "/sql/comment-repository-test-data-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
})
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Test
    @DisplayName("스케줄에 달린 댓글을 모두 조회할 수 있다")
    void findBySchedule() {
        //given
        Schedule schedule = scheduleRepository.findById(1L).get();
        //when
        List<Comment> result = commentRepository.findBySchedule(schedule);
        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getContents()).contains("회사 면접이 잡혀");
        assertThat(result.get(0).getUser().getId()).isEqualTo(3L);
    }

    @Test
    @DisplayName("스케줄에 달린 댓글이 없으면 빈 리스트를 반환한다")
    void findBySchedule_zero_comment() {
        //given
        Schedule schedule = scheduleRepository.findById(2L).get();
        //when
        List<Comment> result = commentRepository.findBySchedule(schedule);
        //then
        assertThat(result.size()).isEqualTo(0);
    }
}