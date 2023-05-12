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

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    @GetMapping("/findingId")
    public ResponseEntity<MemberResponseDto> getMemberIdForFindingId(
            @RequestParam("nickname") String nickname,
            @RequestParam("findQuesNum") Integer findQuesNum,
            @RequestParam("findAnswer") String findAnswer) {

        Optional<MemberResponseDto> memberResponseDto = memberService.findUsernameForFindingId(nickname, findQuesNum, findAnswer);

        if (memberResponseDto.isPresent()) {
            return new ResponseEntity<>(memberResponseDto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/send-email")
    public ResponseEntity<?> getNewPasswordToUser(
            @RequestParam("username") String username){
//토큰 발급 방식
//        verificationTokenService.createVerificationToken(username);
//임시 비밀번호 방식
        authService.issueTempPassword(username);
        return new ResponseEntity<>("Verification token has been sent to your email.", HttpStatus.OK);
    }

    @GetMapping("/findingPw")
    public ResponseEntity<MemberResponseDto> getNewPasswordToUser(
            @RequestParam("username") String username,
            @RequestParam("verificationToken") String verificationToken) {

//메일을 통해 확인한 코드를 가지고 있는 Member entity 출력
        Member tokenMember = verificationTokenService.verifyEmail(verificationToken);
//받아온 username을 통해 db에서 찾은 Member entity 출력
        Member usernameMember = memberService.findMemberByUsername(username);


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
