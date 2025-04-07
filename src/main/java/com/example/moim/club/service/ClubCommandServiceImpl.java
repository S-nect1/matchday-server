package com.example.moim.club.service;

import com.example.moim.club.dto.request.*;
import com.example.moim.club.dto.response.ClubOutput;
import com.example.moim.club.dto.response.ClubSaveOutput;
import com.example.moim.club.dto.response.ClubUpdateOutput;
import com.example.moim.club.dto.response.UserClubOutput;
import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.ClubSearch;
import com.example.moim.club.entity.UserClub;
import com.example.moim.club.exception.advice.ClubControllerAdvice;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.ClubSearchRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.global.enums.ClubRole;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.global.util.FileStore;
import com.example.moim.global.util.TextUtils;
import com.example.moim.notification.dto.ClubJoinEvent;
import com.example.moim.user.entity.User;
import com.example.moim.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubCommandServiceImpl implements ClubCommandService {
    private final ClubRepository clubRepository;
    private final UserClubRepository userClubRepository;
    private final UserRepository userRepository;
    private final FileStore fileStore;
    private final ClubSearchRepository clubSearchRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public ClubSaveOutput saveClub(User user, ClubInput clubInput) throws IOException {
        Club club = clubRepository.save(Club.createClub(clubInput, fileStore.storeFile(clubInput.getProfileImg())));
        // 검색을 위한 저장
        saveClubSearch(club);

        UserClub userClub = userClubRepository.save(UserClub.createLeaderUserClub(user, club));

        List<UserClubOutput> userList = userClubRepository.findAllByClub(club).stream().map(UserClubOutput::new).toList();
        return new ClubSaveOutput(club, userList);
    }

    @Transactional
    public ClubUpdateOutput updateClub(User user, ClubUpdateInput clubUpdateInput, Long clubId) throws IOException {
        Club club = getClub(clubId);
//        Club club = clubRepository.findById(clubId).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_NOT_FOUND));
        UserClub userClub = userClubRepository.findByClubAndUser(club, user).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_USER_NOT_FOUND));
        if (!(userClub.getClubRole().equals(ClubRole.STAFF))) {
            throw new ClubControllerAdvice(ResponseCode.CLUB_PERMISSION_DENIED);
        }

        if (clubUpdateInput.getClubPassword() == null || !clubUpdateInput.getClubPassword().equals(club.getClubPassword())) {
            throw new ClubControllerAdvice(ResponseCode.CLUB_PASSWORD_INCORRECT);
        }

        club.updateClub(clubUpdateInput, fileStore.storeFile(clubUpdateInput.getProfileImg()));
        // 검색 정보 동기화를 위한 처리
        club.getClubSearch().updateFrom(club);
        List<UserClubOutput> userList = userClubRepository.findAllByClub(club).stream().map(UserClubOutput::new).toList();
        return new ClubUpdateOutput(club, userList);
    }

    @Transactional
    public UserClubOutput saveClubUser(User user, ClubUserSaveInput clubUserSaveInput, Long clubId) {
        Club club = getClub(clubId);
//        Club club = clubRepository.findById(clubId).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_NOT_FOUND));
        Optional<UserClub> checkUserClub = userClubRepository.findByClubAndUser(club, user);

        if (club.getClubPassword().equals(clubUserSaveInput.getClubPassword())) {
            if (checkUserClub.isPresent()) {
                throw new ClubControllerAdvice(ResponseCode.CLUB_USER_ALREADY_JOINED);
            }
            club.plusMemberCount();
            UserClub userClub = userClubRepository.save(UserClub.createUserClub(user, club));
            eventPublisher.publishEvent(new ClubJoinEvent(user, club));
            return new UserClubOutput(userClub);
        }
        throw new ClubControllerAdvice(ResponseCode.CLUB_PASSWORD_INCORRECT);
    }

//    public UserClubOutput inviteClubUser(User user, ClubInviteInput clubInviteInput) {
//        userRepository.findById(clubInviteInput.getUser().)
//    }

    @Transactional
    public UserClubOutput updateClubUser(User user, ClubUserUpdateInput clubUserUpdateInput, Long clubId) {
        Club club = getClub(clubId);
//        Club club = clubRepository.findById(clubId).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_NOT_FOUND));
        UserClub userClub = userClubRepository.findByClubAndUser(club, user).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_USER_NOT_FOUND));
        if (!(userClub.getClubRole().equals(ClubRole.STAFF))) {
            throw new ClubControllerAdvice(ResponseCode.CLUB_PERMISSION_DENIED);
        }

        User targetUser = userRepository.findById(clubUserUpdateInput.getUserId()).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_USER_NOT_FOUND));
        UserClub changeUserClub = userClubRepository.findByClubAndUser(club, targetUser).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_USER_NOT_FOUND));
        changeUserClub.changeUserClub(ClubRole.fromKoreanName(clubUserUpdateInput.getClubRole()).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.INVALID_CLUB_ROLE)));
        return new UserClubOutput(changeUserClub);
    }

    @Transactional
    public void clubPasswordUpdate(User user, ClubPswdUpdateInput clubPswdUpdateInput, Long clubId) {
        Club club = getClub(clubId);
//        Club club = clubRepository.findById(clubPswdUpdateInput.getId()).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_NOT_FOUND));
        UserClub userClub = userClubRepository.findByClubAndUser(club, user).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_USER_NOT_FOUND));
        if (!(userClub.getClubRole().equals(ClubRole.STAFF))) {
            throw new ClubControllerAdvice(ResponseCode.CLUB_PERMISSION_DENIED);
        }

        if (!clubPswdUpdateInput.getOldPassword().equals(club.getClubPassword())) {
            throw new ClubControllerAdvice(ResponseCode.CLUB_PASSWORD_INCORRECT);
        }

        if (!clubPswdUpdateInput.getNewPassword().equals(clubPswdUpdateInput.getRePassword())) {
            throw new ClubControllerAdvice(ResponseCode.CLUB_CHECK_PASSWORD_INCORRECT);
        }
        club.updateClubPassword(clubPswdUpdateInput.getNewPassword());
    }

    private Club getClub(Long clubId) {
        return clubRepository.findById(clubId).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_NOT_FOUND));
    }

    private void saveClubSearch(Club club) {
        ClubSearch clubSearch = ClubSearch.builder()
                .club(club)
                .titleNoSpace(TextUtils.clean(club.getTitle()))
                .introNoSpace(TextUtils.clean(club.getIntroduction()))
                .expNoSpace(TextUtils.clean(club.getExplanation()))
                .allFieldsConcat(TextUtils.concatClean("|", club.getTitle(), club.getIntroduction(), club.getExplanation()))
                .build();

        club.updateClubSearch(clubSearchRepository.save(clubSearch));
    }

//    @Transactional
//    public void updateProfileImg(ClubImgInput clubImgInput) throws IOException {
//        Club club = clubRepository.findById(clubImgInput.getId()).get();
//        if (club.getProfileImgPath() != null) {
//            new File(club.getProfileImgPath()).delete();
//        }
//        club.changeProfileImg(fileStore.storeFile(clubImgInput.getImg()));
//    }
}
