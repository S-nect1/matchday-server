package com.example.moim.club.entity;

import com.example.moim.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Club club;
    private String title;
    @Column(length = 500)
    private String content;

    public static Notice createNotice(Club club, String title, String content) {
        Notice notice = new Notice();
        notice.club = club;
        notice.title = title;
        notice.content = content;
        return notice;
    }
}
