package com.hot6.pnureminder.controller;


import com.hot6.pnureminder.dto.AnnualPlanResponseDto;
import com.hot6.pnureminder.dto.MemberResponseDto;
import com.hot6.pnureminder.jwt.JwtFilter;
import com.hot6.pnureminder.service.AnnualPlanService;
import com.hot6.pnureminder.service.MemberService;
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

@RequestMapping("api/announce")
@RequiredArgsConstructor
@RestController
public class AnnounceController {
    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    private final AnnualPlanService annualPlanService;
    private final MemberService memberService;

}