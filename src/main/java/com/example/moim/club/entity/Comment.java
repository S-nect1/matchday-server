package com.example.moim.club.entity;

import com.example.moim.global.entity.BaseEntity;
import com.example.moim.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Schedule schedule;
    private String contents;

    public static Comment createComment(User user, Schedule schedule, String contents) {
        Comment comment = new Comment();
        comment.user = user;
        comment.schedule = schedule;
        comment.contents = contents;
        return comment;
    }
}
