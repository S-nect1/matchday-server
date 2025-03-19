package com.example.moim.club.repository;

import com.example.moim.club.dto.request.ClubSearchCond;
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

    /**
     * FIXME : 지금은 조건을 and 로 해서 조회하는데, or 로 바꾸는게 적합해보임
     * @param clubSearchCond
     * @return
     */
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

    /**
     * FIXME : search 로 내용도 같이 확인해주는게 좋을듯. 그리고 contains 는 띄어쓰기 하면 완전 다른 결과가 나오므로 그것에 대한 해결책도 생각하기
     * @param search
     * @return
     */
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
