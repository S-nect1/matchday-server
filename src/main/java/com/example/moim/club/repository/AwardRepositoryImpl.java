package com.example.moim.club.repository;

import com.example.moim.club.entity.Award;
import com.example.moim.club.entity.Club;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.example.moim.club.entity.QAward.award;

public class AwardRepositoryImpl implements AwardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public AwardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Award> findByClubOrderBy(Club club, String order) {
        return queryFactory
                .selectFrom(award)
                .orderBy(orderEq(order))
                .where(award.club.eq(club))
                .fetch();
    }

    private OrderSpecifier<?> orderEq(String order) {
        if (order.equals("created")) {
            return award.createdDate.desc();
        }
        return award.priority.asc();
    }
}
