package com.example.moim.notification.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotifications is a Querydsl query type for Notifications
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotifications extends EntityPathBase<Notifications> {

    private static final long serialVersionUID = -1320817015L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNotifications notifications = new QNotifications("notifications");

    public final com.example.moim.global.entity.QBaseEntity _super = new com.example.moim.global.entity.QBaseEntity(this);

    public final StringPath category = createString("category");

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRead = createBoolean("isRead");

    public final com.example.moim.user.entity.QUser targetUser;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QNotifications(String variable) {
        this(Notifications.class, forVariable(variable), INITS);
    }

    public QNotifications(Path<? extends Notifications> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNotifications(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNotifications(PathMetadata metadata, PathInits inits) {
        this(Notifications.class, metadata, inits);
    }

    public QNotifications(Class<? extends Notifications> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.targetUser = inits.isInitialized("targetUser") ? new com.example.moim.user.entity.QUser(forProperty("targetUser")) : null;
    }

}

