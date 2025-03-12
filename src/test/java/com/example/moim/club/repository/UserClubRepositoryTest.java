package com.example.moim.club.repository;

import com.example.moim.club.dto.ClubInput;
import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.UserClub;
import com.example.moim.user.dto.SignupInput;
import com.example.moim.user.entity.Gender;
import com.example.moim.user.entity.User;
import com.example.moim.user.repository.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@SqlGroup({
    @Sql(value = "/sql/user-club-repository-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
})
class UserClubRepositoryTest {

    @Autowired
    private UserClubRepository userClubRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClubRepository clubRepository;

    @Test
    @DisplayName("동아리 객체로 동아리 유저 정보를 찾을 수 있다")
    void findAllByClub() {
        //given
        Club club = clubRepository.findById(1L).get();

        //when
        List<UserClub> userClubList = userClubRepository.findAllByClub(club);

        //then
        assertThat(userClubList.size()).isEqualTo(1);
        assertThat(userClubList.get(0).getUser().getId()).isEqualTo(1);
        assertThat(userClubList.get(0).getClub().getId()).isEqualTo(1);
        assertThat(userClubList.get(0).getJoinDate()).isEqualTo("2024-12-11");
    }

    @Test
    @DisplayName("사용자가 존재하지 않는 동아리로 조회할 경우 빈 리스트가 반환된다")
    void findAllByClub_not_exist_club() {
        //given
        Club club = clubRepository.findById(2L).get();

        //when
        List<UserClub> userClubList = userClubRepository.findAllByClub(club);

        //then
        assertThat(userClubList.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("사용자 객체로 동아리 정보를 조회할 수 있다")
    void findByUser() {
        //given
        User user = userRepository.findById(1L).get();

        //when
        List<UserClub> userClubList = userClubRepository.findByUser(user);

        //then
        assertThat(userClubList.size()).isEqualTo(1);
        assertThat(userClubList.get(0).getClub().getId()).isEqualTo(1L);
        assertThat(userClubList.get(0).getUser().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("동아리에 가입한 적이 없는 사용자 객체로 동아리 정보를 조회하면 빈 리스트가 반환된다")
    void findByUser_not_exist_user() {
        //given
        User user = userRepository.findById(2L).get();

        //when
        List<UserClub> userClubList = userClubRepository.findByUser(user);

        //then
        assertThat(userClubList.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("동아리 객체로 동아리 유저 정보들을 찾을 수 있다")
    void findUserByClub() {
        //given
        Club club = clubRepository.findById(1L).get();

        //when
        List<UserClub> userClubList = userClubRepository.findUserByClub(club);

        //then
        assertThat(userClubList.size()).isEqualTo(1);
        assertThat(userClubList.get(0).getClub().getId()).isEqualTo(1L);
        assertThat(userClubList.get(0).getUser().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("동아리 객체로 운영진들 정보만 조회할 수 있다")
    void findAdminByClub() {
        //given
        Club club = clubRepository.findById(3L).get();

        //when
        List<UserClub> userClubList = userClubRepository.findAdminByClub(club);

        //then
        assertThat(userClubList.size()).isEqualTo(2);
        assertThat(userClubList.get(0).getCategory()).isEqualTo("admin");
        assertThat(userClubList.get(1).getCategory()).isEqualTo("creator");
    }

    @Test
    void query_test() {
        Club club = clubRepository.findById(1L).get();
        List<UserClub> userClubList = userClubRepository.findAllByClub(club);
        String gender = userClubList.get(0).getUser().getGender().name();
        String email = userClubList.get(0).getUser().getEmail();
        log.info("email, gender = {}, {}", email, gender);
    }
}