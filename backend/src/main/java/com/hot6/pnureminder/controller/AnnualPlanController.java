package com.hot6.pnureminder.controller;

import com.hot6.pnureminder.dto.AnnualPlanResponseDto;
import com.hot6.pnureminder.dto.MemberResponseDto;
import com.hot6.pnureminder.jwt.JwtFilter;
import com.hot6.pnureminder.service.AnnualPlanService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/annualplan")
public class AnnualPlanController {
    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);
    private final AnnualPlanService annualPlanService;

    @GetMapping("/my")
    public ResponseEntity<List<AnnualPlanResponseDto>> getAnnualPlanWithState(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication: {}", auth);
        String username = auth.getName();
        MemberResponseDto memberResponseDto = annualPlanService.findMemberInfoByUsername(username);

        Integer userState = memberResponseDto.getState();

        List<AnnualPlanResponseDto> annualPlans = annualPlanService.findAllByStateOrState(userState);
        return ResponseEntity.ok().body(annualPlans);
    }
}
