package com.hot6.pnureminder.controller;

import com.hot6.pnureminder.dto.MemberResponseDto;
import com.hot6.pnureminder.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;


//    @GetMapping("/")
//    public ResponseEntity<MemberResponseDto> findMemberInfoById() {
//        return ResponseEntity.ok(memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()));
//    }


    @GetMapping("/my")
    public ResponseEntity<MemberResponseDto> getMemberInfoByUsername(Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(memberService.findMemberInfoByUsername(username));
    }




}
