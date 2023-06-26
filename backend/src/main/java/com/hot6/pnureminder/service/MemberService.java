package com.hot6.pnureminder.service;

import com.hot6.pnureminder.dto.MemberResponseDto;
import com.hot6.pnureminder.entity.Member;
import com.hot6.pnureminder.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
//조회 쿼리만 사용할 예정
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final CustomUserDetailsService customUserDetailsService;


    public Member findMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }


    public MemberResponseDto findMemberInfoByUsername(String username) {
        return customUserDetailsService.findMemberInfoByUsername(username);
    }



    public Optional<MemberResponseDto> findUsernameForFindingId(String nickname, Integer findQuesNum, String findAnswer) {
        Optional<Member> member = memberRepository.findByNicknameAndFindQuesNumAndFindAnswer(nickname, findQuesNum, findAnswer);
        return member.map(MemberResponseDto::toDtoUsername);
    }

    public Integer findMemberStateByUsername(String username) {
        MemberResponseDto member = customUserDetailsService.findMemberInfoByUsername(username);

        return member.getState();

    }
}
