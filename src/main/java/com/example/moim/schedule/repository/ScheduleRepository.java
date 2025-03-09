package com.example.moim.schedule.repository;

import com.example.moim.club.entity.Club;
import com.example.moim.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {
    List<Schedule> findTop5ByClubOrderByCreatedDateDesc(Club club);

}
