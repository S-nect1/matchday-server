package com.example.moim.club.repository;

import com.example.moim.club.dto.request.ClubInput;
import com.example.moim.club.dto.request.ClubSearchCond;
import com.example.moim.club.entity.Club;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ClubRepositoryImplTest {

    @Autowired
    private ClubRepository clubRepository;

    @BeforeEach
    void init() {
        String title = "amazing title";
        String title2 = "amazingtitle";
        String explanation = "explanation";
        String introduction = "introduction";
        String category = "category";
        String university = "university";
        String gender = "gender";
        String activityArea = "activityArea";
        String ageRange = "ageRange";
        String mainEvent = "mainEvent";
        String clubPassword = "clubPassword";
        MultipartFile profileImg = new MockMultipartFile("profileImg", "profileImg".getBytes());
        String mainUniformColor = "mainUniformColor";
        String subUniformColor = "subUniformColor";

        ClubInput clubInput = ClubInput.builder().title(title).explanation(explanation).introduction(introduction).category(category)
                .university(university).gender(gender).activityArea(activityArea).ageRange(ageRange).mainEvent(mainEvent)
                .clubPassword(clubPassword).profileImg(profileImg).mainUniformColor(mainUniformColor).subUniformColor(subUniformColor).build();
        ClubInput clubInput2 = ClubInput.builder().title(title2).explanation(explanation).introduction(introduction).category(category)
                .university(university).gender(gender).activityArea(activityArea).ageRange(ageRange).mainEvent(mainEvent)
                .clubPassword(clubPassword).profileImg(profileImg).mainUniformColor(mainUniformColor).subUniformColor(subUniformColor).build();
        Club club = Club.createClub(clubInput, "/club");
        Club club2 = Club.createClub(clubInput2, "/club");
        clubRepository.save(club);
        clubRepository.save(club2);
    }

    @Test
    @DisplayName("findBySearchCond 는 조건이 둘 다 만족해야 출력된다") // and 조건 이었네,,?
    void findBySearchCond_all_cond() {
        //given
        String category = "nothing";
        String search = "title";
        String university = "university";
        String gender = "gender";
        String activityArea = "activityArea";
        String ageRange = "ageRange";
        String mainEvent = "mainEvent";
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .category(category).search(search).university(university).gender(gender).activityArea(activityArea).ageRange(ageRange).mainEvent(mainEvent).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("findBySearchCond 는 아무런 검색 조건이 2개일 때, 둘 다 만족해야 출력된다") // and 조건 이었네,,?
    void findBySearchCond_tow_cond() {
        //given
        String category = "nothing";
        String search = "title";
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .category(category).search(search).university(null).gender(null).activityArea(null).ageRange(null).mainEvent(null).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("findBySearchCond 는 아무런 검색 조건 없이 검색할 수 있다(전체 검색)")
    void findBySearchCond_null() {
        //given
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .category(null).search(null).university(null).gender(null).activityArea(null).ageRange(null).mainEvent(null).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findBySearchCond 는 제목으로만 검색할 수 있다.")
    void findBySearchCond_title() {
        //given
        String search = "title";
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .category(null).search(search).university(null).gender(null).activityArea(null).ageRange(null).mainEvent(null).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findBySearchCond 는 카테고리로만 검색할 수 있다.")
    void findBySearchCond_category() {
        //given
        String category = "category";
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .category(category).search(null).university(null).gender(null).activityArea(null).ageRange(null).mainEvent(null).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findBySearchCond 는 대학으로만 검색할 수 있다.")
    void findBySearchCond_university() {
        //given
        String university = "university";
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .category(null).search(null).university(university).gender(null).activityArea(null).ageRange(null).mainEvent(null).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findBySearchCond 는 성별로만 검색할 수 있다.")
    void findBySearchCond_gender() {
        //given
        String gender = "gender";
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .category(null).search(null).university(null).gender(gender).activityArea(null).ageRange(null).mainEvent(null).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findBySearchCond 는 활동지역으로만 검색할 수 있다.")
    void findBySearchCond_activityArea() {
        //given
        String activityArea = "activityArea";
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .category(null).search(null).university(null).gender(null).activityArea(activityArea).ageRange(null).mainEvent(null).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findBySearchCond 는 연령대로만 검색할 수 있다.")
    void findBySearchCond_ageRange() {
        //given
        String ageRange = "ageRange";
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .category(null).search(null).university(null).gender(null).activityArea(null).ageRange(ageRange).mainEvent(null).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findBySearchCond 는 종목으로만 검색할 수 있다.")
    void findBySearchCond_mainEvent() {
        //given
        String mainEvent = "mainEvent";
        ClubSearchCond clubSearchCond = ClubSearchCond.builder()
                .category(null).search(null).university(null).gender(null).activityArea(null).ageRange(null).mainEvent(mainEvent).build();
        //when
        List<Club> result = clubRepository.findBySearchCond(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

}