package com.hot6.pnureminder.service;

import com.hot6.pnureminder.dto.AnnualPlanResponseDto;
import com.hot6.pnureminder.dto.EventDto;
import com.hot6.pnureminder.entity.AnnualPlan;
import com.hot6.pnureminder.entity.Event;
import com.hot6.pnureminder.mapperclass.AnnualPlanMapper;
import com.hot6.pnureminder.repository.AnnualPlanRepository;
import com.hot6.pnureminder.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AnnualPlanService {

    private final AnnualPlanRepository annualPlanRepository;
    private final MemberService memberService;
    private final AnnualPlanMapper annualPlanMapper;
    private final EventRepository eventRepository;

    //회원가입시에 학사일정을 전부 이벤트에 등록하는 로직
    @Transactional
    public void registerAnnualPlansAsEventsForState(String username, Integer state1) {
        Integer state2 = 0;
        List<AnnualPlan> annualPlans = annualPlanRepository.findAllByStateOrState(state1,state2);
//        Long memberId = memberService.findMemberByUsername(username).getId();

        List<Event> events = annualPlans.stream()
                .map(annualPlan -> annualPlanMapper.annualPlanToEventDto(annualPlan))
                .map(EventDto::toEntity)
                .toList();

        eventRepository.saveAll(events);
    }



//조회 쿼리만 사용할 예정
    @Transactional(readOnly = true)
    public List<AnnualPlanResponseDto> findAllByStateOrState(Integer state1) {
        Integer state2 = 0;
        List<AnnualPlan> annualPlans = annualPlanRepository.findAllByStateOrState(state1,state2);
        return annualPlans.stream()
                .map(AnnualPlanResponseDto::toDto)
                .toList();
    }



}
