package com.example.moim.club.service;

import com.example.moim.club.dto.*;
import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.UserClub;
import com.example.moim.club.repository.AwardRepository;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.ScheduleRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.exception.club.ClubPermissionException;
import com.example.moim.global.util.FileStore;
import com.example.moim.user.entity.User;
import com.example.moim.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;
    private final UserClubRepository userClubRepository;
    private final ScheduleRepository scheduleRepository;
    private final AwardRepository awardRepository;
    private final UserRepository userRepository;
    private final FileStore fileStore;

    public ClubOutput saveClub(User user, ClubInput clubInput) throws IOException {
        Club club = clubRepository.save(Club.createClub(clubInput, fileStore.storeFile(clubInput.getProfileImg())));
        UserClub userClub = userClubRepository.save(UserClub.createLeaderUserClub(user, club));
        return new ClubOutput(club, userClub.getCategory());
    }

    //    @Transactional
//    public ClubOutput updateClub(User user, ClubUpdateInput clubUpdateInput) throws IOException {
//        Club club = clubRepository.findById(clubUpdateInput.getId()).get();
//        UserClub userClub = userClubRepository.findByClubAndUser(club, user).get();
//        if (!(userClub.getCategory().equals("creator") || userClub.getCategory().equals("admin"))) {
//            throw new ClubPermissionException("모임 정보를 수정할 권한이 없습니다.");
//        }
//
//        if (club.getProfileImgPath() != null) {
//            new File(club.getProfileImgPath()).delete();
//        }
//        club.UpdateClub(clubUpdateInput, fileStore.storeFile(clubUpdateInput.getProfileImg()));
//        return new ClubOutput(club);
//    }
    public List<ClubSearchOutput> searchClub(ClubSearchCond clubSearchCond) {
        return clubRepository.findBySearchCond(clubSearchCond).stream().map(ClubSearchOutput::new).toList();
    }

    public UserClubOutput saveClubUser(User user, ClubUserSaveInput clubUserSaveInput) {
        return new UserClubOutput(userClubRepository.save(UserClub.createUserClub(user, clubRepository.findById(clubUserSaveInput.getClubId()).get())));
    }

//    public UserClubOutput inviteClubUser(User user, ClubInviteInput clubInviteInput) {
//        userRepository.findById(clubInviteInput.getUser().)
//    }

    @Transactional
    public UserClubOutput updateClubUser(User user, ClubUserUpdateInput clubInput) {
        Club club = clubRepository.findById(clubInput.getId()).get();
        UserClub userClub = userClubRepository.findByClubAndUser(club, user).get();
        if (!(userClub.getCategory().equals("creator") || userClub.getCategory().equals("admin"))) {
            throw new ClubPermissionException("모임 멤버 권한을 수정할 권한이 없습니다.");
        }

        UserClub changeUserClub = userClubRepository.findByClubAndUser(club, userRepository.findById(clubInput.getUserId()).get()).get();
        changeUserClub.changeUserClub(clubInput.getPosition(), clubInput.getCategory());
        return new UserClubOutput(changeUserClub);
    }

    public ClubOutput findClub(Long id, User user) {
        Club club = clubRepository.findById(id).get();
        List<UserClubOutput> userClubOutputs = userClubRepository.findAllByClub(club).stream().map(UserClubOutput::new).toList();
        List<ScheduleOutput> scheduleOutputs = scheduleRepository.findTop5ByClubOrderByCreatedDateDesc(club).stream().map(ScheduleOutput::new).toList();
        List<AwardOutput> awardOutputs = awardRepository.findByClub(club).stream().map(AwardOutput::new).toList();
        UserClub userClub = userClubRepository.findByClubAndUser(club, user).get();
        return new ClubOutput(club, userClubOutputs, scheduleOutputs, awardOutputs, userClub.getCategory());
    }

    @Transactional
    public void updateProfileImg(ClubImgInput clubImgInput) throws IOException {
        Club club = clubRepository.findById(clubImgInput.getId()).get();
        if (club.getProfileImgPath() != null) {
            new File(club.getProfileImgPath()).delete();
        }
        club.changeProfileImg(fileStore.storeFile(clubImgInput.getImg()));
    }
}
