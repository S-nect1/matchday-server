package com.example.moim.club.controller;

import com.example.moim.club.dto.AwardInput;
import com.example.moim.club.dto.AwardOutput;
import com.example.moim.club.service.AwardService;
import com.example.moim.user.dto.userDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AwardController implements AwardControllerDocs{
    private final AwardService awardService;

    @PostMapping("/award")
    public AwardOutput awardAdd(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @RequestBody AwardInput awardInput) {
        return awardService.addAward(awardInput);
    }

    @PatchMapping("/award")
    public AwardOutput awardUpdate(@AuthenticationPrincipal userDetailsImpl userDetailsImpl,@RequestBody AwardInput awardInput) {
        return awardService.updateAward(awardInput);
    }

    @GetMapping("/award/{clubId}")
    public List<AwardOutput> awardFind(@PathVariable Long clubId, @RequestParam String order) {
        return awardService.findAward(clubId, order);
    }

    @DeleteMapping("/award/{id}")
    public void awardRemove(@AuthenticationPrincipal userDetailsImpl userDetailsImpl,@PathVariable Long id) {
        awardService.removeAward(id);
    }
}
