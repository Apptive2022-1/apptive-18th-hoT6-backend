package com.hot6.pnureminder.service;

import com.hot6.pnureminder.dto.AnnualPlanResponseDto;
import com.hot6.pnureminder.dto.MemberResponseDto;
import com.hot6.pnureminder.entity.AnnualPlan;
import com.hot6.pnureminder.repository.AnnualPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//조회 쿼리만 사용할 예정
@Transactional(readOnly = true)
public class AnnualPlanService {

    private final AnnualPlanRepository annualPlanRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public MemberResponseDto findMemberInfoByUsername(String username) {
        return customUserDetailsService.findMemberInfoByUsername(username);
    }

    public List<AnnualPlanResponseDto> findAllByStateOrState(Integer state1) {
        Integer state2 = 0;
        List<AnnualPlan> annualPlans = annualPlanRepository.findAllByStateOrState(state1,state2);
        return annualPlans.stream()
                .map(AnnualPlanResponseDto::toDto)
                .collect(Collectors.toList());
    }

}
