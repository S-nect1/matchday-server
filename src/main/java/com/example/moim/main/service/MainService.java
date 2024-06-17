package com.example.moim.main.service;

import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.main.dto.NoClubMainOutput;
import com.example.moim.main.dto.RecommendClubListOutput;
import com.example.moim.user.entity.User;
import com.example.moim.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService {
    private final UserRepository userRepository;
    private final UserClubRepository userClubRepository;
    private final ClubRepository clubRepository;

    public NoClubMainOutput noClubMainPage(User user) {
        user = userRepository.findById(user.getId()).get();
        return new NoClubMainOutput(clubRepository.findTop5ByActivityAreaOrderByMemberCount(user.getActivityArea())
                .stream().map(RecommendClubListOutput::new).toList(), null);
    }
}
