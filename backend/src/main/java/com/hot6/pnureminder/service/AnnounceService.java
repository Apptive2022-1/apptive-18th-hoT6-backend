package com.hot6.pnureminder.service;


import com.hot6.pnureminder.dto.MemberResponseDto;
import com.hot6.pnureminder.repository.AnnounceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnounceService {

    private final AnnounceRepository announceRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public List<> find
        return

}
