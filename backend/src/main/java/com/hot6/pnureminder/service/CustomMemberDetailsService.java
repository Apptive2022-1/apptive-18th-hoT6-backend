package com.hot6.pnureminder.service;

import com.hot6.pnureminder.domain.user.Member;
import com.hot6.pnureminder.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername (String memberId) throws UsernameNotFoundException{
        return memberRepository.findByMemberId((memberId))
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다"));
    }
    
    private UserDetails createUserDetails(Member member){
        return Member.builder()
                .memberId(member.getMemberId())
                .password(passwordEncoder.encode(member.getPassword()))
                .build();
    }
}
