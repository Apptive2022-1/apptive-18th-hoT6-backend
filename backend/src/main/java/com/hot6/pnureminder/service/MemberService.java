package com.hot6.pnureminder.service;

import com.hot6.pnureminder.dto.MemberResponseDto;
import com.hot6.pnureminder.entity.Member;
import com.hot6.pnureminder.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public Integer getMemberStateByEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            return optionalMember.get().getState();
        } else{
            throw new NoSuchElementException("Member get state error");
        }
    }

    public MemberResponseDto findMemberInfoById(Long memberId) {
        return memberRepository.findById(memberId)
            .map(MemberResponseDto::of)
            .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public MemberResponseDto findMemberInfoByEmail(String email) {
        return memberRepository.findByEmail(email)
            .map(MemberResponseDto::of)
            .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }
}
