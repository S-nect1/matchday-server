package com.example.moim.match.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatchUser is a Querydsl query type for MatchUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatchUser extends EntityPathBase<MatchUser> {

    private static final long serialVersionUID = 778660869L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMatchUser matchUser = new QMatchUser("matchUser");

    public final com.example.moim.club.entity.QClub club;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMatch match;

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final com.example.moim.user.entity.QUser user;

    public QMatchUser(String variable) {
        this(MatchUser.class, forVariable(variable), INITS);
    }

    public QMatchUser(Path<? extends MatchUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMatchUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMatchUser(PathMetadata metadata, PathInits inits) {
        this(MatchUser.class, metadata, inits);
    }

    public QMatchUser(Class<? extends MatchUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new com.example.moim.club.entity.QClub(forProperty("club")) : null;
        this.match = inits.isInitialized("match") ? new QMatch(forProperty("match"), inits.get("match")) : null;
        this.user = inits.isInitialized("user") ? new com.example.moim.user.entity.QUser(forProperty("user")) : null;
    }

}

