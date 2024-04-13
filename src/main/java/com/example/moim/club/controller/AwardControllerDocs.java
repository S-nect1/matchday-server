package com.example.moim.club.controller;

import com.example.moim.club.dto.AwardInput;
import com.example.moim.club.dto.AwardOutput;
import com.example.moim.user.dto.userDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "모임 수상내역 api")
public interface AwardControllerDocs {
    @Operation(summary = "수상내역 추가", description = "id는 빼기, imgPath는 앱의 트로피 아이콘중 어떤건지 아이콘 이름 작성")
    AwardOutput awardAdd(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @RequestBody AwardInput awardInput);

    @Operation(summary = "수상내역 수정")
    AwardOutput awardUpdate(@AuthenticationPrincipal userDetailsImpl userDetailsImpl,@RequestBody AwardInput awardInput);

    @Operation(summary = "수상내역 조회", description = "order 는 created, priority중 하나 ex) /award/6?order=created")
    List<AwardOutput> awardFind(@PathVariable Long clubId, @RequestParam String order);

    @Operation(summary = "수상내역 제거")
    void awardRemove(@AuthenticationPrincipal userDetailsImpl userDetailsImpl,@PathVariable Long id);
}
