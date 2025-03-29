package com.example.moim.club.entity;

import com.example.moim.club.dto.request.ClubInput;
import com.example.moim.club.dto.request.ClubUpdateInput;
import com.example.moim.global.entity.BaseEntity;
import com.example.moim.global.enums.*;
import com.example.moim.match.entity.Match;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
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
    @Enumerated(value = EnumType.STRING)
    private ClubCategory clubCategory;
    private String university;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    @Enumerated(value = EnumType.STRING)
    private ActivityArea activityArea;
    @Enumerated(value = EnumType.STRING)
    private SportsType sportsType;
    @Enumerated(value = EnumType.STRING)
    private AgeRange ageRange;
    private String clubPassword;
    private String profileImgPath = "기본 이미지 링크";
    private String mainUniformColor;
    private String subUniformColor;

    private Integer memberCount;
    private Integer scheduleCount;
    private Integer matchCount;
    
    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<UserClub> userClub = new ArrayList<>();

    @OneToMany(mappedBy = "homeClub", cascade = CascadeType.REMOVE)
    private List<Match> homeMatches = new ArrayList<>();

    @OneToMany(mappedBy = "awayClub", cascade = CascadeType.REMOVE)
    private List<Match> awayMatches = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<Notice> notices = new ArrayList<>();

    public static Club createClub(ClubInput clubInput, String profileImgPath) {
        Club club = new Club();
        club.title = clubInput.getTitle();
        club.explanation = clubInput.getExplanation();
        club.introduction = clubInput.getIntroduction();
        club.clubCategory = ClubCategory.fromKoreanName(clubInput.getClubCategory());
        club.university = clubInput.getOrganization();
        club.gender = Gender.fromKoreanName(clubInput.getGender());
        club.activityArea = ActivityArea.fromKoreanName(clubInput.getActivityArea());
        club.sportsType = SportsType.fromKoreanName(clubInput.getSportsType());
        club.ageRange = AgeRange.fromKoreanName(clubInput.getAgeRange());
        club.clubPassword = clubInput.getClubPassword();
        club.profileImgPath = profileImgPath;
        club.mainUniformColor = clubInput.getMainUniformColor();
        club.subUniformColor = clubInput.getSubUniformColor();
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
        if (clubUpdateInput.getClubCategory() != null) {
            this.clubCategory = ClubCategory.fromKoreanName(clubUpdateInput.getClubCategory());
        }
        if (clubUpdateInput.getOrganization() != null) {
            this.university = clubUpdateInput.getOrganization();
        }
        if (clubUpdateInput.getGender() != null) {
            this.gender = Gender.fromKoreanName(clubUpdateInput.getGender());
        }
        if (clubUpdateInput.getActivityArea() != null) {
            this.activityArea = ActivityArea.fromKoreanName(clubUpdateInput.getActivityArea());
        }
        if (clubUpdateInput.getAgeRange() != null) {
            this.ageRange = AgeRange.fromKoreanName(clubUpdateInput.getAgeRange());
        }
        if (clubUpdateInput.getSportsType() != null) {
            this.sportsType = SportsType.fromKoreanName(clubUpdateInput.getSportsType());
        }
        if (profileImgPath != null) {
            if (this.profileImgPath != null) {
                new File(this.profileImgPath).delete();
            }
            this.profileImgPath = profileImgPath;
        }
    }

    public void updateClubPassword(String newPassword) {
        this.clubPassword = newPassword;
    }
}
