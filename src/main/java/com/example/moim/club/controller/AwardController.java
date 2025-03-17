package com.example.moim.club.controller;

import com.example.moim.club.dto.AwardInput;
import com.example.moim.club.dto.AwardOutput;
import com.example.moim.club.service.AwardService;
import com.example.moim.global.exception.BaseResponse;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.user.dto.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AwardController implements AwardControllerDocs{
    private final AwardService awardService;

    @PostMapping("/award")
    public BaseResponse<AwardOutput> awardAdd(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody @Valid AwardInput awardInput) {
        return BaseResponse.onSuccess(awardService.addAward(awardInput), ResponseCode.OK);
    }

    @PatchMapping("/award")
    public AwardOutput awardUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody @Valid AwardInput awardInput) {
        return awardService.updateAward(awardInput);
    }

    @GetMapping("/award/{clubId}")
    public List<AwardOutput> awardFind(@PathVariable Long clubId, @RequestParam String order) {
        return awardService.findAward(clubId, order);
    }

    @DeleteMapping("/award/{id}")
    public void awardRemove(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable Long id) {
        awardService.removeAward(id);
    }
}
