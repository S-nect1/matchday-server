package com.example.moim.club.entity;

import com.example.moim.club.dto.request.ClubInput;
import com.example.moim.club.dto.request.ClubUpdateInput;
import com.example.moim.club.exception.advice.ClubControllerAdvice;
import com.example.moim.global.entity.BaseEntity;
import com.example.moim.global.enums.*;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.global.util.TextUtils;
import com.example.moim.match.entity.Match;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

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
    @OneToOne(mappedBy = "club")  // 검색을 위한 테이블 매핑
    private ClubSearch clubSearch;

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

    /**
     * TODO : university 는 없을 수도 있으므로, null 일 경우를 처리해주기
     */
    public static Club createClub(ClubInput clubInput, String profileImgPath) {
        Club club = new Club();
        club.title = clubInput.getTitle();
        club.explanation = clubInput.getExplanation();
        club.introduction = clubInput.getIntroduction();
        club.clubCategory = ClubCategory.fromKoreanName(clubInput.getClubCategory()).get();
        club.university = clubInput.getUniversity();
        club.gender = Gender.fromKoreanName(clubInput.getGender()).get();
        club.activityArea = ActivityArea.fromKoreanName(clubInput.getActivityArea()).get();
        club.sportsType = SportsType.fromKoreanName(clubInput.getSportsType()).get();
        club.ageRange = AgeRange.fromKoreanName(clubInput.getAgeRange()).get();
        club.clubPassword = clubInput.getClubPassword();
        club.profileImgPath = profileImgPath;
        club.mainUniformColor = clubInput.getMainUniformColor();
        club.subUniformColor = clubInput.getSubUniformColor();
        club.memberCount = 1;
        return club;
    }

    public Club updateClubSearch(ClubSearch clubSearch) {
        this.clubSearch = clubSearch;
        return this;
    }

    public void changeProfileImg(String newImgPath) {
        this.profileImgPath = newImgPath;
    }

    public void plusMemberCount() {
        memberCount++;
    }

    public void updateClub(ClubUpdateInput clubUpdateInput, String profileImgPath) {
        if (StringUtils.hasText(clubUpdateInput.getTitle())) {
            this.title = clubUpdateInput.getTitle();
        }
        if (StringUtils.hasText(clubUpdateInput.getExplanation())) {
            this.explanation = clubUpdateInput.getExplanation();
        }
        if (StringUtils.hasText(clubUpdateInput.getIntroduction())) {
            this.introduction = clubUpdateInput.getIntroduction();
        }
        if (StringUtils.hasText(clubUpdateInput.getClubCategory())) {
            this.clubCategory = ClubCategory.fromKoreanName(clubUpdateInput.getClubCategory()).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.INVALID_CLUB_CATEGORY));
        }
        if (StringUtils.hasText(clubUpdateInput.getOrganization())) {
            this.university = clubUpdateInput.getOrganization();
        }
        if (StringUtils.hasText(clubUpdateInput.getGender())) {
            this.gender = Gender.fromKoreanName(clubUpdateInput.getGender()).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.INVALID_GENDER));
        }
        if (StringUtils.hasText(clubUpdateInput.getActivityArea())) {
            this.activityArea = ActivityArea.fromKoreanName(clubUpdateInput.getActivityArea()).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.INVALID_ACTIVITY_AREA));
        }
        if (StringUtils.hasText(clubUpdateInput.getAgeRange())) {
            this.ageRange = AgeRange.fromKoreanName(clubUpdateInput.getAgeRange()).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.INVALID_AGE_RANGE));
        }
        if (StringUtils.hasText(clubUpdateInput.getSportsType())) {
            this.sportsType = SportsType.fromKoreanName(clubUpdateInput.getSportsType()).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.INVALID_SPORTS_TYPE));
        }
        if (StringUtils.hasText(profileImgPath)) {
            if (StringUtils.hasText(this.profileImgPath)) {
                new File(this.profileImgPath).delete();
            }
            this.profileImgPath = profileImgPath;
        }
    }

    public void updateClubPassword(String newPassword) {
        this.clubPassword = newPassword;
    }
}
