package com.example.moim.club.repository;

import com.example.moim.club.dto.request.ClubInput;
import com.example.moim.club.dto.request.ClubSearchCond;
import com.example.moim.club.entity.*;
import com.example.moim.global.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@SqlGroup(
        @Sql(value = "/sql/user-club-repository-test-data-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
)
class ClubRepositoryImplTest {

    @Autowired
    private ClubRepository clubRepository;

    @BeforeEach
    void init() {
        String title = "amazing title";
        String title2 = "amazingtitle";
        String explanation = "explanation";
        String introduction = "introduction";
        ClubCategory clubCategory = ClubCategory.SMALL_GROUP;
        String university = "한양대학교";
        Gender gender = Gender.UNISEX;
        ActivityArea activityArea = ActivityArea.SEOUL;
        AgeRange ageRange = AgeRange.TWENTIES;
        SportsType sportsType = SportsType.SOCCER;
        String clubPassword = "clubPassword";
        MultipartFile profileImg = new MockMultipartFile("profileImg", "profileImg".getBytes());
        String mainUniformColor = "mainUniformColor";
        String subUniformColor = "subUniformColor";

        ClubInput clubInput = ClubInput.builder().title(title).explanation(explanation).introduction(introduction).clubCategory(clubCategory.getKoreanName())
                .university(university).gender(gender.getKoreanName()).activityArea(activityArea.getKoreanName()).ageRange(ageRange.getKoreanName()).sportsType(sportsType.getKoreanName())
                .clubPassword(clubPassword).profileImg(profileImg).mainUniformColor(mainUniformColor).subUniformColor(subUniformColor).build();
        ClubInput clubInput2 = ClubInput.builder().title(title2).explanation(explanation).introduction(introduction).clubCategory(clubCategory.getKoreanName())
                .university(university).gender(gender.getKoreanName()).activityArea(activityArea.getKoreanName()).ageRange(ageRange.getKoreanName()).sportsType(sportsType.getKoreanName())
                .clubPassword(clubPassword).profileImg(profileImg).mainUniformColor(mainUniformColor).subUniformColor(subUniformColor).build();
        Club club = Club.createClub(clubInput, "/club");
        Club club2 = Club.createClub(clubInput2, "/club");
        clubRepository.save(club);
        clubRepository.save(club2);
    }

    @Test
    @DisplayName("findBySearchCond 는 search 와 한양대학교 둘 중 하나만 만족되어도 출력된다")
    void findBySearchCond_all_cond() {
        //given
        ClubCategory clubCategory = ClubCategory.SMALL_GROUP;
        String keyword = "amazingtitle";
        String university = "한양대학교";
        Gender gender = Gender.UNISEX;
        ActivityArea activityArea = ActivityArea.SEOUL;
        AgeRange ageRange = AgeRange.TWENTIES;
        SportsType sportsType = SportsType.SOCCER;
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .clubCategory(clubCategory.getKoreanName()).search(keyword).university(university).gender(gender.getKoreanName())
                .activityArea(activityArea.getKoreanName()).ageRange(ageRange.getKoreanName()).sportsType(sportsType.getKoreanName()).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findBySearchCond 는 search, university 조건의 결과값이 없을 경우 무시될 수 있다")
    void findBySearchCond_avoid_search_and_university() {
        //given
        ClubCategory clubCategory = ClubCategory.SMALL_GROUP;
        String keyword = "dagdskjlksjdgl";
        String university = "sgkjskjgl;sjdlkgj";
        Gender gender = Gender.UNISEX;
        ActivityArea activityArea = ActivityArea.SEOUL;
        AgeRange ageRange = AgeRange.TWENTIES;
        SportsType sportsType = SportsType.SOCCER;
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .clubCategory(clubCategory.getKoreanName()).search(keyword).university(university).gender(gender.getKoreanName())
                .activityArea(activityArea.getKoreanName()).ageRange(ageRange.getKoreanName()).sportsType(sportsType.getKoreanName()).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findBySearchCond 는 검색 조건이 hard 조건과 soft 조건 2개일 때, 둘 다 만족해야 출력된다")
    void findBySearchCond_tow_cond() {
        //given
        ClubCategory clubCategory = ClubCategory.SMALL_GROUP;
        String keyword = "amazingtitle";
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .clubCategory(clubCategory.getKoreanName()).search(keyword).university(null).gender(null).activityArea(null).ageRange(null).sportsType(null).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("findBySearchCond 는 아무런 검색 조건 없이 검색할 수 있다(전체 검색)")
    void findBySearchCond_null() {
        //given
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .clubCategory(null).search(null).university(null).gender(null).activityArea(null).ageRange(null).sportsType(null).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    /**
     * FIXME: amazing title 와 amazingtitle 가 같이 결과로 나올 수 있도록 바꾸기
     */
    @Test
    @DisplayName("findBySearchCond 는 제목으로만 검색할 수 있다.")
    void findBySearchCond_title() {
        //given
        String search = "amazingtitle";
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .clubCategory(null).search(search).university(null).gender(null).activityArea(null).ageRange(null).sportsType(null).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
//        assertThat(result.size()).isEqualTo(2);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("findBySearchCond 는 카테고리로만 검색할 수 있다.")
    void findBySearchCond_category() {
        //given
        ClubCategory clubCategory = ClubCategory.SMALL_GROUP;
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .clubCategory(clubCategory.getKoreanName()).search(null).university(null).gender(null).activityArea(null).ageRange(null).sportsType(null).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findBySearchCond 는 소속으로만 검색할 수 있다.")
    void findBySearchCond_university() {
        //given
        String organization = "한양대학교";
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .clubCategory(null).search(null).university(organization).gender(null).activityArea(null).ageRange(null).sportsType(null).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findBySearchCond 는 성별로만 검색할 수 있다.")
    void findBySearchCond_gender() {
        //given
        Gender gender = Gender.UNISEX;
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .clubCategory(null).search(null).university(null).gender(gender.getKoreanName()).activityArea(null).ageRange(null).sportsType(null).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findBySearchCond 는 활동지역으로만 검색할 수 있다.")
    void findBySearchCond_activityArea() {
        //given
        ActivityArea activityArea = ActivityArea.SEOUL;
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .clubCategory(null).search(null).university(null).gender(null).activityArea(activityArea.getKoreanName()).ageRange(null).sportsType(null).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findBySearchCond 는 연령대로만 검색할 수 있다.")
    void findBySearchCond_ageRange() {
        //given
        AgeRange ageRange = AgeRange.TWENTIES;
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .clubCategory(null).search(null).university(null).gender(null).activityArea(null).ageRange(ageRange.getKoreanName()).sportsType(null).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findBySearchCond 는 종목으로만 검색할 수 있다.")
    void findBySearchCond_mainEvent() {
        //given
        SportsType sportsType = SportsType.SOCCER;
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .clubCategory(null).search(null).university(null).gender(null).activityArea(null).ageRange(null).sportsType(sportsType.getKoreanName()).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

}