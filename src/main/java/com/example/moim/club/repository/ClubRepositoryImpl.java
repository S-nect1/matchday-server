package com.example.moim.club.repository;

import com.example.moim.club.dto.ClubSearchCond;
import com.example.moim.club.entity.Club;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.example.moim.club.entity.QClub.club;
import static org.springframework.util.StringUtils.hasText;

public class ClubRepositoryImpl implements ClubRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ClubRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Club> findBySearchCond(ClubSearchCond clubSearchCond) {
        return queryFactory
                .selectFrom(club)
                .where(categoryEq(clubSearchCond.getCategory()), searchContains(clubSearchCond.getSearch()), universityEq(clubSearchCond.getUniversity()),
                        genderEq(clubSearchCond.getGender()), activityAreaEq(clubSearchCond.getActivityArea()), ageRangeEq(clubSearchCond.getAgeRange()),
                        mainEventEq(clubSearchCond.getMainEvent()))
                .fetch();
    }

    private BooleanExpression categoryEq(String category) {
        if (hasText(category)) {
            return club.category.eq(category);
        }
        return null;
    }

    private BooleanExpression searchContains(String search) {
        if (hasText(search)) {
            return club.title.contains(search);
        }
        return null;
    }

    private BooleanExpression universityEq(String university) {
        if (hasText(university)) {
            return club.university.eq(university);
        }
        return null;
    }

    private BooleanExpression genderEq(String gender) {
        if (hasText(gender)) {
            return club.gender.eq(gender);
        }
        return null;
    }

    private BooleanExpression activityAreaEq(String activityArea) {
        if (hasText(activityArea)) {
            return club.activityArea.eq(activityArea);
        }
        return null;
    }

    private BooleanExpression ageRangeEq(String ageRange) {
        if (hasText(ageRange)) {
            return club.ageRange.eq(ageRange);
        }
        return null;
    }

    private BooleanExpression mainEventEq(String mainEvent) {
        if (hasText(mainEvent)) {
            return club.mainEvent.eq(mainEvent);
        }
        return null;
    }
}
