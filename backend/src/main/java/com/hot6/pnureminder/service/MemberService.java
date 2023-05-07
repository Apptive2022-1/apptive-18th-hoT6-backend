package com.hot6.pnureminder.service;

import com.hot6.pnureminder.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
//조회 쿼리만 사용할 예정
@Transactional(readOnly = true)
public class MemberService {

    private final CustomUserDetailsService customUserDetailsService;

    public MemberResponseDto findMemberInfoByUsername(String username) {
        return customUserDetailsService.findMemberInfoByUsername(username);
    }

    public Integer findMemberStateByUsername(String username) {
        MemberResponseDto member = customUserDetailsService.findMemberInfoByUsername(username);

        return member.getState();
    }
}
