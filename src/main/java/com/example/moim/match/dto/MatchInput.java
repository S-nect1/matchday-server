package com.example.moim.match.dto;

import com.example.moim.match.entity.Gender;
import com.example.moim.match.entity.Match;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatchInput {

    private Long clubId;

    @NotBlank(message = "매치 종목을 선택해주세요.")
    private String event;

    @NotBlank(message = "인원 수를 선택해주세요.")
    private String matchSize;

    @Schema(pattern = "yyyy/MM/dd HH:mm", description = "yyyy/MM/dd HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
    @NotNull(message = "매치 시작 시간을 입력해주세요.")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
    @NotNull(message = "매치 종료 시간을 입력해주세요.")
    private LocalDateTime endTime;

    @NotBlank(message = "매치 장소를 입력해주세요.")
    private String location;

    private int fee = 0;

    private String account;

    @NotNull(message = "최소 인원수를 설정해주세요.")
    private int minParticipants;
}
