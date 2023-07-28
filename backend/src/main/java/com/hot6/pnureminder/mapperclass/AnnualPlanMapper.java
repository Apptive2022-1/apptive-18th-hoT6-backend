package com.hot6.pnureminder.mapperclass;

import com.hot6.pnureminder.dto.EventDto;
import com.hot6.pnureminder.entity.AnnualPlan;
import org.springframework.stereotype.Component;


@Component
public class AnnualPlanMapper {

    public EventDto annualPlanToEventDto(AnnualPlan annualPlan) {
        return EventDto.builder()
                .eventId(null)
                .title(annualPlan.getContext()) // 예시로 context를 title로 사용
                .startTime(annualPlan.getStartDate())
                .endTime(annualPlan.getEndDate())
                .description("")
                .location("")
                .color("#FF7979")
                .alarmTime(0)
                .build();
    }

}