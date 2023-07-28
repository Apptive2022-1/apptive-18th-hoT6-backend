package com.hot6.pnureminder.dto;

import com.hot6.pnureminder.entity.AnnualPlan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnnualPlanResponseDto {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String context;

    public static AnnualPlanResponseDto toDto(AnnualPlan annualPlan){

        return new AnnualPlanResponseDto(
                annualPlan.getStartDate(),
                annualPlan.getEndDate(),
                annualPlan.getContext()
        );

    }

}
