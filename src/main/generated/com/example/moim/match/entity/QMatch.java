package com.example.moim.match.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatch is a Querydsl query type for Match
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatch extends EntityPathBase<Match> {

    private static final long serialVersionUID = -896587494L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMatch match = new QMatch("match");

    public final com.example.moim.global.entity.QBaseEntity _super = new com.example.moim.global.entity.QBaseEntity(this);

    public final StringPath account = createString("account");

    public final StringPath ageRange = createString("ageRange");

    public final com.example.moim.club.entity.QClub awayClub;

    public final NumberPath<Integer> awayScore = createNumber("awayScore", Integer.class);

    public final StringPath bank = createString("bank");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final DateTimePath<java.time.LocalDateTime> endTime = createDateTime("endTime", java.time.LocalDateTime.class);

    public final StringPath event = createString("event");

    public final NumberPath<Integer> fee = createNumber("fee", Integer.class);

    public final EnumPath<Gender> gender = createEnum("gender", Gender.class);

    public final com.example.moim.club.entity.QClub homeClub;

    public final NumberPath<Integer> homeScore = createNumber("homeScore", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isBall = createBoolean("isBall");

    public final StringPath location = createString("location");

    public final EnumPath<MatchHalf> matchHalf = createEnum("matchHalf", MatchHalf.class);

    public final StringPath matchSize = createString("matchSize");

    public final EnumPath<MatchStatus> matchStatus = createEnum("matchStatus", MatchStatus.class);

    public final NumberPath<Integer> minParticipants = createNumber("minParticipants", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath note = createString("note");

    public final com.example.moim.schedule.entity.QSchedule schedule;

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QMatch(String variable) {
        this(Match.class, forVariable(variable), INITS);
    }

    public QMatch(Path<? extends Match> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMatch(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMatch(PathMetadata metadata, PathInits inits) {
        this(Match.class, metadata, inits);
    }

    public QMatch(Class<? extends Match> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.awayClub = inits.isInitialized("awayClub") ? new com.example.moim.club.entity.QClub(forProperty("awayClub")) : null;
        this.homeClub = inits.isInitialized("homeClub") ? new com.example.moim.club.entity.QClub(forProperty("homeClub")) : null;
        this.schedule = inits.isInitialized("schedule") ? new com.example.moim.schedule.entity.QSchedule(forProperty("schedule"), inits.get("schedule")) : null;
    }

}

