package com.hot6.pnureminder.service;

import com.hot6.pnureminder.Dto.SignUpDto;
import com.hot6.pnureminder.Dto.TokenDto;
import com.hot6.pnureminder.JWT.JwtTokenProvider;
import com.hot6.pnureminder.domain.user.Member;
import com.hot6.pnureminder.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final BCryptPasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenDto login(String memberId, String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
        Authentication authentication=authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);

        return tokenDto;
    }

    @Transactional
    public String signUp(SignUpDto signUpDto) throws Exception{
        boolean existcheck = checkMemberId(signUpDto.getMemberId());

        if (existcheck) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        String encodePwd = encoder.encode(signUpDto.getPassword());

        Member member = memberRepository.save(signUpDto.toEntity(encodePwd));

        if (member!=null){
            return member.getMemberId();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    public boolean checkMemberId(String memberId) {
        return memberRepository.existsMembersByMemberId(memberId);
    }

}
