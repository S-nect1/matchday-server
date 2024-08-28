package com.example.moim.match.repository;

import com.example.moim.club.entity.Club;
import com.example.moim.match.dto.MatchClubSearchCond;
import com.example.moim.match.dto.MatchSearchCond;
import com.example.moim.match.entity.Gender;
import com.example.moim.match.entity.Match;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

import static com.example.moim.club.entity.QClub.club;
import static com.example.moim.match.entity.MatchStatus.REGISTERED;
import static com.example.moim.match.entity.QMatch.match;
import static org.springframework.util.StringUtils.hasText;

public class MatchRepositoryImpl implements MatchRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MatchRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Match> findBySearchCond(MatchSearchCond matchSearchCond) {
        return queryFactory
                .selectFrom(match)
                .leftJoin(match.homeClub, club).fetchJoin()
                .where(
                        match.matchStatus.eq(REGISTERED),
                        searchContains(matchSearchCond.getSearch()),
                        matchDateEq(matchSearchCond.getMatchDate()),
//                        teamAbilityEq(matchSearchCond.getTeamAbility()), // 팀능력 어디서 입력?
                        ageRangeEq(matchSearchCond.getAgeRange()),
                        areaEq(matchSearchCond.getArea()),
                        genderEq(matchSearchCond.getGender()),
                        matchTypeEq(matchSearchCond.getMatchType())
                )
                .orderBy(match.startTime.asc())
                .fetch();
    }

    @Override
    public List<Club> findClubsBySearchCond(MatchClubSearchCond matchClubSearchCond, Club matchClub) {
        return queryFactory
                .selectFrom(club)
                .where(
                        club.activityArea.eq(matchClub.getActivityArea()),
                        searchContains(matchClubSearchCond.getSearch()),
                        ageRangeEq(matchClubSearchCond.getAgeRange()),
                        genderEq(matchClubSearchCond.getGender()),
                        matchTypeEq(matchClubSearchCond.getMatchType())
                )
                .fetch();
    }

    private BooleanExpression searchContains(String search) {
        if (hasText(search)) {
            return match.event.contains(search)
                    .or(match.name.contains(search))
                    .or(match.location.contains(search))
                    .or(match.fee.stringValue().contains(search))
                    .or(match.note.contains(search));
        }

        return null;
    }

    private BooleanExpression matchDateEq(LocalDate matchDate) {
        return matchDate != null ? Expressions.dateTemplate(LocalDate.class,"DATE({0})", match.startTime).eq(matchDate) : null;
    }

    private BooleanExpression matchTypeEq(String matchType) {
        return matchType != null ? match.event.eq(matchType) : null;
    }

    private BooleanExpression genderEq(String gender) {
        return gender != null ? match.gender.eq(Gender.valueOf(gender)) : null;
    }

    private BooleanExpression areaEq(String area) {
        return area != null ? match.location.eq(area) : null;
    }

    private BooleanExpression ageRangeEq(String ageRange) {
        return ageRange != null ? club.ageRange.eq(ageRange) : null;
    }

//    private BooleanExpression teamAbilityEq(String teamAbility) {
//        return teamAbility != null ? club.ability : null;
//    }
}
