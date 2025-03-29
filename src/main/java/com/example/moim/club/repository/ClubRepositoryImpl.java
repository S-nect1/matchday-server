package com.example.moim.club.repository;

import com.example.moim.club.dto.request.ClubSearchCond;
import com.example.moim.club.entity.*;
import com.example.moim.global.enums.*;
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
                .where(clubCategoryEq(clubSearchCond.getClubCategory()), searchContains(clubSearchCond.getSearch()), universityEq(clubSearchCond.getOrganization()),
                        genderEq(clubSearchCond.getGender()), activityAreaEq(clubSearchCond.getActivityArea()), ageRangeEq(clubSearchCond.getAgeRange()),
                        sportCategoryEq(clubSearchCond.getSportsType()))
                .fetch();
    }

    private BooleanExpression clubCategoryEq(String clubCategory) {
        if (clubCategory != null) {
            return club.clubCategory.eq(ClubCategory.fromKoreanName(clubCategory));
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

    private BooleanExpression universityEq(String organization) {
        if (hasText(organization)) {
            return club.university.eq(organization);
        }
        return null;
    }

    private BooleanExpression genderEq(String gender) {
        if (gender != null) {
            return club.gender.eq(Gender.fromKoreanName(gender));
        }
        return null;
    }

    private BooleanExpression activityAreaEq(String activityArea) {
        if (activityArea != null) {
            return club.activityArea.eq(ActivityArea.fromKoreanName(activityArea));
        }
        return null;
    }

    private BooleanExpression ageRangeEq(String ageRange) {
        if (ageRange != null) {
            return club.ageRange.eq(AgeRange.fromKoreanName(ageRange));
        }
        return null;
    }

    private BooleanExpression sportCategoryEq(String sportsType) {
        if (sportsType != null) {
            return club.sportsType.eq(SportsType.fromKoreanName(sportsType));
        }
        return null;
    }
}
