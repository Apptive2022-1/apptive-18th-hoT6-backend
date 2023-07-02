package com.hot6.pnureminder.controller;

import com.hot6.pnureminder.dto.*;
import com.hot6.pnureminder.entity.Member;
import com.hot6.pnureminder.service.AuthService;
import com.hot6.pnureminder.service.MemberService;
import com.hot6.pnureminder.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MemberService memberService;
    private final VerificationTokenService verificationTokenService;


    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenRequestDto requestDto) {
        authService.logout(requestDto);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    @PostMapping("/findingId")
    public String getMemberIdForFindingId(@RequestBody FindingIdRequestDto requestDto) {
        String username = memberService.findUsernameForFindingId(requestDto.getNickname(), requestDto.getFindQuesNum(), requestDto.getFindAnswer());
        return username;
    }


    // 보안문제 리팩터링 요청
    @PostMapping("/send-email")
    public ResponseEntity<?> getNewPasswordToUser(
            @RequestParam("username") String username){
        authService.issueTempPassword(username);
        return new ResponseEntity<>("Verification token has been sent to your email.", HttpStatus.OK);
    }


    // 토큰발급방식 deprecated 됨
}
