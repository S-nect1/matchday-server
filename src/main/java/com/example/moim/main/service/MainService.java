package com.example.moim.main.service;

import com.example.moim.club.dto.ScheduleSearchInput;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.service.ScheduleService;
import com.example.moim.main.dto.MainOutput;
import com.example.moim.main.dto.NoClubMainOutput;
import com.example.moim.main.dto.RecommendClubListOutput;
import com.example.moim.user.entity.User;
import com.example.moim.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
@RequiredArgsConstructor
public class MainService {
    private final ScheduleService scheduleService;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;

    public NoClubMainOutput noClubMainPage (User user) {
        user = userRepository.findById(user.getId()).get();
        return new NoClubMainOutput(clubRepository.findTop5ByActivityAreaOrderByMemberCount(user.getActivityArea())
                .stream().map(RecommendClubListOutput::new).toList(), null);
    }

    public MainOutput mainPage(Long clubId) {
        return new MainOutput(clubRepository.findById(clubId).get(),
                scheduleService.findSchedule(new ScheduleSearchInput(Integer.parseInt(LocalDate.now().toString().replace("-", "")),
                clubId, null, null)));
    }
}
