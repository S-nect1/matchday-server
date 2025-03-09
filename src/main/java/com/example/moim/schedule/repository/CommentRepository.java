package com.example.moim.schedule.repository;

import com.example.moim.schedule.entity.Comment;
import com.example.moim.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c" +
            " join fetch c.user u" +
            " where c.schedule = :schedule")
    List<Comment> findBySchedule(@Param("schedule") Schedule schedule);
}
