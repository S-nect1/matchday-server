package com.example.moim.schedule.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScheduleVote is a Querydsl query type for ScheduleVote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScheduleVote extends EntityPathBase<ScheduleVote> {

    private static final long serialVersionUID = -1942522996L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScheduleVote scheduleVote = new QScheduleVote("scheduleVote");

    public final com.example.moim.global.entity.QBaseEntity _super = new com.example.moim.global.entity.QBaseEntity(this);

    public final StringPath attendance = createString("attendance");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSchedule schedule;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final com.example.moim.user.entity.QUser user;

    public QScheduleVote(String variable) {
        this(ScheduleVote.class, forVariable(variable), INITS);
    }

    public QScheduleVote(Path<? extends ScheduleVote> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScheduleVote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScheduleVote(PathMetadata metadata, PathInits inits) {
        this(ScheduleVote.class, metadata, inits);
    }

    public QScheduleVote(Class<? extends ScheduleVote> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.schedule = inits.isInitialized("schedule") ? new QSchedule(forProperty("schedule"), inits.get("schedule")) : null;
        this.user = inits.isInitialized("user") ? new com.example.moim.user.entity.QUser(forProperty("user")) : null;
    }

}

