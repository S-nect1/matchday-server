package com.example.moim.club.entity;

import com.example.moim.club.dto.ClubInput;
import com.example.moim.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Club extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String explanation;
    private String profileImgPath;
    private String backgroundImgPath;
    
    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<UserClub> userClub = new ArrayList<>();

    public static Club createClub(ClubInput clubInput, String profileImgPath, String backImgPath) {
        Club club = new Club();
        club.title = clubInput.getTitle();
        club.explanation = clubInput.getExplanation();
        club.profileImgPath = profileImgPath;
        club.backgroundImgPath = backImgPath;
        return club;
    }

    public void changeProfileImg(String newImgPath) {
        this.profileImgPath = newImgPath;
    }

    public void changeBackgroundImg(String newImgPath) {
        this.backgroundImgPath = newImgPath;
    }
}
