package com.example.moim.club.service;

import com.example.moim.club.dto.AwardInput;
import com.example.moim.club.dto.AwardOutput;
import com.example.moim.club.entity.Award;
import com.example.moim.club.entity.Club;
import com.example.moim.club.repository.AwardRepository;
import com.example.moim.club.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AwardService {
    private final AwardRepository awardRepository;
    private final ClubRepository clubRepository;

    public AwardOutput addAward(AwardInput awardInput) {
        Club club = clubRepository.findById(awardInput.getClubId()).get();
        return new AwardOutput(awardRepository.save(new Award(club, awardInput)));
    }

    @Transactional
    public AwardOutput updateAward(AwardInput awardInput) {
        Award award = awardRepository.findById(awardInput.getId()).get();
        award.changeAward(awardInput);
        return new AwardOutput(award);
    }

    public List<AwardOutput> findAward(Long clubId, String order) {
        return awardRepository.findByClubOrderBy(clubRepository.findById(clubId).get(), order).stream().map(AwardOutput::new).collect(Collectors.toList());
    }

    @Transactional
    public void removeAward(Long id) {
        awardRepository.deleteById(id);
    }
}
