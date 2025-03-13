package com.example.moim.match.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatchApplication is a Querydsl query type for MatchApplication
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatchApplication extends EntityPathBase<MatchApplication> {

    private static final long serialVersionUID = 1369812950L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMatchApplication matchApplication = new QMatchApplication("matchApplication");

    public final com.example.moim.club.entity.QClub club;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isBall = createBoolean("isBall");

    public final QMatch match;

    public final StringPath note = createString("note");

    public final com.example.moim.schedule.entity.QSchedule schedule;

    public final EnumPath<MatchAppStatus> status = createEnum("status", MatchAppStatus.class);

    public QMatchApplication(String variable) {
        this(MatchApplication.class, forVariable(variable), INITS);
    }

    public QMatchApplication(Path<? extends MatchApplication> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMatchApplication(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMatchApplication(PathMetadata metadata, PathInits inits) {
        this(MatchApplication.class, metadata, inits);
    }

    public QMatchApplication(Class<? extends MatchApplication> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new com.example.moim.club.entity.QClub(forProperty("club")) : null;
        this.match = inits.isInitialized("match") ? new QMatch(forProperty("match"), inits.get("match")) : null;
        this.schedule = inits.isInitialized("schedule") ? new com.example.moim.schedule.entity.QSchedule(forProperty("schedule"), inits.get("schedule")) : null;
    }

}

