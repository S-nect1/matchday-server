package com.example.moim.match.repository;

import com.example.moim.match.dto.MatchSearchCond;
import com.example.moim.match.entity.Gender;
import com.example.moim.match.entity.Match;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.example.moim.club.entity.QClub.club;
import static com.example.moim.match.entity.QMatch.match;

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
                        searchLike(matchSearchCond.getSearch()),
//                        teamAbilityEq(matchSearchCond.getTeamAbility()), // 팀능력 어디서 입력?
                        ageRangeEq(matchSearchCond.getAgeRange()),
                        areaEq(matchSearchCond.getArea()),
                        genderEq(matchSearchCond.getGender()),
                        matchTypeEq(matchSearchCond.getMatchType())
                )
                .fetch();
    }

    private BooleanExpression searchLike(String search) {
        if (search == null || search.isEmpty()) {
            return null;
        }

        String formattedSearch = "%" + search.toLowerCase() + "%";

        return match.event.likeIgnoreCase(formattedSearch)
                .or(match.matchSize.likeIgnoreCase(formattedSearch))
                .or(match.location.likeIgnoreCase(formattedSearch))
                .or(match.fee.stringValue().likeIgnoreCase(formattedSearch))
                .or(match.account.likeIgnoreCase(formattedSearch))
                .or(match.minParticipants.stringValue().likeIgnoreCase(formattedSearch));
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
