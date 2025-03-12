package com.example.moim.club.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserClub is a Querydsl query type for UserClub
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserClub extends EntityPathBase<UserClub> {

    private static final long serialVersionUID = 1996396971L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserClub userClub = new QUserClub("userClub");

    public final com.example.moim.global.entity.QBaseEntity _super = new com.example.moim.global.entity.QBaseEntity(this);

    public final StringPath category = createString("category");

    public final QClub club;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> joinDate = createDate("joinDate", java.time.LocalDate.class);

    public final NumberPath<Integer> matchCount = createNumber("matchCount", Integer.class);

    public final StringPath position = createString("position");

    public final NumberPath<Integer> scheduleCount = createNumber("scheduleCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final com.example.moim.user.entity.QUser user;

    public QUserClub(String variable) {
        this(UserClub.class, forVariable(variable), INITS);
    }

    public QUserClub(Path<? extends UserClub> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserClub(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserClub(PathMetadata metadata, PathInits inits) {
        this(UserClub.class, metadata, inits);
    }

    public QUserClub(Class<? extends UserClub> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new QClub(forProperty("club")) : null;
        this.user = inits.isInitialized("user") ? new com.example.moim.user.entity.QUser(forProperty("user")) : null;
    }

}

