package com.example.moim.club.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAward is a Querydsl query type for Award
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAward extends EntityPathBase<Award> {

    private static final long serialVersionUID = -626252781L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAward award = new QAward("award");

    public final com.example.moim.global.entity.QBaseEntity _super = new com.example.moim.global.entity.QBaseEntity(this);

    public final QClub club;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgPath = createString("imgPath");

    public final NumberPath<Integer> priority = createNumber("priority", Integer.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QAward(String variable) {
        this(Award.class, forVariable(variable), INITS);
    }

    public QAward(Path<? extends Award> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAward(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAward(PathMetadata metadata, PathInits inits) {
        this(Award.class, metadata, inits);
    }

    public QAward(Class<? extends Award> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new QClub(forProperty("club")) : null;
    }

}

