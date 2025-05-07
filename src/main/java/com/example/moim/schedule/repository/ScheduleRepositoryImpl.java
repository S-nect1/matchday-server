package com.example.moim.schedule.repository;

import com.example.moim.club.entity.Club;
import com.example.moim.schedule.entity.Schedule;
import com.example.moim.schedule.entity.ScheduleCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.moim.schedule.comment.entity.QComment.comment;
import static com.example.moim.schedule.entity.QSchedule.schedule;
import static org.springframework.util.StringUtils.hasText;

import static com.example.moim.club.entity.QClub.club;

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
                .where(schedule.club.eq(club), schedule.startTime.goe(startTime), schedule.endTime.loe(endTime),
                        searchContains(search), categoryEq(category))
                .fetch();
    }

    private BooleanExpression searchContains(String search) {
        if (hasText(search)) {
            return schedule.title.contains(search);
        }
        return null;
    }

    private BooleanExpression categoryEq(String category) {
        Optional<ScheduleCategory> scheduleCategory = ScheduleCategory.fromKoreanName(category);
        if (hasText(category) && scheduleCategory.isPresent()) {
            return schedule.category.eq(scheduleCategory.get());
        }
        return null;
    }

    @Override
    public Schedule findWithClubById(Long id) {
        return queryFactory
                .selectFrom(schedule)
                .join(schedule.club, club).fetchJoin()
                .where(schedule.id.eq(id))
                .fetchOne();
    }

    @Override
    public Schedule findByIdWithComment(Long id) {
        return queryFactory
                .selectFrom(schedule)
                .join(schedule.comments, comment).fetchJoin()
                .where(schedule.id.eq(id))
                .fetchOne();
    }
}
