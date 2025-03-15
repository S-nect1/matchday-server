package com.example.moim.schedule.repository;

import com.example.moim.schedule.entity.Schedule;
import com.example.moim.schedule.entity.ScheduleVote;
import com.example.moim.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleVoteRepository extends JpaRepository<ScheduleVote, Long> {
    Optional<ScheduleVote> findByScheduleAndUser(Schedule schedule, User user);

    @Query("select sv from ScheduleVote sv" +
            " join fetch sv.user u" +
            " where sv.schedule = :schedule")
    List<ScheduleVote> findBySchedule(@Param("schedule") Schedule schedule);

    @Query("select sv from ScheduleVote sv" +
            " join fetch sv.user u" +
            " where sv.schedule.id = :id")
    List<ScheduleVote> findByScheduleId(@Param("id") Long id);
}
