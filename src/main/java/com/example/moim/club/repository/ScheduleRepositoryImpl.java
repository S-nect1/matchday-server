package com.example.moim.club.repository;

import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.Schedule;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.moim.club.entity.QSchedule.*;

public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ScheduleRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Schedule> findByClubAndTime(Club club, LocalDateTime startTime, LocalDateTime endTime) {
        return queryFactory
                .selectFrom(schedule)
                .orderBy(schedule.startTime.asc())
                .where(schedule.club.eq(club), schedule.startTime.goe(startTime), schedule.endTime.loe(endTime))
                .fetch();
    }
}
