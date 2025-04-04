package com.example.moim.club.repository;

import com.example.moim.club.dto.request.ClubSearchCond;
import com.example.moim.club.entity.*;
import com.example.moim.global.enums.*;
import com.example.moim.global.util.TextUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.moim.club.entity.QClub.club;
import static com.example.moim.club.entity.QClubSearch.clubSearch;
import static org.springframework.util.StringUtils.hasText;

public class ClubRepositoryImpl implements ClubRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ClubRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Club> findBySearchCond(ClubSearchCond clubSearchCond) {
        // 1차: OR 조건만으로 먼저 조회
        BooleanExpression fallbackSearchCond = safeOrExpressions(
                searchContains(clubSearchCond.getSearch()),
                universityContains(clubSearchCond.getUniversity())
        );

        List<Club> fallbackResult = queryFactory
                .selectFrom(club)
                .where(fallbackSearchCond)
                .fetch();

        BooleanBuilder builder = new BooleanBuilder();

        // fallback: search 조건 무시하고 나머지 조건만으로 조회
        builder.and(clubCategoryEq(clubSearchCond.getClubCategory()));
        builder.and(genderEq(clubSearchCond.getGender()));
        builder.and(activityAreaEq(clubSearchCond.getActivityArea()));
        builder.and(ageRangeEq(clubSearchCond.getAgeRange()));
        builder.and(sportCategoryEq(clubSearchCond.getSportsType()));

        if (!fallbackResult.isEmpty()) {
            // 2차: OR + AND 조건 모두 적용
            builder.and(fallbackSearchCond);
        }

        return queryFactory
                .selectFrom(club)
                .join(club.clubSearch, clubSearch)
                .where(builder)
                .fetch();
    }

    private BooleanExpression clubCategoryEq(String clubCategory) {
        if (clubCategory == null) return null;

        return ClubCategory.fromKoreanName(clubCategory)
                .map(club.clubCategory::eq)
                .orElse(null);
    }

    private BooleanExpression searchContains(String search) {
        if (!hasText(search)) return null;

        return clubSearch.allFieldsConcat.contains(TextUtils.clean(search));
    }

    /**
     * INFO : university 는 '학교이름@좌표' 이런 식으로 저장되니까 contain 으로 조회
     */
    private BooleanExpression universityContains(String university) {
        if (!hasText(university)) return null;

        return club.university.contains(university);
    }

    private BooleanExpression genderEq(String gender) {
        if (gender == null) return null;

        return Gender.fromKoreanName(gender)
                .map(club.gender::eq)
                .orElse(null);
    }

    private BooleanExpression activityAreaEq(String activityArea) {
        if (activityArea == null) return null;

        return ActivityArea.fromKoreanName(activityArea)
                .map(club.activityArea::eq)
                .orElse(null);
    }

    private BooleanExpression ageRangeEq(String ageRange) {
        if (ageRange == null) return null;

        return AgeRange.fromKoreanName(ageRange)
                .map(club.ageRange::eq)
                .orElse(null);
    }

    private BooleanExpression sportCategoryEq(String sportsType) {
        if (sportsType == null) return null;

        return SportsType.fromKoreanName(sportsType)
                .map(club.sportsType::eq)
                .orElse(null);
    }

    /**
     * INFO : 조건이 하나라도 있으면, 포함되게 하고 아예 없으면 무조건 TRUE 로 반환하여 다른 조건으로 조회가 가능하도록 함
     */
    public BooleanExpression safeOrExpressions(BooleanExpression... expressions) {
        return Arrays.stream(expressions)
                .filter(Objects::nonNull)
                .reduce(BooleanExpression::or)
                .orElse(Expressions.TRUE);
    }

}
