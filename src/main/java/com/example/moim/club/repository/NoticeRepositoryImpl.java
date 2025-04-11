package com.example.moim.club.repository;

import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.Notice;
import com.example.moim.club.entity.QNotice;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Notice> findByCursor(Long cursor, int size, Club club) {
        return queryFactory
                .selectFrom(QNotice.notice)
                .where(
                        ltCursor(cursor),
                        clubEq(club)
                )
                .orderBy(QNotice.notice.id.desc())
                .limit(size + 1) // hasNext 판단용으로 1개 더
                .fetch();
    }

    private BooleanExpression ltCursor(Long cursor) {
        return cursor != null ? QNotice.notice.id.lt(cursor) : null;
    }

    private BooleanExpression clubEq(Club club) {
        return club != null ? QNotice.notice.club.eq(club) : null;
    }
}

