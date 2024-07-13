package com.example.moim.club.entity;

import com.example.moim.club.dto.ClubInput;
import com.example.moim.club.dto.ClubUpdateInput;
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
    @Column(length = 500)
    private String introduction;
    private String category;
    private String university;
    private String gender;
    private String activityArea;
    private String mainEvent;
    private String clubPassword;
    private String profileImgPath;
    private int memberCount;
    
    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<UserClub> userClub = new ArrayList<>();

    public static Club createClub(ClubInput clubInput, String profileImgPath) {
        Club club = new Club();
        club.title = clubInput.getTitle();
        club.explanation = clubInput.getExplanation();
        club.introduction = clubInput.getIntroduction();
        club.category = clubInput.getCategory();
        club.university = clubInput.getUniversity();
        club.gender = clubInput.getGender();
        club.activityArea = clubInput.getActivityArea();
        club.mainEvent = clubInput.getMainEvent();
        club.clubPassword = clubInput.getClubPassword();
        club.profileImgPath = profileImgPath;
        return club;
    }

    public void changeProfileImg(String newImgPath) {
        this.profileImgPath = newImgPath;
    }

//    public void UpdateClub(ClubUpdateInput clubUpdateInput, String profileImgPath) {
//        this.title = clubUpdateInput.getTitle();
//        this.explanation = clubUpdateInput.getExplanation();
//        this.introduction = clubUpdateInput.getIntroduction();
//        this.category = clubInput.getCategory();
//        this.university = clubInput.getUniversity();
//        this.gender = clubInput.getGender();
//        this.activityArea = clubInput.getActivityArea();
//        this.mainEvent = clubInput.getMainEvent();
//        this.clubPassword = clubInput.getClubPassword();
//        this.profileImgPath = profileImgPath;
//    }
}
