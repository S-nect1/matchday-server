package com.example.moim.club.entity;

import com.example.moim.club.dto.AwardInput;
import com.example.moim.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Award extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Club club;
    private String title;
    private int priority;
    private String imgPath;

    public Award(Club club, AwardInput awardInput) {
        this.club = club;
        this.title = awardInput.getTitle();
        if (awardInput.getPriority() != null) {
            this.priority = awardInput.getPriority();
        }
        this.imgPath = awardInput.getImgPath();
    }

    public void changeAward(AwardInput awardInput) {
        this.imgPath = awardInput.getImgPath();
        this.title = awardInput.getTitle();
        if (awardInput.getPriority() != null) {
            this.priority = awardInput.getPriority();
        }
    }

}
