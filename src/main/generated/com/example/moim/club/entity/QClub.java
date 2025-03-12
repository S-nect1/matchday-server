package com.example.moim.club.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClub is a Querydsl query type for Club
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClub extends EntityPathBase<Club> {

    private static final long serialVersionUID = -1267078080L;

    public static final QClub club = new QClub("club");

    public final com.example.moim.global.entity.QBaseEntity _super = new com.example.moim.global.entity.QBaseEntity(this);

    public final StringPath activityArea = createString("activityArea");

    public final StringPath ageRange = createString("ageRange");

    public final ListPath<com.example.moim.match.entity.Match, com.example.moim.match.entity.QMatch> awayMatches = this.<com.example.moim.match.entity.Match, com.example.moim.match.entity.QMatch>createList("awayMatches", com.example.moim.match.entity.Match.class, com.example.moim.match.entity.QMatch.class, PathInits.DIRECT2);

    public final StringPath category = createString("category");

    public final StringPath clubPassword = createString("clubPassword");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath explanation = createString("explanation");

    public final StringPath gender = createString("gender");

    public final ListPath<com.example.moim.match.entity.Match, com.example.moim.match.entity.QMatch> homeMatches = this.<com.example.moim.match.entity.Match, com.example.moim.match.entity.QMatch>createList("homeMatches", com.example.moim.match.entity.Match.class, com.example.moim.match.entity.QMatch.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduction = createString("introduction");

    public final StringPath mainEvent = createString("mainEvent");

    public final StringPath mainUniformColor = createString("mainUniformColor");

    public final NumberPath<Integer> matchCount = createNumber("matchCount", Integer.class);

    public final NumberPath<Integer> memberCount = createNumber("memberCount", Integer.class);

    public final ListPath<Notice, QNotice> notices = this.<Notice, QNotice>createList("notices", Notice.class, QNotice.class, PathInits.DIRECT2);

    public final StringPath profileImgPath = createString("profileImgPath");

    public final NumberPath<Integer> scheduleCount = createNumber("scheduleCount", Integer.class);

    public final StringPath subUniformColor = createString("subUniformColor");

    public final StringPath title = createString("title");

    public final StringPath university = createString("university");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final ListPath<UserClub, QUserClub> userClub = this.<UserClub, QUserClub>createList("userClub", UserClub.class, QUserClub.class, PathInits.DIRECT2);

    public QClub(String variable) {
        super(Club.class, forVariable(variable));
    }

    public QClub(Path<? extends Club> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClub(PathMetadata metadata) {
        super(Club.class, metadata);
    }

}

