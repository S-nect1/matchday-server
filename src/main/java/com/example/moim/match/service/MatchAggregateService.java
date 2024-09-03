package com.example.moim.match.service;

import com.example.moim.match.entity.Match;
import com.example.moim.match.entity.MatchUser;
import com.example.moim.match.repository.MatchRepository;
import com.example.moim.match.repository.MatchUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchAggregateService {
    private final MatchRepository matchRepository;
    private final MatchUserRepository matchUserRepository;

    //한시간마다 지금시간-48< 매치 끝나는시간 <지금시간이면 집계
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
//    @Scheduled(fixedRate = 10000)//테스트 10초마다
    @Transactional
    public void aggregateMatchScore() {
        log.info("집계 시작");
        for (Match match : matchRepository.findByEndTimeBetween(LocalDateTime.now().minusHours(48), LocalDateTime.now())) {
            // 참가자들의 점수 집계
            int homeScore = 0;
            int awayScore = 0;
            for (MatchUser matchUser : matchUserRepository.findByMatch(match)) {
                if (matchUser.getScore() == null) {
                    continue;
                }

                if (matchUser.getClub().getId().equals(match.getHomeClub().getId())) {
                    homeScore += matchUser.getScore();
                } else {
                    awayScore += matchUser.getScore();
                }
            }
            log.info("Match ID: {}, homeScore: {}, awayScore: {}", match.getId(), homeScore, awayScore);
            match.setMatchScore(homeScore, awayScore);
        }
    }

}
