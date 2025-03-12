package com.example.moim.schedule.repository;

import com.example.moim.club.entity.Club;
import com.example.moim.schedule.entity.Schedule;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;
import static org.springframework.util.StringUtils.hasText;

import static com.example.moim.club.entity.QClub.*;
import static com.example.moim.schedule.entity.QSchedule.*;

public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ScheduleRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Schedule> findByClubAndTime(Club club, LocalDateTime startTime, LocalDateTime endTime, String search, String category) {
        return queryFactory
                .selectFrom(schedule)
                .orderBy(schedule.startTime.asc())
                .where(schedule.club.eq(club), schedule.startTime.goe(startTime), schedule.endTime.loe(endTime))
                .fetch();
    }

    private BooleanExpression searchContains(String search) {
        if (hasText(search)) {
            return schedule.title.contains(search);
        }
        return null;
    }

    private BooleanExpression categoryContains(String category) {
        if (hasText(category)) {
            return schedule.category.contains(category);
        }
        return null;
    }

    @Override
    public Schedule findScheduleById(Long id) {
        return queryFactory
                .selectFrom(schedule)
                .join(schedule.club, club).fetchJoin()
                .where(schedule.id.eq(id))
                .fetchOne();
    }
}
