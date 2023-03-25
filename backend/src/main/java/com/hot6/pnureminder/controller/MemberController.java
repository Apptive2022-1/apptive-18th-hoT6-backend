package com.hot6.pnureminder.controller;

import com.hot6.pnureminder.Dto.LoginDto;
import com.hot6.pnureminder.Dto.SignUpDto;
import com.hot6.pnureminder.Dto.TokenDto;
import com.hot6.pnureminder.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/test"})
public class MemberController {

//(순환 참조 에러, 객체 불변성, 테스트코드 작성시 편리성을 위해 생성자 주입 방식 적용)
//의존성 주입 실수 방지를 위해 final 키워드 사용
//
    private final MemberService memberService;

    @PostMapping("/signup")
    public String signUp(@RequestBody SignUpDto signUpDto) throws Exception {
        return memberService.signUp(signUpDto);
    }

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody LoginDto loginDto){
        return new ResponseEntity(memberService.login(loginDto), HttpStatus.OK);
    }


}
