package com.example.moim.schedule.entity;

import com.example.moim.global.enums.AgeRange;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class OpponentTeamInfo {
    private String opponentTeamName;
    @Enumerated(value = EnumType.STRING)
    private AgeRange opponentTeamAgeRange;
}
