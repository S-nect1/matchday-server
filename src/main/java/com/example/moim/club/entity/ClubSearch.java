package com.example.moim.club.entity;

import com.example.moim.global.entity.BaseEntity;
import com.example.moim.global.util.TextUtils;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ClubSearch extends BaseEntity {
    @Id
    private Long id;
    @OneToOne
    @MapsId  // Club의 PK를 FK로도 사용
    @JoinColumn(name = "club_id")
    private Club club;
    private String titleNoSpace;
    private String introNoSpace;
    private String expNoSpace;
    private String allFieldsConcat;

    @Builder
    public ClubSearch(Club club, String titleNoSpace, String introNoSpace, String expNoSpace, String allFieldsConcat) {
        this.club = club;
        this.titleNoSpace = titleNoSpace;
        this.introNoSpace = introNoSpace;
        this.expNoSpace = expNoSpace;
        this.allFieldsConcat = allFieldsConcat;
    }

    public void updateFrom(Club club) {
        this.titleNoSpace = TextUtils.clean(club.getTitle());
        this.introNoSpace = TextUtils.clean(club.getIntroduction());
        this.expNoSpace = TextUtils.clean(club.getExplanation());
        this.allFieldsConcat = TextUtils.concatClean(
                "|", club.getTitle(), club.getIntroduction(), club.getExplanation()
        );
    }
}
