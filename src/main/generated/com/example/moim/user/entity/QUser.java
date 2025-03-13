package com.example.moim.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -803765526L;

    public static final QUser user = new QUser("user");

    public final com.example.moim.global.entity.QBaseEntity _super = new com.example.moim.global.entity.QBaseEntity(this);

    public final StringPath activityArea = createString("activityArea");

    public final StringPath birthday = createString("birthday");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    public final StringPath fcmToken = createString("fcmToken");

    public final EnumPath<Gender> gender = createEnum("gender", Gender.class);

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgPath = createString("imgPath");

    public final StringPath mainFoot = createString("mainFoot");

    public final StringPath mainPosition = createString("mainPosition");

    public final StringPath name = createString("name");

    public final ListPath<com.example.moim.notification.entity.Notifications, com.example.moim.notification.entity.QNotifications> notifications = this.<com.example.moim.notification.entity.Notifications, com.example.moim.notification.entity.QNotifications>createList("notifications", com.example.moim.notification.entity.Notifications.class, com.example.moim.notification.entity.QNotifications.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath refreshToken = createString("refreshToken");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath subPosition = createString("subPosition");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final ListPath<com.example.moim.club.entity.UserClub, com.example.moim.club.entity.QUserClub> userClub = this.<com.example.moim.club.entity.UserClub, com.example.moim.club.entity.QUserClub>createList("userClub", com.example.moim.club.entity.UserClub.class, com.example.moim.club.entity.QUserClub.class, PathInits.DIRECT2);

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

