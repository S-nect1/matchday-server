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
    private String ageRange;
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
        club.ageRange = clubInput.getAgeRange();
        club.clubPassword = clubInput.getClubPassword();
        club.profileImgPath = profileImgPath;
        club.memberCount = 1;
        return club;
    }

    public void changeProfileImg(String newImgPath) {
        this.profileImgPath = newImgPath;
    }

    public void plusMemberCount() {
        memberCount++;
    }

    public void updateClub(ClubUpdateInput clubUpdateInput, String profileImgPath) {
        if (clubUpdateInput.getTitle() != null) {
            this.title = clubUpdateInput.getTitle();
        }
        if (clubUpdateInput.getExplanation() != null) {
            this.explanation = clubUpdateInput.getExplanation();
        }
        if (clubUpdateInput.getIntroduction() != null) {
            this.introduction = clubUpdateInput.getIntroduction();
        }
        if (clubUpdateInput.getCategory() != null) {
            this.category = clubUpdateInput.getCategory();
        }
        if (clubUpdateInput.getUniversity() != null) {
            this.university = clubUpdateInput.getUniversity();
        }
        if (clubUpdateInput.getGender() != null) {
            this.gender = clubUpdateInput.getGender();
        }
        if (clubUpdateInput.getActivityArea() != null) {
            this.activityArea = clubUpdateInput.getActivityArea();
        }
        if (clubUpdateInput.getAgeRange() != null) {
            this.ageRange = clubUpdateInput.getAgeRange();
        }
        if (clubUpdateInput.getMainEvent() != null) {
            this.mainEvent = clubUpdateInput.getMainEvent();
        }
        if (profileImgPath != null) {
            this.profileImgPath = profileImgPath;
        }
    }

    public void updateClubPassword(String newPassword) {
        this.clubPassword = newPassword;
    }
}
